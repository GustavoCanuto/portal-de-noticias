import { fileURLToPath } from 'url';
import fs from 'fs';
import path from 'path';

// Obtém o caminho do arquivo atual
const __filename = fileURLToPath(import.meta.url);

// Obtém o diretório pai do diretório atual
const __dirname = path.dirname(__filename)

export default function saveFileLocal(pageContent,link,pagina,count) {

const folderPath = path.join(__dirname, '../../arquivosGerados/html');
const folderPath2 = path.join(__dirname, '../../arquivosGerados/json');

 // Verifica se a pasta 'html' existe, se não, cria ela
 if (!fs.existsSync(folderPath)) {
    fs.mkdirSync(folderPath);
  }

  // Verifica se a pasta 'json' existe, se não, cria ela
  if (!fs.existsSync(folderPath2)) {
    fs.mkdirSync(folderPath2);
  }
  
const fileName = path.join(folderPath,`${pagina}  e ${count}.html`);
let pageContentString = `<a href="${link}" target="_blank" >noticia original</a><br><br>` ;
 pageContentString += JSON.stringify(pageContent.conteudo, null, 2).replace(/\n/g, '').replace(/\\/g, '');
fs.writeFileSync(fileName, pageContentString, 'utf8');
console.log('Arquivo salvo em html:', `${pagina}  e ${count}.html`);

const fileName2 = path.join(folderPath2,`${pagina}  e ${count}.txt`);
const pageContentString2 = JSON.stringify(pageContent, null, 2).replace(/\n/g, '').replace(/\\/g, '');
fs.writeFileSync(fileName2, pageContentString2, 'utf8');
 console.log('Arquivo salvo em texto:', `${pagina} e ${count}.txt`);

}