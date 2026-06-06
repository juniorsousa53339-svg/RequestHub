import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

import { SolicitacaoResponse } from '../models/solicitacao-response';
import { CriarSolicitacaoRequest } from '../models/criar-solicitacao-request';
import { AlterarSolicitacaoRequest } from '../models/alterar-solicitacao-request';
import { AlterarStatusRequest } from '../models/alterar-status-request';

@Injectable({ providedIn: 'root' })
export class SolicitacaoApiService {
  private readonly baseUrl = `${environment.apiUrl}/solicitacoes`;

  constructor(private http: HttpClient) {}

  /** ADMIN: listar todas */
  public listarTodas(): Observable<SolicitacaoResponse[]> {
    return this.http.get<SolicitacaoResponse[]>(this.baseUrl);
  }

public minhas(): Observable<SolicitacaoResponse[]> {
    return this.http.get<SolicitacaoResponse[]>(`${this.baseUrl}/minhas`);
  }

  /** SOLICITANTE: criar */
  public criar(payload: CriarSolicitacaoRequest): Observable<SolicitacaoResponse> {
    return this.http.post<SolicitacaoResponse>(this.baseUrl, payload);
  }

  /** SOLICITANTE: editar nome/descrição */
  public alterarDados(id: string, payload: AlterarSolicitacaoRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, payload);
  }

  /** ADMIN: alterar status */
  public alterarStatus(id: string, payload: AlterarStatusRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}/status`, payload);
  }

  /** ADMIN: deletar */
  public deletar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
