package com.RequestHub.request_hub.solicitacao.dto;

import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class AlterarStatusSolicitacaoRequest{
    private StatusSolicitacao novoStatus;
}
