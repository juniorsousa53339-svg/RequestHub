package com.RequestHub.request_hub.solicitacao.dto;


import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CriarSolicitacaoResponse {

    private String nome;
    private String descricao;




    public static CriarSolicitacaoResponse fromEntity(Solicitacao s) {
        CriarSolicitacaoResponse r = new CriarSolicitacaoResponse();

        r.setNome(s.getNome());
        r.setDescricao(s.getDescricao());
        return r;
    }

}
