package com.RequestHub.request_hub.solicitacao.service;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import com.RequestHub.request_hub.solicitacao.exception.BusinessException;
import com.RequestHub.request_hub.solicitacao.exception.NotFoundException;
import com.RequestHub.request_hub.solicitacao.repository.SolicitacaoRepository;


import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;


    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

   public Solicitacao saveSolicitacao(Solicitacao solicitacao){
       return solicitacaoRepository.save(solicitacao);
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

    @SneakyThrows
    public void alterarStatus(UUID id, StatusSolicitacao novoStatus){

        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        solicitacao.getStatus().validarTransicaoPara(novoStatus);

        solicitacao.setStatus(novoStatus);
    }

    public void alterarSolicitacao(UUID id) throws BusinessException {

        Solicitacao solicitacao = solicitacaoRepository.
                findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Solicitação não encontrada")
                        );
        solicitacao.getStatus().validarAlteracao();

        solicitacao.setNome("Novo nome");
        solicitacao.setDescricao("Nova descricao");

        solicitacaoRepository.save(solicitacao);
    }
}
