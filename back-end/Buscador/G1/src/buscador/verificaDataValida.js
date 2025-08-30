import isDataDoDiaAnterior from "../utils/checkDate.js";
import { setPrimeiroLink, setSegundoLink, setProximoLoop, setLoop, setPagina, getPrimeiroLink } from './percorrerNoticias.js';
import logger from "../log.js";

async function verificaDataFucntion(verificaData){

  logger.info(verificaData.dataPublicacao)
  logger.info("Data da publicação/ Atualizacao é do dia anterior.\n");
  
   if (getPrimeiroLink()) {
    logger.info("finaliza primeiro loop\n");
      setPagina(0);
      setPrimeiroLink(false);
      setSegundoLink(true);
      setProximoLoop(true);
      return;
    } 
     
    logger.info("finalizado busca");
    setLoop(false);
    setProximoLoop(true);
    
  }

  export default async function verificaDataDiaAnterior(verificaData) {
    if (
      isDataDoDiaAnterior(verificaData.dataPublicacao) 
    ) {
      await verificaDataFucntion(verificaData);
      return true;
    }
    return false;
  }
  