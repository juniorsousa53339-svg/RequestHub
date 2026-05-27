package com.RequestHub.request_hub.solicitacao.service;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.exception.GlobalExceptionHandler;
import com.RequestHub.request_hub.solicitacao.exception.NotFoundException;
import com.RequestHub.request_hub.solicitacao.repository.SolicitacaoRepository;


import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, GlobalExceptionHandler globalExceptionHandler) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.globalExceptionHandler = globalExceptionHandler;
    }


    public void deletarSolicitacao(UUID id , UUID solicitanteId) {

        Solicitacao solicitacao = solicitacaoRepository
                .findByIdAndSolicitanteId(id,solicitanteId)
                        .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));





    }




}
