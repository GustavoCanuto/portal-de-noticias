import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PaginacaoPrevia } from '../../models/paginacaoPrevia';
import { Observable } from 'rxjs';
import { Noticia } from '../../models/noticia';
import { environment } from '../../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class NoticiaService {
  private readonly API = `${environment.apiUrl}/noticia`;

  constructor(private http: HttpClient) {}

  listarTodasPrevias(pagina: number): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 10;
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.set('size', quantidadePorPagina);
    params = params.set('page', pagina);
    return this.http.get<PaginacaoPrevia>(this.API, { params });
  }

  listarPreviasMaisLidas(pagina: number): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 10;
    let params = new HttpParams();
    params = params.set('size', quantidadePorPagina);
    params = params.set('page', pagina);
    return this.http.get<PaginacaoPrevia>(`${this.API}/mais-lidas`);
  }

  listarPreviasUltimasNoticias(
    pagina: number
  ): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 10;
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.set('size', quantidadePorPagina);
    params = params.set('page', pagina);
    return this.http.get<PaginacaoPrevia>(`${this.API}/data-publicacao`, {
      params,
    });
  }

  listarPreviasPrincipaisNoticias(pagina: number): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 15;
    let params = new HttpParams();
    params = params.set('size', quantidadePorPagina);
    params = params.set('page', pagina);
    return this.http.get<PaginacaoPrevia>(`${this.API}/principais`, { params });
  }

  exibirNoticiaCompletaPorId(idNoticia: number): Observable<Noticia> {
    return this.http.get<Noticia>(`${this.API}/${idNoticia}`);
  }

  exibirNoticiaCompletaPorParametrosLink(
    siteBuscado: string,
    dataPublicacao: string,
    tituloNoticia: string
  ): Observable<Noticia> {
    let params = new HttpParams().set('titulo', tituloNoticia);
    return this.http.get<Noticia>(
      `${this.API}/${siteBuscado}/${dataPublicacao}`, {params}
    );
  }

  buscarNoticiaPorTag(
    pagina: number,
    tag: string,
    fimPeriodo: string,
    inicioPeriodo?: string
  ): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 5;
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.set('size', quantidadePorPagina);
    params = params.append('page', pagina);
    params = params.set('nomeTag', tag);
    params = params.set('fimPeriodo', fimPeriodo);
    if (inicioPeriodo) params = params.set('inicioPeriodo', inicioPeriodo);
    return this.http.get<PaginacaoPrevia>(`${this.API}/tag/nomeTag`, { params });
  }

  buscarNoticiaPorTitulo(
    pagina: number,
    palavra: string,
    fimPeriodo: string,
    inicioPeriodo?: string
  ): Observable<PaginacaoPrevia> {

    const quantidadePorPagina = 10;
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.set('size', quantidadePorPagina);
    params = params.append('page', pagina);
    params = params.set('palavra', palavra);
    params = params.set('fimPeriodo', fimPeriodo);
    if (inicioPeriodo) params = params.set('inicioPeriodo', inicioPeriodo);

    return this.http.get<PaginacaoPrevia>(`${this.API}/titulo`, { params });
  }

  buscarNoticiaPorCategoria(
    pagina: number,
    fimPeriodo: string,
    categoria?: string,
    categorias?: string[],
    inicioPeriodo?: string
  ): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 10;
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.set('size', quantidadePorPagina);
    params = params.append('page', pagina);
    if (categoria) {
      params = params.append('categorias', categoria);
    }
    if (categorias) {
      categorias.forEach((categoria) => {
        params = params.append('categorias', categoria);
      });
    }
    params = params.set('fimPeriodo', fimPeriodo);
    if (inicioPeriodo) params = params.set('inicioPeriodo', inicioPeriodo);
    return this.http.get<PaginacaoPrevia>(`${this.API}/categoria`, { params });
  }

  buscarPorSiteFonte(
    pagina: number,
    site: string,
    fimPeriodo: string,
    inicioPeriodo?: string,
  ): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 10;
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.set('size', quantidadePorPagina);
    params = params.append('page', pagina);
    params = params.set('fimPeriodo', fimPeriodo);
    if (inicioPeriodo) params = params.set('inicioPeriodo', inicioPeriodo);

    return this.http.get<PaginacaoPrevia>(`${this.API}/site/${site}`, {
      params,
    });
  }

  buscarPelaSinopse(
    pagina: number,
    palavra: string,
    fimPeriodo: string,
    inicioPeriodo?: string
  ): Observable<PaginacaoPrevia> {
    const quantidadePorPagina = 10;
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.set('size', quantidadePorPagina);
    params = params.append('page', pagina);
    params = params.append('palavra', palavra);
    params = params.set('fimPeriodo', fimPeriodo);
    if (inicioPeriodo) params = params.set('inicioPeriodo', inicioPeriodo);

    return this.http.get<PaginacaoPrevia>(`${this.API}/sinopse`, { params });
  }

  buscarPeloConteudo(
    pagina: number,
    palavra: string,
    fimPeriodo: string,
    inicioPeriodo?: string
  ): Observable<PaginacaoPrevia> {
    let params = new HttpParams();
    params = params.set('sort', 'dataPublicacao,desc');
    params = params.append('page', pagina);
    params = params.append('palavra', palavra);
    params = params.set('fimPeriodo', fimPeriodo);
    if (inicioPeriodo) params = params.set('inicioPeriodo', inicioPeriodo);

    return this.http.get<PaginacaoPrevia>(`${this.API}/conteudo`, { params });
  }
}
