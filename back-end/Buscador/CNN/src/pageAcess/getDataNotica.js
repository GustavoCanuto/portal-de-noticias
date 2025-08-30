export default async function getDataNoticia(pageInstance) {
    try {
      const { dataElement } = await pageInstance.evaluate(() => {
        const dataElement = document.querySelector('.post__data').innerText;
        return { dataElement };
      });
  
      // Extrair data de publicação
      const publicacaoMatch = dataElement.match(/(\d{2})\/(\d{2})\/(\d{4}) às (\d{2}):(\d{2})/);
      const publicacao = publicacaoMatch ? `${publicacaoMatch[3]}-${publicacaoMatch[2]}-${publicacaoMatch[1]}T${publicacaoMatch[4]}:${publicacaoMatch[5]}:00.000Z` : null;
  
      // Extrair data de atualização
      const atualizacaoMatch = dataElement.match(/Atualizado (\d{2})\/(\d{2})\/(\d{4}) às (\d{2}):(\d{2})/);
      const atualizacao = atualizacaoMatch ? `${atualizacaoMatch[3]}-${atualizacaoMatch[2]}-${atualizacaoMatch[1]}T${atualizacaoMatch[4]}:${atualizacaoMatch[5]}:00.000Z` : null;
  
  
     // console.log("publicao:"+publicacao)
     // console.log("atualizacao:"+atualizacao)
      return { dataPublicacao: publicacao, dataAtualizacao: atualizacao };
    } catch (error) {
      console.error(error);
      return { dataPublicacao: null, dataAtualizacao: null };
    }
  }