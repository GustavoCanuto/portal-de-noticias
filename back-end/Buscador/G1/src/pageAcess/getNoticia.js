export default async function getNoticia(pageInstance, link,verificaData) {
  try {
    const pageContent = await pageInstance.evaluate(extractNoticia, link,verificaData);
    return pageContent;
  } catch (error) {
    logger.error("Erro ao buscar os dados da página:"+ error.message);
    throw error;
  }
}

async function extractNoticia(link,verificaData) {

  const linkNoticia = link.link;
  const imagemCapa = `<img src='${link.imagem}' alt='${link.imagemAlt}'/>`;
  const dataPublicacaoElement = verificaData.dataPublicacao;
  const tags = Array.from(document.querySelectorAll(".entities__list-item")).map((tag) => tag.textContent.trim());
  const articleBody = document.querySelector('article[itemprop="articleBody"]');

  if (!articleBody) {
    throw new Error('Elemento article[itemprop="articleBody"] não encontrado');
  }

  const content = extractContent(articleBody);


  return {
    siteBuscado: 'G1',
    linkDaNoticiaOficial: linkNoticia,
    imagemCapa: imagemCapa,
    titulo: document.querySelector(".content-head__title").innerHTML,
    sinopse: document.querySelector(".content-head__subtitle").innerHTML,
    autoria: document.querySelector(".content-publication-data__from").innerHTML,
    categoria: document.querySelector(".header-editoria--link").innerHTML,
    tags: tags,
    dataPublicacao: dataPublicacaoElement,
    conteudo: content.replace(/Link_noticia_oficial/g, linkNoticia),
    numeroVisualizacao: 1
  };


  function extractContent(articleBody) {

    
    const elements = articleBody.querySelectorAll(
      ".content-media__description, " +
        ".content-text__container, .content-media-figure amp-img, " +
        ".show-table__container, .content-blockquote, " +
        ".content-video .content-media__container, .content-intertitle," +
        " .show-flourish-embed-block__container, .content-unordered-list, " +
        " .twitter-tweet"
    );

    //extrair isso
    let content = "";

    elements.forEach((element) => {
      
     
      
      // Remover todas as imagens e i-amphtml-sizer
      element.querySelectorAll('img, i-amphtml-sizer').forEach(img => img.remove());

      // Verifica se há links dentro da LI e remove a LI
      element.querySelectorAll("li").forEach((li) => {
        if (li.querySelector("a")) {
          li.remove();
        }
       }); 

      // Verificar se há um elemento <a> dentro da classe content-text__container
      if (element.classList.contains("content-text__container")) {
        const aElement = element.querySelector("a");

        if (aElement) {
          const palavras = ["CANAL", "DO", "G1"];
          const strongElement = aElement.querySelector("strong");
          // Verificar se o texto do strongElement contém todas as palavras da lista
          if (strongElement && palavras.every((palavra) => strongElement.innerText.toUpperCase().includes(palavra.toUpperCase()))) {
              aElement.remove();
            }
          }
      }

      //video indisponivel
      if (element.classList.contains("content-media__container")) {
        element.innerHTML = `<p>Vídeo Indisponível acesse a matéria oficial em:</p><a href="Link_noticia_oficial" target="_blank" class="link__reportagem">Clique para ler notícia oficial</a >`;
      }

      content += element.outerHTML;
    });

    return content;
  }
}
