import getDataNoticia from "../pageAcess/getDataNotica.js";
import acessaLink from "../pageAcess/acessaLink.js";
import buscaDados from "./buscarDadosLink.js";
import verificaLink from "./verificaLink.js";
import verificaDataDiaAnterior from "./verificaDataValida.js";
import processaLink from "./processaLink.js";
import logger from "../log.js";

let pagina = 0;
let loop = true;
let primeiroLink = true;
let segundoLink = false;
let proximoLoop = false;
let atualizar = false;
let categoriaLink = [ 'politica', 'ultimas', 'cotidiano', 'saude']

export async function percorrerNoticias(pageInstance) {
  logger.info("Iniciado busca");

  pagina = 0;
  loop = true;
  primeiroLink = true;
  segundoLink = false;
  proximoLoop = false;

  while (loop) {
    logger.info("Inicio busca de links\n");
    logger.info("pagina: "+ pagina)
    logger.info(categoriaLink[pagina]);
    let dados2 = await buscaDados(pageInstance);
    let count = 1;

    for (const link of dados2) {
      atualizar = false;
    //  link.link ='https://noticias.uol.com.br/ultimas-noticias/agencia-brasil/2024/03/17/pesquisa-constata-que-dengue-se-espalha-para-o-sul-e-centro-oeste.htm'
      try {
        await acessaLink(link.link,pageInstance);

        logger.info("pegar data noticia");
        const verificaData = await getDataNoticia(pageInstance);

        if (!verificaLink(link, verificaData)) continue;

        if (await verificaDataDiaAnterior(verificaData)) {
          count++;
          break;
        } 

        logger.info("processando link...");
        count++;
        await processaLink(link, verificaData, pageInstance);
        
      } catch (error) {

        pageInstance = await timeoutError(pageInstance,error.name,link.link);

        logger.error("\nErro ao buscar os dados da página:"+ error.message +"\n");
        continue;
      }
    }
    pagina++;
  }
}

async function timeoutError(pageInstance,error,link){
  if (error === 'TimeoutError') {
    logger.error('\nTimeout de navegação. Recarregando a página...\n'+ link);
    await pageInstance.reload();
    await clearCache(pageInstance);
  }

  return pageInstance;
}

export default async function clearCache(pageInstance) {
  await pageInstance.evaluate(() => {
    // Limpa o cache do navegador
    return window.location.reload(true);
  });
}

export function setPagina(value) {
    pagina = value;
  }
  
  export function getPrimeiroLink() {
    return primeiroLink;
}

export function getPagina() {
  return  pagina;
}

export function getCategoriaLink(pagina) {
    return categoriaLink[pagina];
}

export function getSegundoLink() {
  return segundoLink;
}

export function getAtualizar() {
  return atualizar;
}

export function setLoop(value) {
    loop = value;
  }
  
export function setPrimeiroLink(value) {
    primeiroLink = value;
  }
  
export function setSegundoLink(value) {
    segundoLink = value;
  }
  
export function setProximoLoop(value) {
    proximoLoop = value;
  }
  
export function setAtualizar(value) {
    atualizar = value;
}