package com.RequestHub.request_hub.solicitacao.exception;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
