import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
})
export class LoginComponent {
  username = '';
  password = '';
  error: string | null = null;
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    this.error = null;

    if (!this.username.trim() || !this.password.trim()) {
      this.error = 'Informe usuário e senha.';
      return;
    }

    this.loading = true;

    // 1) guarda credencial (o interceptor vai usar)
    this.auth.login(this.username, this.password);

    // 2) valida no backend
    this.auth.me().subscribe({
      next: (me) => {
        const role = AuthService.resolveRole(me);

        // 3) navega só quando a credencial é válida
        if (role === 'ADMIN') {
          this.router.navigateByUrl('/admin/solicitacoes');
        } else {
          this.router.navigateByUrl('/solicitacoes/nova');
        }

        this.loading = false;
      },
      error: (err) => {
        // 4) senha errada -> 401
        if (err.status === 401) {
          this.auth.logout();
          this.error = 'Usuário ou senha inválidos.';
        } else {
          this.error = 'Erro ao validar login.';
        }
        this.loading = false;
      }
    });
  }
}
``
