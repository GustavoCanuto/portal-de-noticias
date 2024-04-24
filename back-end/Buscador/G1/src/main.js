import getDataNoticia from "./pageAcess/getDataNotica.js";
import getNoticia from "./pageAcess/getNoticia.js";
import getLinkNoticias from "./pageAcess/geLinkNoticias.js";
import replaceClasses from "./utils/replaceClasses.js";
import cleanNoticia from "./utils/cleanNoticia.js";
import isDataDoDiaAnterior from "./utils/checkDate.js";
import saveFileLocal from "./repository/saveFileLocal.js";
import {cadastrarNoticia, atualizarNoticia} from "./repository/saveBd.js";

let pageInstance;
let linksVerificado = [];


export default async function fetchData(browserInstance) {
  console.log("********************Iniciado busca*************************\n");

  let pagina = 2;
  let loop = true;
  let primeiroLink = true;
  let segundoLink = false;
  let proximoLoop = false;

  if (!browserInstance) {
    console.log("Navegador não inicializado.");
    return;
  }

  if (!pageInstance) pageInstance = await browserInstance.newPage();

  try {
    while (loop) {
      console.log("Inicio busca de links");
      console.log("pagina: "+ pagina)
      let linkBuscado = segundoLink
        ? `https://g1.globo.com/guia/guia-sp/index/feed/pagina-${pagina}.ghtml`
        : `https://g1.globo.com/sp/sao-paulo/index/feed/pagina-${pagina}.ghtml`;

      await pageInstance.goto(linkBuscado, {
        timeout: 50000,
        waitUntil: "load",
      });

      const dados = await getLinkNoticias(pageInstance);
      let count = 1;

      for (const link of dados) {
        let atualizar = false;

        if (proximoLoop) {
          console.log("executando proximo Loop");
          proximoLoop = false;
          break;
        }

        try {
          await pageInstance.goto(link.link, {
            timeout: 50000,
            waitUntil: "load",
          });

        

          const verificaData = await getDataNoticia(pageInstance);

          if (verificaData.grafico){
            
            await pageInstance.evaluate(async () => {
              await new Promise((resolve, reject) => {
                let totalHeight = 0;
                let distance = 100;
                const timer = setInterval(() => {
                  const scrollHeight = document.body.scrollHeight;
                  window.scrollBy(0, distance);
                  totalHeight += distance;
          
                  if (totalHeight >= scrollHeight) {
                    clearInterval(timer);
                    resolve();
                  }
                }, 100);
              });
            });
          }
          // Verificar se o link já foi verificado
          let linkJaFoiVerificado = linksVerificado.find(
            (item) => item.link === link.link
          );

          if (linkJaFoiVerificado) {
            console.log("Link já verificado");
            // Verificar se a data de atualização é diferente
            if (linkJaFoiVerificado.dataAtualizacao !== verificaData.dataAtualizada) {
              console.log("A data de atualização é diferente");
              // console.log("\ndata registrada: " +linkJaFoiVerificado.dataAtualizacao);
              // console.log("nova data: "+ verificaData.dataAtualizada+"\n");
              atualizar = true;
            } else {
              console.log("A data de atualização é a mesma, verificando proxima notica\n");
              continue;
            }
          }

          if (isDataDoDiaAnterior(verificaData.dataPublicacao)) {
            count++;
            console.log("Data da publicação/ Atualizacao é do dia anterior.\n");

            if (primeiroLink) {
              console.log("finaliza primeiro loop\n");
              pagina = 0;
              primeiroLink = false;
              segundoLink = true;
              proximoLoop = true;
              continue;
            } else {
              console.log("***********finalizado busca**************");
              loop = false;
              proximoLoop = true;
              continue;
            }
            
          } else {

            console.log("processo de pegar conteudo");

            console.log(verificaData.dataAtualizada)
            //const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));
            //await delay(15000); // Esperar 15 segundos

            const pageContent = await getNoticia(pageInstance, link, verificaData);

            if (!pageContent) {
              console.error("Conteúdo da página não encontrado.");
              return; //throw erro
            }

            pageContent.conteudo = replaceClasses(pageContent.conteudo);
            pageContent.conteudo = cleanNoticia(pageContent.conteudo);

         // saveFileLocal(pageContent, link.link, pagina, segundoLink, count);

            count++;

            let cache = {
              link: link.link,
              dataAtualizacao: verificaData.dataAtualizada,
            };
            linksVerificado.push(cache);

            if(atualizar) await atualizarNoticia(link.link, pageContent);
           else await cadastrarNoticia(pageContent);
          }
        } catch (error) {
          console.error("\nErro ao buscar os dados da página:", error.message);
          // Pular para a próxima iteração do loop
          continue;
        }
      }
      pagina++;
    }
  } catch (error) {
    console.error("\nErro ao buscar os dados:", error.message);

    // Se der erro de timeout, tenta novamente
    if (error instanceof puppeteer.TimeoutError) {
      console.log("Tentando novamente...");
      fetchData();
    }
  }
}
