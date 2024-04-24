export default async function getLinkNoticias(pageInstance) {

    const dados = await pageInstance.evaluate(() => {
        const linkElements = document.querySelectorAll('.home__list__tag');
        const dadosArray = [];
      
        linkElements.forEach(linkElement => {
          
          const link = linkElement.href;
          if(link){
          const imagemElement = linkElement.querySelector('img');
          const imagem = imagemElement ? imagemElement.src : null;
          const  imagemAlt = imagemElement ? imagemElement.alt : null;
      
          dadosArray.push({ link, imagem, imagemAlt });
          }
        });
      
        return dadosArray;
      });

      return dados;
}