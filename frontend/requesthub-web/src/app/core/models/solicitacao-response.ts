import { StatusSolicitacao } from './status-solicitacao';

export interface SolicitacaoResponse {

id: string;
nome: string;
descricao: string;
status: StatusSolicitacao;
createdAt: string;
}
