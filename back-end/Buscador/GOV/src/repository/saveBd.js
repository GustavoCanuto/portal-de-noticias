import fetch from 'node-fetch';
import logger from "../log.js";

 async function cadastrarNoticia(pageContent) {
  try {
    const response = await fetch('http://localhost:8082/noticia', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(pageContent),
    });
    if (response.ok) {
      logger.info('Notícia cadastrada com sucesso\n');
    } else {
        const errorText = await response.text(); // Aqui você obtém o texto do erro
        logger.error('Erro ao cadastrar notícia:'+ response.statusText+" "+ errorText);
    }
  } catch (error) {
    const errorText = await response.text(); // Aqui você obtém o texto do erro
    logger.error('Erro ao cadastrar notícia:'+ response.statusText+" "+ errorText);

  }
}

 async function atualizarNoticia(linkNoticia, pageContent) {
  try {
    const response = await fetch(`http://localhost:8080/noticia?linkNoticia=${linkNoticia}`, {
      method: 'PUT', // ou 'PATCH' dependendo da sua API
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(pageContent),
    });
    if (response.ok) {
      logger.info('Notícia atualizada com sucesso\n');
    } else {
        const errorText = await response.text(); // Aqui você obtém o texto do erro
        logger.error('Erro ao atualizar notícia:'+ response.statusText+" "+ errorText);
    }
  } catch (error) {
    logger.error('Erro ao atualizar notícia: '+ error);
  }
}

export {cadastrarNoticia, atualizarNoticia}