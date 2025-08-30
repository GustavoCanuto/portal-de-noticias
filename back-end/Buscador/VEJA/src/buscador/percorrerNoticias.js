import getDataNoticia from "../pageAcess/getDataNotica.js";
import acessaLink from "../pageAcess/acessaLink.js";
import buscaDados from "./buscarDadosLink.js";
import verificaLink from "./verificaLink.js";
import verificaDataDiaAnterior from "./verificaDataValida.js";
import processaLink from "./processaLink.js";
import logger from "../log.js";

let pagina = 1;
let loop = true;
let primeiroLink = true;
let segundoLink = false;
let proximoLoop = false;
let atualizar = false;

export async function percorrerNoticias(pageInstance) {
  logger.info("Iniciado busca");

  pagina = 1;
  loop = true;
  primeiroLink = true;
  segundoLink = false;
  proximoLoop = false;

  while (loop) {
    logger.info("Inicio busca de links\n");
    logger.info("pagina: "+ pagina)
    let dados2 = await buscaDados(pageInstance);
    let count = 1;

    for (const link of dados2) {
      atualizar = false;

      if (proximoLoop) {
        logger.info("executando proximo Loop");
        proximoLoop = false;
        break;
      }

      try {
        await acessaLink(link.link,pageInstance);
        logger.info("pegar data noticia");
        const verificaData = await getDataNoticia(pageInstance);

        if (!verificaLink(link, verificaData)) continue;

        if (await verificaDataDiaAnterior(verificaData)) {
          count++;
          continue;
        } 

        logger.info("processando link...");
        await processaLink(link, verificaData, pageInstance);
        count++;
      } catch (error) {

        pageInstance = await timeoutError(pageInstance,error.name);

        logger.error("\nErro ao buscar os dados da página:"+ error.message);
        continue;
      }
    }
    pagina++;
  }
}


async function timeoutError(pageInstance,error){
  if (error === 'TimeoutError') {
    logger.error('\nTimeout de navegação. Recarregando a página...\n');
    // Recarregar a página
    await pageInstance.reload();
    await clearCache(pageInstance);
  }

  return pageInstance;
}

export default async function clearCache(pageInstance) {
  await pageInstance.evaluate(() => {
    // Limpa o cache do navegador
    return window.location.reload(true);
  });
}

export function setPagina(value) {
    pagina = value;
  }
  
export function getPrimeiroLink() {
    return primeiroLink;
}

export function getSegundoLink() {
  return segundoLink;
}

export function getAtualizar() {
  return atualizar;
}

export function setLoop(value) {
    loop = value;
  }
  
export function setPrimeiroLink(value) {
    primeiroLink = value;
  }
  
export function setSegundoLink(value) {
    segundoLink = value;
  }
  
export function setProximoLoop(value) {
    proximoLoop = value;
  }
  
export function setAtualizar(value) {
    atualizar = value;
}