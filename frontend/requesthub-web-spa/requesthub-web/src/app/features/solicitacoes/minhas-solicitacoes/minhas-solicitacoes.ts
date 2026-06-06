import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Observable, BehaviorSubject, switchMap, finalize } from 'rxjs';

import { SolicitacaoApiService } from '../../../core/services/solicitacao-api';
import { SolicitacaoResponse } from '../../../core/models/solicitacao-response';
import { AuthService } from '../../../core/services/auth';
import { CriarSolicitacaoRequest } from '../../../core/models/criar-solicitacao-request';
import { AlterarSolicitacaoRequest } from '../../../core/models/alterar-solicitacao-request';

@Component({
  selector: 'app-minhas-solicitacoes',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './minhas-solicitacoes.html',
  styleUrls: ['./minhas-solicitacoes.scss'],
})
export class MinhasSolicitacoesComponent {

  // ===== Estado (UX) =====
  public mensagem: string | null = null;
  public carregando = false;

  // ===== Criar =====
  public mostrarFormCriar = false;
  public novo: CriarSolicitacaoRequest = { nome: '', descricao: '' };

  // ===== Editar inline =====
  public editId: string | null = null;
  public editModel: AlterarSolicitacaoRequest = { nome: '', descricao: '' };

  // ===== Lista reativa (o "segredo" da fluidez) =====
  private refresh$ = new BehaviorSubject<void>(undefined);

  public solicitacoes$: Observable<SolicitacaoResponse[]> = this.refresh$.pipe(
    switchMap(() => this.api.minhas())
  );

  constructor(
    private api: SolicitacaoApiService,
    private auth: AuthService,
  ) {}

  private recarregar(): void {
    this.refresh$.next();
  }

  trocarUsuario(): void {
    this.auth.logout();
  }

  toggleCriar(): void {
    this.mensagem = null;
    this.mostrarFormCriar = !this.mostrarFormCriar;

    if (this.mostrarFormCriar) {
      this.novo = { nome: '', descricao: '' };
    }
  }

  criar(): void {
    this.mensagem = null;

    if (!this.novo.nome.trim() || !this.novo.descricao.trim()) {
      this.mensagem = 'Preencha nome e descrição.';
      return;
    }

    this.carregando = true;

    this.api.criar(this.novo).pipe(
      finalize(() => this.carregando = false)
    ).subscribe({
      next: () => {
        this.mostrarFormCriar = false;
        this.mensagem = 'Solicitação criada com sucesso!';
        this.recarregar();
      },
      error: (err) => {
        if (err?.status === 401) {
          this.mensagem = 'Não autenticado. Faça login novamente.';
          return;
        }
        if (err?.status === 403) {
          this.mensagem = 'Acesso negado: somente SOLICITANTE pode criar.';
          return;
        }
        this.mensagem = 'Erro ao criar solicitação.';
      }
    });
  }

  iniciarEdicao(s: SolicitacaoResponse): void {
    this.mensagem = null;
    this.editId = s.id;
    this.editModel = { nome: s.nome, descricao: s.descricao };
  }

  cancelarEdicao(): void {
    this.editId = null;
  }

  salvarEdicao(id: string): void {
    this.mensagem = null;

    if (!this.editModel.nome.trim() || !this.editModel.descricao.trim()) {
      this.mensagem = 'Nome e descrição não podem ficar vazios.';
      return;
    }

    this.carregando = true;

    this.api.alterarDados(id, this.editModel).pipe(
      finalize(() => this.carregando = false)
    ).subscribe({
      next: () => {
        this.editId = null;
        this.mensagem = 'Solicitação atualizada com sucesso!';
        this.recarregar();
      },
      error: (err) => {
        if (err?.status === 409) {
          this.mensagem = 'Não é possível alterar: solicitação já finalizada.';
          return;
        }
        if (err?.status === 403) {
          this.mensagem = 'Acesso negado.';
          return;
        }
        this.mensagem = 'Erro ao alterar solicitação.';
      }
    });
  }

  podeEditar(s: SolicitacaoResponse): boolean {
    return s.status === 'ABERTA';
  }
}
