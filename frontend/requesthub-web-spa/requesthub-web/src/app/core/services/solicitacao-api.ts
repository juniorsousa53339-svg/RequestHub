import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { SolicitacaoResponse } from '../models/solicitacao-response';
import {environment} from '../../../environments/environment';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})

export class SolicitacaoApiService {

private readonly baseUrl = `${environment.apiUrl}/solicitacoes`;

  constructor(private http: HttpClient ) { }

 public listarTodas() : Observable<SolicitacaoResponse[]> {
    return this.http.get<SolicitacaoResponse[]>(this.baseUrl);
  }


public deletar(id: string) {
  return this.http.delete<void>(`${this.baseUrl}/${id}`);
}


}

