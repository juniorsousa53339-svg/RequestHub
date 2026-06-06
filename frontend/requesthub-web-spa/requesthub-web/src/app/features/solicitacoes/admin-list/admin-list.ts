import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Observable, BehaviorSubject, switchMap, finalize } from 'rxjs';

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

  public mensagem: string | null = null;
  public carregando = false;

  // controla qual card está com o menu aberto
  public menuStatusAbertoId: string | null = null;

  // ===== Lista reativa (fluidez) =====
  private refresh$ = new BehaviorSubject<void>(undefined);

  public solicitacoes$: Observable<SolicitacaoResponse[]> = this.refresh$.pipe(
    switchMap(() => this.api.listarTodas())
  );

  constructor(
    private api: SolicitacaoApiService,
    private auth: AuthService,
    private router: Router
  ) {}

  private recarregar(): void {
    this.refresh$.next();
  }

  trocarUsuario(): void {
    this.auth.logout();
    this.mensagem = null;
    this.menuStatusAbertoId = null;
    this.router.navigateByUrl('/login');
  }

  toggleStatusMenu(id: string): void {
    this.mensagem = null;
    this.menuStatusAbertoId = (this.menuStatusAbertoId === id) ? null : id;
  }

  confirmarExcluir(id: string): void {
    this.mensagem = null;

    const ok = confirm('Tem certeza que deseja excluir esta solicitação?');
    if (!ok) return;

    this.carregando = true;

    this.api.deletar(id).pipe(
      finalize(() => this.carregando = false)
    ).subscribe({
      next: () => {
        this.mensagem = 'Solicitação excluída com sucesso.';
        this.recarregar();
      },
      error: (err) => {
        if (err?.status === 409) {
          this.mensagem = 'Não foi possível excluir: a solicitação não está ABERTA.';
          return;
        }
        if (err?.status === 403) {
          this.mensagem = 'Acesso negado: somente ADMIN pode excluir.';
          return;
        }
        this.mensagem = 'Erro ao excluir solicitação.';
      }
    });
  }

  alterarStatus(id: string, novoStatus: StatusSolicitacao): void {
    this.mensagem = null;
    this.menuStatusAbertoId = null; // fecha menu logo ao clicar (UX melhor)

    this.carregando = true;

    this.api.alterarStatus(id, { novoStatus }).pipe(
      finalize(() => this.carregando = false)
    ).subscribe({
      next: () => {
        this.mensagem = 'Status atualizado com sucesso.';
        this.recarregar();
      },
      error: (err) => {
        if (err?.status === 409) {
          this.mensagem = 'Transição inválida (ABERTA → EM_ANDAMENTO → FINALIZADA).';
          return;
        }
        if (err?.status === 403) {
          this.mensagem = 'Acesso negado: somente ADMIN pode alterar status.';
          return;
        }
        this.mensagem = 'Erro ao atualizar status.';
      }
    });
  }
}
