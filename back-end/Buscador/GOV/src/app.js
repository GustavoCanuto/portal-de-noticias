import { percorrerNoticias } from "./buscador/percorrerNoticias.js"; 
import puppeteer from 'puppeteer';

let pageInstance;
const userAgent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/58.0.3029.110 ';

export default async function fetchData(browserInstance) {

  if (!browserInstance) {
    console.log("Navegador não inicializado.");
    return;
  }

  if (!pageInstance || pageInstance.isClosed()) {
    pageInstance = await browserInstance.newPage();
  }

  await pageInstance.setUserAgent(userAgent);

   try {
   
    await percorrerNoticias(pageInstance);

  } catch (error) {
    console.error("\nErro ao buscar os dados:", error);
    setTimeout(async () => {
      console.log("\nTentando novamente...\n");
      await browserInstance.close();
      browserInstance = await puppeteer.launch({ timeout: 80000 });
      await fetchData(browserInstance);
    }, 120000);
    
  }
}






