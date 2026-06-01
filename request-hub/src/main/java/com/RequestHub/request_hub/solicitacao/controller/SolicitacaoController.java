package com.RequestHub.request_hub.solicitacao.controller;

import com.RequestHub.request_hub.solicitacao.dto.AlterarSolicitacaoRequest;
import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.dto.AlterarStatusSolicitacaoRequest;
import com.RequestHub.request_hub.infrastructure.exception.BusinessException;
import com.RequestHub.request_hub.solicitacao.dto.CriarSolicitacaoRequest;
import com.RequestHub.request_hub.solicitacao.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {


    private final SolicitacaoService solicitacaoService;



    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PreAuthorize("hasRole('SOLICITANTE')")
    @PostMapping
    public ResponseEntity<Solicitacao> criar(@RequestBody @Valid CriarSolicitacaoRequest
                                                         criarSolicitacaoRequest) {

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setNome(criarSolicitacaoRequest.getNome());
        solicitacao.setDescricao(criarSolicitacaoRequest.getDescricao());

        Solicitacao salva = solicitacaoService.saveSolicitacao(solicitacao);
        return ResponseEntity.status(201).body(salva);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id


    ) throws BusinessException {

        solicitacaoService.deletarSolicitacao(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Solicitacao> ListarSolicitacoes(){
        var lista = solicitacaoService.ListarSolicitacoes();
        return lista;
    }

    @PreAuthorize("hasRole('SOLICITANTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarSolicitacao(
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity <Void> alterarStatus(

            @PathVariable UUID id,
            @RequestBody @Valid AlterarStatusSolicitacaoRequest request

            ) throws BusinessException {

        solicitacaoService.alterarStatus(
                id,
                request.getNovoStatus()
        );

        return ResponseEntity.ok().build();
    }

}
