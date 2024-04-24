import getNoticia from "../pageAcess/getNoticia.js";
import cleanNoticia from "../utils/cleanNoticia.js";
import { cadastrarNoticia, atualizarNoticia } from "../repository/saveBd.js";
import { getAtualizar } from './percorrerNoticias.js';

let linksVerificado = [];

export default async function processaLink(link, verificaData, pageInstance){

    console.log("processo de pegar conteudo");
  
    const pageContent = await getNoticia(pageInstance, link, verificaData);
  
    if (!pageContent) {
      console.error("Conteúdo da página não encontrado.");
      return; //throw erro
   }

   pageContent.conteudo = cleanNoticia(pageContent.conteudo);
   
   let cache = {
    link: link.link,
    dataAtualizacao: verificaData.dataAtualizacao,
    };

   linksVerificado.push(cache);
 
  
    if (getAtualizar()) await atualizarNoticia(link.link, pageContent);
    else await cadastrarNoticia(pageContent);
  }

  export function getLinksVerificado() {
    return linksVerificado;
  }