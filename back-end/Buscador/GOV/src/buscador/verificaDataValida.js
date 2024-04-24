import isDataDoDiaAnterior from "../utils/checkDate.js";
import logger from "../log.js";

async function verificaDataFucntion(verificaData){

  logger.info(verificaData.dataPublicacao)
  logger.info("Data da publicação/ Atualizacao é do dia anterior.\n");
     
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
  