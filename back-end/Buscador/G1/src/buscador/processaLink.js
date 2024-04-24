import getNoticia from "../pageAcess/getNoticia.js";
import cleanNoticia from "../utils/cleanNoticia.js";
import replaceClasses from "../utils/replaceClasses.js";
import { cadastrarNoticia, atualizarNoticia } from "../repository/saveBd.js";
import { getAtualizar } from './percorrerNoticias.js';
import logger from "../log.js";

let linksVerificado = [];

export default async function processaLink(link, verificaData, pageInstance){

  logger.info("processo de pegar conteudo");

    let cache = {
      link: link.link,
      dataAtualizacao: verificaData.dataAtualizada,
      };
  
     linksVerificado.push(cache);

    if (verificaData.grafico){

      logger.info("noticia com grafico");

      await pageInstance.evaluate(async () => {
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
      });
    }

    const pageContent = await getNoticia(pageInstance, link, verificaData);
  
    if (!pageContent) {
      logger.error("Conteúdo da página não encontrado.");
      return; //throw erro
   }
   
   pageContent.conteudo = replaceClasses(pageContent.conteudo);
   pageContent.conteudo = cleanNoticia(pageContent.conteudo);

    if (getAtualizar()) await atualizarNoticia(link.link, pageContent);
    else await cadastrarNoticia(pageContent);
  }

  export function getLinksVerificado() {
    return linksVerificado;
  }