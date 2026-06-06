import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Router, RouterModule } from '@angular/router';

import { SolicitacaoApiService } from '../../../core/services/solicitacao-api';
import { SolicitacaoResponse } from '../../../core/models/solicitacao-response';
import { AuthService } from '../../../core/services/auth';

type StatusSolicitacao = 'ABERTA' | 'EM_ANDAMENTO' | 'FINALIZADA';

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

  // controla qual card está com o menu aberto
  public menuStatusAbertoId: string | null = null;

  constructor(
    private api: SolicitacaoApiService,
    private auth: AuthService,
    private router: Router
  ) {
    this.solicitacoes$ = this.api.listarTodas();
  }

  private recarregar(): void {
    this.solicitacoes$ = this.api.listarTodas();
  }

  trocarUsuario(): void {
    this.auth.logout();
    this.menuStatusAbertoId = null;
    this.router.navigateByUrl('/login');
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

  toggleStatusMenu(id: string): void {
    this.mensagem = null;
    this.menuStatusAbertoId = (this.menuStatusAbertoId === id) ? null : id;
  }

  alterarStatus(id: string, novoStatus: StatusSolicitacao): void {
    this.mensagem = null;

    this.api.alterarStatus(id, { novoStatus }).subscribe({
      next: () => {
        this.menuStatusAbertoId = null; // fecha o menu
        this.recarregar();              // atualiza card e badge
        this.mensagem = 'Status atualizado com sucesso.';
      },
      error: (err) => {
        this.menuStatusAbertoId = null;

        if (err.status === 409) {
          this.mensagem = 'Transição inválida de status (siga a ordem ABERTA → EM_ANDAMENTO → FINALIZADA).';
          return;
        }
        if (err.status === 403) {
          this.mensagem = 'Acesso negado: somente ADMIN pode alterar status.';
          return;
        }
        this.mensagem = 'Erro ao atualizar status.';
      }
    });
  }
}
