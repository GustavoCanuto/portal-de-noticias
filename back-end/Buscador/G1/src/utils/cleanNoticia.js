export default function limparNoticia(content) {

    //limpeza

      //limpar tag p
      content = content.replace(
        /<p\s+class="([^"]+)"[^>]*>(.*?)<\/p>/g,
        '<p class="$1">$2</p>'
      );

      //limpar html amp-img
      content = content.replace(/<img([^>]*)>/g, (match, p1) => {
        const srcMatch = p1.match(/src="([^"]*)"/);
        const altMatch = p1.match(/alt="([^"]*)"/);
        const classMatch = p1.match(/class="([^"]*)"/);
        return `<img ${srcMatch ? `src="${srcMatch[1]}"` : ""} ${
          altMatch ? `alt="${altMatch[1]}"` : ""
        } ${classMatch ? `class="${classMatch[1]}"` : ""} />`;
      });

      //limpar Leia Mais 
      content = content.replace(/<strong>(.*?)LEIA MAIS:(.*?)<\/strong>/gi, "<strong>$1 $2</strong>");
      content = content.replace(/<strong>(.*?)Leia também:(.*?)<\/strong>/gi, "<strong>$1 $2</strong>");
      content = content.replace(/<strong>(.*?)LEIA MAIS(.*?)<\/strong>/gi, "<strong>$1 $2</strong>");
      content = content.replace(/<strong>(.*?)Leia também(.*?)<\/strong>/gi, "<strong>$1 $2</strong>");
    
     //remover Videos: ultima noticia
     content = content.replace(/<(\w+)[^>]*>VÍDEOS:(.*?)<\/\1>/g, '');

      //remover tags vazias
      content = content.replace(/<(?!video|img|iframe|grafico)(\w+)[^>]*>\s*<\/\1>/g, '');

 return content;
}