import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SolicitacaoApiService } from '../../../core/services/solicitacao-api';
import { SolicitacaoResponse } from '../../../core/models/solicitacao-response';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-admin-list',
  imports: [CommonModule],
  templateUrl: './admin-list.html',
  styleUrl: './admin-list.scss',
})
export class AdminListComponent {


public solicitacoes$: Observable<SolicitacaoResponse[]>;

 constructor(private api: SolicitacaoApiService) {

   this.solicitacoes$ = this.api.listarTodas();
}
}

