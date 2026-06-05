import { Injectable } from '@angular/core';

export type UserRole = 'ADMIN' | 'SOLICITANTE';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private username: string | null = null;
  private password: string | null = null;

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

  getAuthorizationHeader(): string | null {
    if (!this.isLoggedIn()) return null;

    const token = btoa(`${this.username}:${this.password}`);
    return `Basic ${token}`;
  }

  getRole(): UserRole | null {
    if (!this.isLoggedIn()) return null;
    return this.username === 'admin' ? 'ADMIN' : 'SOLICITANTE';
  }
}

