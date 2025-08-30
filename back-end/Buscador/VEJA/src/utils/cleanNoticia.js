export default function limparNoticia(content) {

  content = content.replace(
  /<p\s+(?:[^>]*\s)?class="([^"]+)"[^>]*>(.*?)<\/p>/g,
  '<p class="$1">$2</p>'
);

content = content.replace(
  /<h3\s+(?:[^>]*\s)?class="([^"]+)"[^>]*>(.*?)<\/h3>/g,
  '<h3 class="$1">$2</h3>'
);

content = content.replace(
  /<h2\s+(?:[^>]*\s)?class="([^"]+)"[^>]*>(.*?)<\/h2>/g,
  '<h2 class="$1">$2</h2>'
);

content = content.replace(
  /<blockquote\s+(?:[^>]*\s)?class="([^"]+)"[^>]*>(.*?)<\/blockquote>/g,
  '<blockquote class="$1">$2</blockquote>'
);

content = content.replace(
  /<ul\s+(?:[^>]*\s)?class="([^"]+)"[^>]*>(.*?)<\/ul>/g,
  '<ul class="$1">$2</ul>'
);

content =  content.replace(/&nbsp;/g, '');

content = content.replace(/<(?!video|img|iframe)(\w+)[^>]*>\s*<\/\1>/g, '');

 return content;
}