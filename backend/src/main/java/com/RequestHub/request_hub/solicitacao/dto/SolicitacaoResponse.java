package com.RequestHub.request_hub.solicitacao.dto;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class SolicitacaoResponse {

    private String nome;
    private String descricao;
    private StatusSolicitacao status;
    private LocalDateTime createdAt;


    public static SolicitacaoResponse fromEntity(Solicitacao s) {
        SolicitacaoResponse r = new SolicitacaoResponse();
        r.setNome(s.getNome());
        r.setDescricao(s.getDescricao());
        r.setStatus(s.getStatus());
        r.setCreatedAt(s.getCreatedAt());
        return r;
    }

}
