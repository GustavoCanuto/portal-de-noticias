
import getLinkNoticias from "../pageAcess/geLinkNoticias.js";
import acessaLink from "../pageAcess/acessaLink.js";
import { getPagina } from './percorrerNoticias.js';

export default async function buscaDados(pageInstance){

      let linkBuscado = `https://www.saopaulo.sp.gov.br/spnoticias/ultimas-noticias/page/${getPagina()}/`
  
       await acessaLink(linkBuscado,pageInstance);
  
      const dadosLink = await getLinkNoticias(pageInstance);
  
      return dadosLink;
  }