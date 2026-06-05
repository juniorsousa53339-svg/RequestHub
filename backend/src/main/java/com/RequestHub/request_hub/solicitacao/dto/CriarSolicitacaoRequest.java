package com.RequestHub.request_hub.solicitacao.dto;

import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;

import lombok.Getter;
import lombok.Setter;



@Getter @Setter
public class CriarSolicitacaoRequest {

    private String nome;
    private String descricao;
}
