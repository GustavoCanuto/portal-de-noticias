import { setAtualizar } from './percorrerNoticias.js';
import { getLinksVerificado } from './processaLink.js';

export default function verificaLink(link,verificaData){
      console.log("verificando Link")

       let linkJaFoiVerificado = getLinksVerificado().find((item) => item.link === link.link);
  
       if (linkJaFoiVerificado) {
         console.log("Link já verificado");

         if (linkJaFoiVerificado.dataAtualizacao !== verificaData.dataPublicacao) {
           console.log("A data de atualização é diferente");
           setAtualizar(true);
           return true;
        
         } else {
           console.log("A data de atualização é a mesma, verificando proxima notica\n");
           return false;
          
         }
       }
    return true;
  }