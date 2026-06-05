package com.RequestHub.request_hub.solicitacao.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AlterarSolicitacaoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;
}
