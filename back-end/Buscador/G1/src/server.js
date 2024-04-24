import express from 'express';
import puppeteer from 'puppeteer';
import app  from './app.js';

const port = 3000;
const server = express();

(async () => {
  const runApp = async () => {
    try {
      const browserInstance = await puppeteer.launch({ timeout: 80000 });
      await app(browserInstance); // Executa a busca ao iniciar o servidor

      // Agendar a busca a cada 30 minutos
      setInterval(async () => await app(browserInstance), 30 * 60 * 1000);
    } catch (error) {
      console.error('Erro ao iniciar o navegador:', error);
      console.log('Reiniciando o navegador...');

      // Tentar reiniciar o navegador após um intervalo de tempo
      setTimeout(() => runApp(), 2 * 60 * 1000); // Reiniciar após 2 minutos
    }
  };

  runApp();
})();

server.listen(port, () => {
  console.log("Servidor rodando na porta "+ port);
});
