package com.RequestHub.request_hub.solicitacao.domain;

import com.RequestHub.request_hub.solicitacao.exception.BusinessException;

public enum StatusSolicitacao {

    ABERTA,
    EM_ANDAMENTO,
    FINALIZADA;


    public boolean podeExcluir() {
        return this == ABERTA;
    }


    public void validarAlteracao() throws BusinessException {
        if (this == EM_ANDAMENTO || this == ABERTA) {
            throw new BusinessException("Solicitação finalizada não pode ser alterada");
        }
    }

    public void validarTransicaoPara(StatusSolicitacao novoStatus) throws BusinessException {
        if (this.ordinal() + 1 != novoStatus.ordinal()) {
            throw new BusinessException("Transição de status inválida");
        }
    }
}


