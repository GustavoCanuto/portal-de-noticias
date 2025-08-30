import { setAtualizar } from './percorrerNoticias.js';
import { getLinksVerificado } from './processaLink.js';
import logger from "../log.js";

export default function verificaLink(link,verificaData){
  logger.info("verificando Link")

       let linkJaFoiVerificado = getLinksVerificado().find((item) => item.link === link.link);
  
       if (linkJaFoiVerificado) {
        logger.info("Link já verificado");

         if (linkJaFoiVerificado.dataAtualizacao !== verificaData.dataPublicacao) {
          logger.info("A data de atualização é diferente");
           setAtualizar(true);
           return true;
        
         } else {
          logger.info("A data de atualização é a mesma, verificando proxima notica\n");
           return false;
          
         }
       }
    return true;
  }