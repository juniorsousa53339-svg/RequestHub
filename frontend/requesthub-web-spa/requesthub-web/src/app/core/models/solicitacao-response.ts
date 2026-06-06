import { StatusSolicitacao } from './status-solicitacao';

export interface SolicitacaoResponse {
  id: string;
  nome: string;
  descricao: string;
  status: 'ABERTA' | 'EM_ANDAMENTO' | 'FINALIZADA';
  createdAt?: string;
}

