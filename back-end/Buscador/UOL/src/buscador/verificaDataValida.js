import isDataDoDiaAnterior from "../utils/checkDate.js";
import { setLoop, getPagina, setPagina  } from './percorrerNoticias.js';

async function verificaDataFucntion(verificaData){

    console.log(verificaData.dataPublicacao)
    console.log("Data da publicação/ Atualizacao é do dia anterior.\n");
  
   if (getPagina()>=3) {
       console.log("finalizado busca");
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
  