package com.RequestHub.request_hub.solicitacao.controller;

import com.RequestHub.request_hub.solicitacao.domain.AlterarSolicitacaoRequest;
import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.exception.BusinessException;
import com.RequestHub.request_hub.solicitacao.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class SolicitacaoController {


    private final SolicitacaoService solicitacaoService;



    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public Solicitacao saveSolicitacao(@RequestBody @Valid Solicitacao solicitacao) {
        var savedSolicitacao = solicitacaoService.saveSolicitacao(solicitacao);
        return savedSolicitacao;
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteSolicitacao(
            @RequestParam @Valid UUID id,
            @RequestParam @Valid UUID solicitanteId

    ) throws BusinessException {

        solicitacaoService.deletarSolicitacao(id,solicitanteId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public List<Solicitacao> ListarSolicitacoes(){
        var lista = solicitacaoService.ListarSolicitacoes();
        return lista;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> alterar(
            @PathVariable UUID id,
            @RequestBody AlterarSolicitacaoRequest request

            ) throws BusinessException {

        solicitacaoService.alterarSolicitacao(
                id,
                request.getNome(),
                request.getDescricao()
        );

        return ResponseEntity.ok().build();
    }

     /*
         FALTA IMPLEMENTAR OS METODOS: AlterarStatus && AlterarSolicitacao
      */


}
