import isDataDoDiaAnterior from "../utils/checkDate.js";
import { setLoop, getPagina, setPagina  } from './percorrerNoticias.js';
import logger from "../log.js";

async function verificaDataFucntion(verificaData){

  logger.info(verificaData.dataPublicacao)
  logger.info("Data da publicação/ Atualizacao é do dia anterior.\n");
  
   if (getPagina()>=3) {
    logger.info("finalizado busca");
      setPagina(0);
      setLoop(false);
    } 

    
  }

  export default async function verificaDataDiaAnterior(verificaData) {
    if (
      isDataDoDiaAnterior(verificaData.dataPublicacao) ||
      isDataDoDiaAnterior(verificaData.dataAtualizada)
    ) {
      await verificaDataFucntion(verificaData);
      return true;
    }
    return false;
  }
  