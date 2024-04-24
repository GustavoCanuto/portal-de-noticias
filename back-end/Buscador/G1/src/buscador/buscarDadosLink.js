
import getLinkNoticias from "../pageAcess/geLinkNoticias.js";
import acessaLink from "../pageAcess/acessaLink.js";
import { getSegundoLink, getPagina } from './percorrerNoticias.js';

export default async function buscaDados(pageInstance){

      let linkBuscado = getSegundoLink()
      ? `https://g1.globo.com/guia/guia-sp/index/feed/pagina-${getPagina()}.ghtml`
      : `https://g1.globo.com/sp/sao-paulo/index/feed/pagina-${getPagina()}.ghtml`;
  
       await acessaLink(linkBuscado,pageInstance);
  
      const dadosLink = await getLinkNoticias(pageInstance);
  
      return dadosLink;
  }