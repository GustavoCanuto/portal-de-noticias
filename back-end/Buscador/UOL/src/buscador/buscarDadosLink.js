
import getLinkNoticias from "../pageAcess/geLinkNoticias.js";
import acessaLink from "../pageAcess/acessaLink.js";
import { getCategoriaLink, getPagina } from './percorrerNoticias.js';

export default async function buscaDados(pageInstance){

      let linkBuscado =  `https://noticias.uol.com.br/${getCategoriaLink(getPagina())}/`;
      
       await acessaLink(linkBuscado,pageInstance);
  
      const dadosLink = await getLinkNoticias(pageInstance);
  
      return dadosLink;
  }