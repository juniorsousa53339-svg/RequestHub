package com.RequestHub.request_hub.solicitacao.controller;

import com.RequestHub.request_hub.solicitacao.dto.*;
import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.infrastructure.exception.BusinessException;
import com.RequestHub.request_hub.solicitacao.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {


    private final SolicitacaoService solicitacaoService;



    public SolicitacaoController(
            SolicitacaoService
                    solicitacaoService
    ) {
        this.solicitacaoService = solicitacaoService;
    }


    @PostMapping
    @PreAuthorize("hasRole('SOLICITANTE')")
    public ResponseEntity<SolicitacaoResponse> criar(
            @RequestBody @Valid CriarSolicitacaoRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName(); // "luciano"

        Solicitacao salva = solicitacaoService.criarSolicitacao(request, username);

        return ResponseEntity.status(201).body(SolicitacaoResponse.fromEntity(salva));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable  UUID id

    ) throws BusinessException {

        solicitacaoService.
                deletarSolicitacao
                        (id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<SolicitacaoResponse> ListarSolicitacoes(){

        return solicitacaoService.

                ListarSolicitacoes()
                .stream()
                .map(SolicitacaoResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasRole('SOLICITANTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarSolicitacao(
            @PathVariable UUID id,
            @RequestBody @Valid AlterarSolicitacaoRequest request

            ) throws BusinessException {

        solicitacaoService.alterarSolicitacao
                (
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

    @GetMapping("/auth/me")
    public Map<String, Object> me(Authentication auth) {
        return Map.of("username", auth.getName(),
                "roles",auth.getAuthorities().stream()

                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }


    @GetMapping("/minhas")
    @PreAuthorize("hasRole('SOLICITANTE')")
    public List<SolicitacaoResponse> minhas(Authentication authentication) {
        String username = authentication.getName();

        return solicitacaoService.listarMinhas(username)
                .stream()
                .map(SolicitacaoResponse::fromEntity)
                .toList();
    }


}
