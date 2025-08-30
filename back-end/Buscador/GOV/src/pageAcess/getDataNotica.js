import logger from "../log.js";

export default async function getDataNoticia(pageInstance) {
  try {
      const verificaData = await pageInstance.evaluate(() => {
          const dataPublicacaoElement = document.querySelector('.article-header .date');

          const dateText = dataPublicacaoElement.textContent.trim();
          const publicacaoMatch = dateText.match(/(\d{2})\/(\d{2})\/(\d{4}) - (\d{1,2})h(\d{2})/);
          const publicacao = publicacaoMatch ? `${publicacaoMatch[3]}-${publicacaoMatch[2]}-${publicacaoMatch[1]}T${publicacaoMatch[4].padStart(2, '0')}:${publicacaoMatch[5]}:00.000Z` : null;
      
          return {
             dataPublicacao: publicacao
          };
      });
      logger.info(verificaData.dataPublicacao)
      return verificaData;
  } catch (error) {
    logger.error('Erro ao buscar os dados da página:'+ error.message); 
      throw error;
  }
}