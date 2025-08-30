export default function replaceClasses(content) {

      //troca nome classes
    content = content.replace(/content-text__container/g, 'text__container');
    content = content.replace(/content-intertitle/g, 'subtitulo');
    content = content.replace(/content-media__description/g, 'descricao__midia');
    content = content.replace(/amp-img/g, 'img');
    content = content.replace(/<\/img>/g, '');
    content = content.replace(/(<img[^>]*class=")[^"]*("[^>]*)>/g, '$1imagem__container$2>');
    content = content.replace(/show-table__container/g, 'tabela');
    content = content.replace(/content-blockquote/g, 'citacao');
    content = content.replace(/content-unordered-list/g, 'lista');
    content = content.replace(/content-media__container/g, 'video__indisponivel');
    content = content.replace(/twitter-tweet/g, 'publi__externa');
    content = content.replace(/show-flourish-embed-block__container/g, 'publi__externa');

    return content;
}