package com.RequestHub.request_hub.solicitacao.service;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import com.RequestHub.request_hub.solicitacao.exception.BusinessException;
import com.RequestHub.request_hub.solicitacao.exception.NotFoundException;
import com.RequestHub.request_hub.solicitacao.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public void alterarStatus(UUID id, StatusSolicitacao novoStatus) throws BusinessException {

        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        solicitacao.getStatus().validarTransicaoPara(novoStatus);

        solicitacao.setStatus(novoStatus);
    }

    public  Solicitacao alterarSolicitacao(UUID id, String nomeAtual) throws BusinessException {

        Solicitacao solicitacao = solicitacaoRepository.
                findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Solicitação não encontrada")
                        );
        solicitacao.getStatus().validarAlteracao();

        solicitacao.setNome("Novo nome");
        solicitacao.setDescricao("Nova descricao");

        solicitacaoRepository.save(solicitacao);

        return  solicitacao;
    }

   public List<Solicitacao> ListarSolicitacoes(){
        return solicitacaoRepository.findAll();
   }

}
