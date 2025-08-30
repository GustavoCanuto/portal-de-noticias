import getNoticia from "../pageAcess/getNoticia.js";
import { cadastrarNoticia, atualizarNoticia } from "../repository/saveBd.js";
import { getAtualizar } from './percorrerNoticias.js';
import logger from "../log.js";

let linksVerificado = [];

export default async function processaLink(link, verificaData, pageInstance){

  logger.info("processo de pegar conteudo");
  
    let cache = {
      link: link.link,
      dataAtualizacao: verificaData.dataAtualizacao,
      };
  
     linksVerificado.push(cache);

    const pageContent = await getNoticia(pageInstance, link, verificaData.dataPublicacao);
  
    if (!pageContent) {
      logger.error("Conteúdo da página não encontrado.");
      return; //throw erro
   }

   pageContent.conteudo =  pageContent.conteudo.replace(/&nbsp;/g, '');
         
   pageContent.conteudo = pageContent.conteudo.replace(/<(?!video|img|iframe)(\w+)[^>]*>\s*<\/\1>/g, ''); 
  
    if (getAtualizar()) await atualizarNoticia(link.link, pageContent);
    else await cadastrarNoticia(pageContent);
  }

  export function getLinksVerificado() {
    return linksVerificado;
  }