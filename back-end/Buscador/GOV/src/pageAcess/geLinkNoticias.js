export default async function getLinkNoticias(pageInstance) {

    const dados = await pageInstance.evaluate(() => {
        const linkElements = document.querySelectorAll('.category-thumbnail');
        const dadosArray = [];
      
        linkElements.forEach(linkElement => {
          const link = linkElement.querySelector('a').href;
          const imagemElement = linkElement.querySelector('.wp-image-thumb');
          const imagem = imagemElement ? imagemElement.src : null;
          const  imagemAlt = imagemElement ? imagemElement.alt : null;
      
          dadosArray.push({ link, imagem, imagemAlt });
        });
      
        return dadosArray;
      });

      return dados;
}