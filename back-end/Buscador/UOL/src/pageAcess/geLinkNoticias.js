export default async function getLinkNoticias(pageInstance) {

  const dados = await pageInstance.evaluate(async () => {

    
  let cont =0;
  const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

  //8
  while (cont < 7) {
   
    const botaoCarregarMais = document.querySelector('.btn-search.ver-mais.btn-more');
    if(botaoCarregarMais){
    // Simula um clique no botão
    botaoCarregarMais.click();
    console.log("Esperando 3 segundos...");
    await delay(3000); // Esperar 3 segundos

    await new Promise((resolve, reject) => {
      let totalHeight = 0;
      let distance = 100;
      const timer = setInterval(() => {
        const scrollHeight = document.body.scrollHeight;
        window.scrollBy(0, distance);
        totalHeight += distance;
  
        if (totalHeight >= scrollHeight) {
          clearInterval(timer);
          resolve();
        }
      }, 100);
    });

    }
    cont++;
  }
  

  await new Promise((resolve, reject) => {
    let totalHeight = 0;
    let distance = 100;
    const timer = setInterval(() => {
      const scrollHeight = document.body.scrollHeight;
      window.scrollBy(0, distance);
      totalHeight += distance;

      if (totalHeight >= scrollHeight) {
        clearInterval(timer);
        resolve();
      }
    }, 100);
  });

  const linkElements = document.querySelectorAll('.thumbnails-wrapper');
      const dadosArray = [];
    
      linkElements.forEach(linkElemento => {
        
        const link = linkElemento.querySelector('a').href;
        if(link && link.includes('noticias.uol.com.br')){
        const imagemElement = linkElemento.querySelector('img');
        const imagem = imagemElement ? imagemElement.src : null;
        const  imagemAlt = imagemElement ? imagemElement.alt : null;
    
        dadosArray.push({ link, imagem, imagemAlt });
        }
      });
    
      return dadosArray;
    });

    return dados;
}