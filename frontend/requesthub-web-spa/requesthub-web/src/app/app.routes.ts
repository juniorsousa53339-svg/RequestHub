import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';
import { AdminListComponent } from './features/solicitacoes/admin-list/admin-list';
import { MinhasSolicitacoesComponent } from './features/solicitacoes/minhas-solicitacoes/minhas-solicitacoes';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  { path: 'admin/solicitacoes', component: AdminListComponent },

  { path: 'solicitacoes/minhas', component: MinhasSolicitacoesComponent },
  { path: 'solicitacoes/nova', redirectTo: 'solicitacoes/minhas', pathMatch: 'full' },

  { path: '**', redirectTo: 'login' },
];
