import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';
import { AdminListComponent } from './features/solicitacoes/admin-list/admin-list';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  { path: 'admin/solicitacoes', component: AdminListComponent },

  // placeholder do solicitante (a gente cria logo em seguida)
  // { path: 'solicitacoes/nova', component: CreateComponent },

  { path: '**', redirectTo: 'login' },
];
