package com.RequestHub.request_hub.solicitacao.service;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.exception.BusinessException;
import com.RequestHub.request_hub.solicitacao.exception.NotFoundException;
import com.RequestHub.request_hub.solicitacao.repository.SolicitacaoRepository;



import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;


    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }


    public void deletarSolicitacao(UUID id , UUID solicitanteId) throws BusinessException {

        Solicitacao solicitacao = solicitacaoRepository
                .findByIdAndSolicitanteId(id,solicitanteId)
                        .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));


        if (!solicitacao.getStatus().podeExcluir()) {
            throw new BusinessException("Solicitação não pode ser excluída");
        }

        solicitacaoRepository.delete(solicitacao);
    }




}
