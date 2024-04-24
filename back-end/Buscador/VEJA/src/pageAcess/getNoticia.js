export default async function getNoticia(pageInstance, link, verificaData) {
  try {
    const pageContent = await pageInstance.evaluate(
      extractNoticia,
      link,
      verificaData
    );
    return pageContent;
  } catch (error) {
    console.error("Erro ao buscar os dados da página:"+ error.message);
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
  const dataAtualizadaElement = verificaData.dataAtualizacao;
  const tags = Array.from(document.querySelectorAll(".article-tags li")).map(
    (tag) => tag.textContent.trim()
  );
  const articleBody = document.querySelector("article"); // +

  if (!articleBody) {
    throw new Error("Elemento article não encontrado");
  }

  const content = extractContent(articleBody);

  return {
    siteBuscado: "VEJA",
    linkDaNoticiaOficial: linkNoticia,
    imagemCapa: imagemCapa,
    titulo: document.querySelector(".post-header .title").textContent,
    sinopse: document.querySelector(".post-header .description").textContent,
    autoria: document.querySelector(".post-header .author> strong").innerHTML,
    categoria: document.querySelector(".post-header .category a").textContent,
    tags: tags,
    dataPublicacao: dataPublicacaoElement,
    dataModificada: dataAtualizadaElement,
    conteudo: content,
    numeroVisualizacao: 1,
  };

  function extractContent(articleBody) {
    const elements = articleBody.querySelectorAll(
      ".featured-media img, " +
        ".featured-media .caption, " +
        ".content .wp-caption-text, .content p:not(.infogram-embed p, .instagram-media p, .twitter-tweet p, blockquote > p), " +
        ".content .wp-caption img, .content blockquote," +
        ".content ul:not(.block-menu, .relacionadas-s-img, .block-menu, .article-tags, .share.hide-s)," +
        " .content h3, .content h2, " +
        ".content .infogram-embed , .content .instagram-media, .content .twitter-tweet "
    );

    //extrair isso
    let content = "";

    elements.forEach((element) => {

    if (element.classList.contains("caption")) {
        element.removeAttribute("style");
        element.classList.add("descricao__midia");
        content += element.outerHTML;
        return;
      }

      //adicionando classes
      if(adiconaClasseParagrafo(element, content)){
        content = adiconaClasseParagrafo(element, content);
        return;
      }
  
      if(adicionaClasse(element, content)) {
        content = adicionaClasse(element, content);
        return;
      }

      if (element.tagName.toLowerCase() === "blockquote") {
        element.removeAttribute("style");
        element.classList.add("citacao");
        let conteudo = element.outerHTML.replace(/<p>/g, "");
        conteudo = conteudo.replace(/<\/p>/g, "");
        content += conteudo;
        return;
      }

      if (
        element.classList.contains("infogram-embed") ||
        element.classList.contains("instagram-media") ||
        element.classList.contains("twitter-tweet")
      ) {
        element.style.display = "block";
        element.classList.add("publi__externa");
        content += element.outerHTML;
        return;
      }

      content += element.outerHTML;
    });

    return content;
  }

  function adiconaClasseParagrafo(element, content){

    if (element.tagName.toLowerCase() === "p") {
      let propaganda = element.firstElementChild;

      if(verificaPropagandaParagafro(propaganda)) return false;

      element.removeAttribute("style");
      element.classList.add("text__container");
      content += element.outerHTML;
      return content;
    }

    return false;

  }

  function verificaPropagandaParagafro(propaganda){
    if (propaganda) {
      if (propaganda.tagName.toLowerCase() === "a") {
        let texto = propaganda.textContent.trim();

        if (texto.startsWith("+")) {
          // O primeiro texto começa com "+"
          return true;
        }
      }

      if (propaganda.tagName.toLowerCase() === "b") {
        let texto = propaganda.textContent.trim();
        if (
          texto.startsWith("BAIXE") ||
          texto.startsWith("IOS") ||
          texto.startsWith("ANDROID")
        ) {
          // O primeiro texto começa com
          return true;
        }
      }
    }
  }

  function adicionaClasse(element, content) {
    if (element.tagName.toLowerCase() === "ul") {
      element.removeAttribute("style");
      element.classList.add("lista");
      content += element.outerHTML;
      return content;
    }

    if (element.tagName.toLowerCase() === "img") {
      element.removeAttribute("style");
      element.classList.add("imagem__container");
      content += element.outerHTML;
      return content;
    }

    if (element.classList.contains("wp-caption-text")) {
      element.removeAttribute("style");
      element.classList.add("descricao__midia");
      content += element.outerHTML;
      return content;
    }

    if (element.tagName.toLowerCase() === "h3") {
      element.removeAttribute("style");
      element.classList.add("subtitulo");
      content += element.outerHTML;
      return content;
    }

    if (element.tagName.toLowerCase() === "h2") {
      element.removeAttribute("style");
      element.classList.add("subtitulo");
      content += element.outerHTML;
      return content;
    }

    return false;
  }
}
