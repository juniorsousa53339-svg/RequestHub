import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { RouterModule } from '@angular/router';

import { SolicitacaoApiService } from '../../../core/services/solicitacao-api';
import { SolicitacaoResponse } from '../../../core/models/solicitacao-response';
import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-admin-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-list.html',
  styleUrls: ['./admin-list.scss'],
})
export class AdminListComponent {
  public solicitacoes$: Observable<SolicitacaoResponse[]>;
  public mensagem: string | null = null;

  constructor(
    private api: SolicitacaoApiService,
    private auth: AuthService
  ) {
    this.solicitacoes$ = this.api.listarTodas();
  }

  private recarregar(): void {
    this.solicitacoes$ = this.api.listarTodas();
  }

  trocarUsuario(): void {
    this.auth.logout();
    // o routerLink já navega; aqui só limpa credencial
  }

  confirmarExcluir(id: string): void {
    this.mensagem = null;

    const ok = confirm('Tem certeza que deseja excluir esta solicitação?');
    if (!ok) return;

    this.api.deletar(id).subscribe({
      next: () => {
        this.recarregar();
        this.mensagem = 'Solicitação excluída com sucesso.';
      },
      error: (err) => {
        if (err.status === 409) {
          this.mensagem = 'Não foi possível excluir: a solicitação não está ABERTA.';
          return;
        }
        if (err.status === 403) {
          this.mensagem = 'Acesso negado: somente ADMIN pode excluir.';
          return;
        }
        this.mensagem = 'Erro ao excluir solicitação.';
      }
    });
  }
}
