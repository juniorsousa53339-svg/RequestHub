package com.RequestHub.request_hub.solicitacao.domain;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AlterarSolicitacaoRequest {
    private String nome;
    private String descricao;
}
