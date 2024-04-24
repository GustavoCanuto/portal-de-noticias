export default async function getNoticia(pageInstance, link, verificaData) {
  try {
    const pageContent = await pageInstance.evaluate(extractNoticia, link, verificaData);
    return pageContent;
  } catch (error) {
    logger.error("Erro ao buscar os dados da página:"+ error.message);
    throw error;
  }
}

async function extractNoticia(link, verificaData) {


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
  
    
  const linkNoticia = link.link;
  const imagemCapa = `<img src='${link.imagem}' alt='${link.imagemAlt}'/>`;
  const dataPublicacaoElement = verificaData.dataPublicacao;
  const dataAtualizadaElement = verificaData.dataAtualizada;
  const articleBody = document.querySelector('article'); // +

  if (!articleBody) {
    throw new Error('Elemento article não encontrado');
  }
  const content = extractContent(articleBody);


  return {
    siteBuscado: 'UOL',
    linkDaNoticiaOficial: linkNoticia,
    imagemCapa: imagemCapa,
    titulo: document.querySelector("article .title").innerHTML,
    autoria: document.querySelector("article .solar-author-names").innerHTML,
    categoria : document.querySelector("article .kicker-item a")?.textContent ?? 
                 document.querySelector("article .featured")?.textContent ?? 
                 'São Paulo',
    dataPublicacao: dataPublicacaoElement.replace(/-?\d{2}:\d{2}$/, ''),
    dataModificada: dataAtualizadaElement? dataAtualizadaElement.replace(/-?\d{2}:\d{2}$/, ''): null,
    conteudo: content,
    numeroVisualizacao: 1
  };


  function extractContent(articleBody) {

    const elements = articleBody.querySelectorAll(
      ".solar-caption:not(aside *), " +
        "p:not(aside *):not(.solar-related *), h2:not(aside *,.heading-3),h3:not(aside *, .heading-3),h4:not(aside *, .heading-3), .photo-image img:not(aside *)," +
        "ul:not(aside *):not(.links-list), .solar-embed-video .youtube-embed, cite:not(aside *), .twitter-tweet, .instagram-media " 
    );

    //extrair isso
    let content = "";

    elements.forEach((element) => {
      
    if (element.classList.contains("instagram-media")|| element.classList.contains("twitter-tweet")) {
     
        element.classList.add("publi__externa");
        content += element.outerHTML;
        return;
      }
      if (element.classList.contains("youtube-embed"))  {
        
        let iframe =  element.querySelector('iframe');
        iframe.removeAttribute("style");
        content += `<div class=video__container> ${iframe.outerHTML}</div>`;
        return;
      }

         //adicionando classes 
      if (element.tagName.toLowerCase() === "p")  {
          element.removeAttribute("style");
       
          let paragrafo = element.querySelectorAll('p, h2, h3, ul, cite');
          
          eleminaParagrafro(paragrafo);

          element.classList.add("text__container");
          content += element.outerHTML;
          return;
        }
      

      if (element.classList.contains("solar-caption")) {
        element.removeAttribute("style");
        element.classList.add("descricao__midia");
        content += element.outerHTML;
        return;
      }
  
   
      if (element.tagName.toLowerCase() === "h2"||element.tagName.toLowerCase() === "h3"||element.tagName.toLowerCase() === "h4")  {
        element.removeAttribute("style");
        const texto = element.textContent ? element.textContent.toLowerCase():'';
        if(texto.startsWith("notícias relacionadas"))   return;

        element.classList.add("subtitulo");
        content += element.outerHTML;
        return;
      }
  
        if (element.tagName.toLowerCase() === "ul")  {

          element.removeAttribute("style");
      
         eleminaLi(element);

          element.classList.add("lista");
          content += element.outerHTML;
          return;
        }

      

        if (element.tagName.toLowerCase() === "img")  {
          element.removeAttribute("style");
          element.classList.add("imagem__container");
          content += element.outerHTML;
          return;
        }

        if (element.tagName.toLowerCase() === "cite")  {
          element.removeAttribute("style");
          element.classList.add("citacao");
          content += element.outerHTML;
          return;
        }

      content += element.outerHTML;
    });

    return content;
  }

  function eleminaLi(element){
    // Verifica se há links dentro da LI e remove a LI
    element.querySelectorAll("li").forEach((li) => {
      if (li.children.length === 1 && li.querySelector("a")) {
        li.remove();
      }
     }); 
  }

  function eleminaParagrafro(paragrafo){
    // Verifica se há links dentro da LI e remove a LI
    if (paragrafo.length>0) {

      paragrafo.forEach((para) => {
        para.remove();
      });

    }
  }
}
