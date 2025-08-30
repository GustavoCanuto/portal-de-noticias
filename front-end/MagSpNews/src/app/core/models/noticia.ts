export interface Noticia {
  id: number
  siteBuscado: string,
  linkDaNoticiaOficial: String,
  categoria: string,
  autoria: string,
  dataPublicacao: string,
  dataModificada: string,
  sinopse?: String,
  titulo: string,
  numeroVisualizacao: number,
  tags: Tag[]
  conteudo: string,
}

export interface Tag {
  nome: string,
}
