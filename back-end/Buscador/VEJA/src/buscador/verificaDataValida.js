import isDataDoDiaAnterior from "../utils/checkDate.js";
import { setPrimeiroLink, setSegundoLink, setProximoLoop, setLoop, setPagina, getPrimeiroLink } from './percorrerNoticias.js';

async function verificaDataFucntion(verificaData){

    console.log(verificaData.dataPublicacao)
    console.log("Data da publicação/ Atualizacao é do dia anterior.\n");
  
   if (getPrimeiroLink()) {
      console.log("finaliza primeiro loop\n");
      setPagina(0);
      setPrimeiroLink(false);
      setSegundoLink(true);
      setProximoLoop(true);
      return;
    } 
     
    console.log("finalizado busca");
    setLoop(false);
    setProximoLoop(true);
    
  }

  export default async function verificaDataDiaAnterior(verificaData) {
    if (
      isDataDoDiaAnterior(verificaData.dataPublicacao) ||
      isDataDoDiaAnterior(verificaData.dataAtualizacao)
    ) {
      await verificaDataFucntion(verificaData);
      return true;
    }
    return false;
  }
  