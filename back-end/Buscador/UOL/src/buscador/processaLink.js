import getNoticia from "../pageAcess/getNoticia.js";
import { cadastrarNoticia, atualizarNoticia } from "../repository/saveBd.js";
import { getAtualizar } from './percorrerNoticias.js';
import logger from "../log.js";

let linksVerificado = [];

export default async function processaLink(link, verificaData, pageInstance){

  logger.info(" processo de pegar conteudo");
   
   let cache = {
    link: link.link,
    dataAtualizacao: verificaData.dataAtualizada,
    };

   linksVerificado.push(cache);

    const pageContent = await getNoticia(pageInstance, link, verificaData);
  
    if (!pageContent) {
      logger.error("Conteúdo da página não encontrado.");
      return; //throw erro
   }

   const conteudo = pageContent.conteudo; 
   const titulo = pageContent.titulo; 

   // Expressão regular para buscar São Paulo, SP ou Paulista, ignorando o caso das letras
   const regex = /\b(s[ãa]o paulo|sp|paulista)\b/i;
  
   // Verifica se a expressão regular encontra correspondência no conteúdo ou no título
   if (!regex.test(conteudo) && !regex.test(titulo)) {
    logger.info("Texto não é de São Paulo. Vendo próxima notícia. ** link :"+ link.link+"\n");
     return;
   } 
  
   pageContent.conteudo = pageContent.conteudo.replace(/<(?!video|img|iframe)(\w+)[^>]*>\s*<\/\1>/g, '');
   
    if (getAtualizar()) await atualizarNoticia(link.link, pageContent);
    else await cadastrarNoticia(pageContent);
  }

  export function getLinksVerificado() {
    return linksVerificado;
  }