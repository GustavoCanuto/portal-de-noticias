package br.com.magportal.apimagspnews.infra;

import java.time.Duration;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

	@Bean
	public RedisCacheConfiguration cacheConfiguration(ObjectMapper mapper) {
		ObjectMapper myMapper = mapper.copy()
	            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
	            .registerModule(
	                new Hibernate5Module()
	                    .enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING)
	                    .enable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS)
	            )
	            .activateDefaultTyping(
	                mapper.getPolymorphicTypeValidator(),
	                DefaultTyping.EVERYTHING,
	                As.PROPERTY
	            );
	        return RedisCacheConfiguration.defaultCacheConfig()
	            .entryTtl(Duration.ofMinutes(30))
	            .disableCachingNullValues()
	            .serializeValuesWith(
	                SerializationPair.fromSerializer(
	                    new GenericJackson2JsonRedisSerializer(myMapper)
	                )
	            );
	}


	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, ObjectMapper mapper) {

		RedisCacheConfiguration redisCacheConfiguration = cacheConfiguration(mapper);
				
		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration)
				.withCacheConfiguration("cachePaginaInicial",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(12)))
				.withCacheConfiguration("cacheNoticiaCompleta",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(22)))
				.withCacheConfiguration("cachePadrao",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)))
				.withCacheConfiguration("cacheValidacaoLink",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(7)))
				.withCacheConfiguration("cacheValidacaoLinkSite",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
				.transactionAware().build();

	}
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("localhost");
		redisStandaloneConfiguration.setPort(6379);

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofSeconds(2))
				.clientOptions(ClientOptions.builder().socketOptions(SocketOptions.builder().build())
						.timeoutOptions(TimeoutOptions.enabled()).autoReconnect(false)

						.build())
				.build();

		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration,
				clientConfig);
		connectionFactory.setValidateConnection(true);
		connectionFactory.afterPropertiesSet();

		return connectionFactory;
	}

	// Backup Cache
	@Bean
	public CaffeineCacheManager caffeineCacheManager() {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.registerCustomCache(
			    "cachePaginaInicial",
			    Caffeine.newBuilder()
			        .maximumSize(10000)
			        .expireAfterWrite(Duration.ofMinutes(12))
			        .build()
			);
		
		caffeineCacheManager.registerCustomCache(
			    "cacheNoticiaCompleta",
			    Caffeine.newBuilder()
			        .maximumSize(10000)
			        .expireAfterWrite(Duration.ofMinutes(22))
			        .build()
			);
		
		caffeineCacheManager.registerCustomCache(
			    "cachePadrao",
			    Caffeine.newBuilder()
			        .maximumSize(10000)
			        .expireAfterWrite(Duration.ofMinutes(5))
			        .build()
			);
		
		caffeineCacheManager.registerCustomCache(
			    "cacheValidacaoLink",
			    Caffeine.newBuilder()
			        .maximumSize(10000)
			        .expireAfterWrite(Duration.ofMinutes(7))
			        .build()
			);

		caffeineCacheManager.registerCustomCache(
			    "cacheValidacaoLinkSite",
			    Caffeine.newBuilder()
			        .maximumSize(10000)
			        .expireAfterWrite(Duration.ofMinutes(10))
			        .build()
			);

		return caffeineCacheManager;
	}


	@Bean
	@Primary
	public CompositeCacheManager compositeCacheManager(RedisCacheManager redisCacheManager,
			CaffeineCacheManager caffeineCacheManager) {
		try {
			RedisConnection conection = redisConnectionFactory().getConnection();
			conection.close();
			return new CompositeCacheManager(redisCacheManager, caffeineCacheManager);
		} catch (Exception ex) {
			return new CompositeCacheManager(caffeineCacheManager, redisCacheManager);
		}
	}

}