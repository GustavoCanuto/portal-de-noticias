import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaginacaoTag } from '../../models/paginacaoTag';
import { environment } from '../../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private readonly API = `${environment.apiUrl}/tag`;

  constructor(private http: HttpClient) {}

  buscarNomeTag(tag: string): Observable<PaginacaoTag>{
    const quantidadeDeTagsParaCarregar = 20;
    let params = new HttpParams();
    params = params.set('size', quantidadeDeTagsParaCarregar);
    params = params.set('nomeTag', tag);
    return this.http.get<PaginacaoTag>(`${this.API}/nomeTag`, {params});
  }
}
