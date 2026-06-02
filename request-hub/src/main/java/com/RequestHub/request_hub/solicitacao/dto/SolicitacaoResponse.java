package com.RequestHub.request_hub.solicitacao.dto;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class SolicitacaoResponse {

    private String nomeSolicitante;
    private String descricaoSolicitante;
    private StatusSolicitacao status;
    private LocalDateTime createdAt;


    public static SolicitacaoResponse fromEntity(Solicitacao s) {
        SolicitacaoResponse r = new SolicitacaoResponse();
        r.setNomeSolicitante(s.getNome());
        r.setDescricaoSolicitante(s.getDescricao());
        r.setStatus(s.getStatus());
        r.setCreatedAt(s.getCreatedAt());
        return r;
    }

}
