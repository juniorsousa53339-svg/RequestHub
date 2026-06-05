import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export type UserRole = 'ADMIN' | 'SOLICITANTE';

export interface AuthMeResponse {
  username: string;
  roles: string[]; // ex: ["ROLE_ADMIN"] ou ["ROLE_SOLICITANTE"]
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private username: string | null = null;
  private password: string | null = null;

  constructor(private http: HttpClient) {}

  /** 1) "Login" no Basic Auth = guardar credenciais localmente */
  login(username: string, password: string): void {
    this.username = username;
    this.password = password;
  }

  logout(): void {
    this.username = null;
    this.password = null;
  }

  isLoggedIn(): boolean {
    return !!this.username && !!this.password;
  }

  /** 2) Header usado pelo interceptor em TODAS as requests */
  getAuthorizationHeader(): string | null {
    if (!this.isLoggedIn()) return null;
    const token = btoa(`${this.username}:${this.password}`);
    return `Basic ${token}`;
  }

  /** 3) Validação REAL do login: chama o backend */
  me(): Observable<AuthMeResponse> {
    return this.http.get<AuthMeResponse>(`${environment.apiUrl}/solicitacoes/auth/me`);
  }

  /** 4) Helper simples: extrai role do retorno do /auth/me */
  static resolveRole(me: AuthMeResponse): UserRole {
    return me.roles.includes('ROLE_ADMIN') ? 'ADMIN' : 'SOLICITANTE';
  }
}
