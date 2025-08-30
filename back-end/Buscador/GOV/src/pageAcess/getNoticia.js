import logger from "../log.js";

export default async function getNoticia(pageInstance, link, dataPublicacao) {
  try {
    const pageContent = await pageInstance.evaluate(extractNoticia, link, dataPublicacao);
    return pageContent;
  } catch (error) {
    logger.error("Erro ao buscar os dados da página: "+ error.message);
    throw error;
  }
}

async function extractNoticia(link, dataPublicacao) {

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
  const dataPublicacaoElement = dataPublicacao;
  const tags = Array.from(document.querySelectorAll(".category")).map((tag) => tag.textContent.trim());
  const articleBody = document.querySelector('.article-main');

  if (!articleBody) {
    throw new Error('Elemento .article-main não encontrado');
  }

  const content = extractContent(articleBody);


  return {
    siteBuscado: 'GOV',
    linkDaNoticiaOficial: linkNoticia,
    imagemCapa: imagemCapa,
    titulo: document.querySelector(".article-header .title").innerHTML,
    sinopse: document.querySelector(".article-header .excerpt").innerHTML,
    autoria: document.querySelector(".article-header .date b").innerHTML.replace("|", ""),
    categoria: tags[0]? tags[0]: "São Paulo",
    tags: tags,
    dataPublicacao: dataPublicacaoElement,
    conteudo: content,
    numeroVisualizacao: 1
  };


  function extractContent(articleBody) {

    const elements = articleBody.querySelectorAll(
      ".wp-caption-text, " +
        "p:not(blockquote > p):not(:has(iframe)):not(:has(blockquote)), img, iframe, blockquote, " +
        "ul, table " 
    );

    //extrair isso
    let content = "";

    elements.forEach((element) => {
      
         //adicionando classes 
         if (element.tagName.toLowerCase() === "p")  {
          element.removeAttribute("style");
          let texto = element.textContent.toLowerCase().trim();
          if(texto.startsWith("siga o canal “governo de são paulo”")) return;
          element.classList.add("text__container");
          content += element.outerHTML;
          return;
        }

        if (element.tagName.toLowerCase() === "table")  {
          element.removeAttribute("style");
          content += `<div class='tabela'>${element.outerHTML}</div>`;
          return;
        }

      if (element.classList.contains("instagram-media") || element.classList.contains("twitter-tweet")) {

        element.classList.add("publi__externa");
        content += element.outerHTML;
        return;
      }

      if (element.classList.contains("wp-caption-text")) {
        element.removeAttribute("style");
        element.classList.add("descricao__midia");
        content += element.outerHTML;
        return;
      }

        if (element.tagName.toLowerCase() === "iframe")  {
          element.removeAttribute("style");
          content += `<div class=video__container> ${element.outerHTML}</div>`;
          return;
        }
  
        if (element.tagName.toLowerCase() === "blockquote")  {
          element.removeAttribute("style");
          element.classList.add("citacao");
          let conteudo = element.outerHTML.replace(/<p>/g, '');
          conteudo = conteudo.replace(/<\/p>/g, '');
          content += conteudo;
          return;
        }
  
  
        if (element.tagName.toLowerCase() === "ul")  {
          element.removeAttribute("style");
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

      content += element.outerHTML;
    });

    return content;
  }
}
