
import getLinkNoticias from "../pageAcess/geLinkNoticias.js";
import acessaLink from "../pageAcess/acessaLink.js";
import { getSegundoLink, getPagina } from './percorrerNoticias.js';

export default async function buscaDados(pageInstance){

      let linkBuscado = getSegundoLink()
      ? `https://www.cnnbrasil.com.br/tudo-sobre/sao-paulo-capital/pagina/${getPagina()}/`
      : `https://www.cnnbrasil.com.br/tudo-sobre/sao-paulo-estado/pagina/${getPagina()}/`;
  
       await acessaLink(linkBuscado,pageInstance);
  
      const dadosLink = await getLinkNoticias(pageInstance);
  
      return dadosLink;
  }