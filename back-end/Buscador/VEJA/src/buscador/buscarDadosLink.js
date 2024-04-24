
import getLinkNoticias from "../pageAcess/geLinkNoticias.js";
import acessaLink from "../pageAcess/acessaLink.js";
import { getSegundoLink } from './percorrerNoticias.js';

export default async function buscaDados(pageInstance){

      let linkBuscado = getSegundoLink()
        ? `https://vejasp.abril.com.br/?page=2&s=s%C3%A3o+paulo&orderby=date`
        : `https://vejasp.abril.com.br/?s=sp&orderby=date`;
  
       await acessaLink(linkBuscado,pageInstance);
  
      const dadosLink = await getLinkNoticias(pageInstance);
  
      return dadosLink;
  }