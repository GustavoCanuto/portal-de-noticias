export default async function getDataNoticia(pageInstance) {
    try {
        const verificaData = await pageInstance.evaluate(() => {
  
            const dataPublicacaoElement = document.querySelector('.solar-author-date .solar-date .date');
            const dataAtualizadaElement = document.querySelector('.solar-author-updated .date');
        
            return {
                dataPublicacao: dataPublicacaoElement? dataPublicacaoElement.getAttribute('datetime'):null,
                dataAtualizada: dataAtualizadaElement? dataAtualizadaElement.getAttribute('datetime'):null
      
            };
        });

        return verificaData;
    } catch (error) {
        logger.error('Erro ao buscar os dados da página:'+ error.message); 
        throw error;
    }
  }