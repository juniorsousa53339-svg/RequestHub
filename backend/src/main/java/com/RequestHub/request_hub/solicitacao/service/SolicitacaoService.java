package com.RequestHub.request_hub.solicitacao.service;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import com.RequestHub.request_hub.infrastructure.exception.BusinessException;
import com.RequestHub.request_hub.infrastructure.exception.NotFoundException;
import com.RequestHub.request_hub.solicitacao.dto.CriarSolicitacaoRequest;
import com.RequestHub.request_hub.solicitacao.repository.SolicitacaoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;


    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository
            ) {
        this.solicitacaoRepository =
                solicitacaoRepository;
    }

   public Solicitacao criarSolicitacao(CriarSolicitacaoRequest request, String username) {

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setNome(request.getNome());
        solicitacao.setDescricao(request.getDescricao());


       UUID solicitanteId = UUID.nameUUIDFromBytes(username.getBytes(StandardCharsets.UTF_8));
       solicitacao.setSolicitanteId(solicitanteId);


       return solicitacaoRepository.save(solicitacao);
   }


    public void deletarSolicitacao(UUID id)
            throws BusinessException {

        Solicitacao solicitacao = solicitacaoRepository
                .findById(id)
                        .orElseThrow(()
                                -> new NotFoundException
                                ("Solicitação não encontrada"));


        if (!solicitacao.getStatus().podeExcluir()) {
            throw new BusinessException("Solicitação não pode ser excluída");
        }

        solicitacaoRepository.
                delete(solicitacao);
    }


    public  Solicitacao alterarSolicitacao(

            UUID id ,
            String nome ,
            String descricao

    ) throws BusinessException {

        Solicitacao solicitacao =
                solicitacaoRepository.
                findById(id)
                        .orElseThrow(()
                                -> new NotFoundException
                                ("Solicitação não encontrada")
                        );


        solicitacao.getStatus()
                .validarAlteracao();

        solicitacao.
                alterardados
                        (nome , descricao);

        return  solicitacaoRepository.
                save(solicitacao);
    }

   public List<Solicitacao> ListarSolicitacoes(){
        return solicitacaoRepository.
                findAll();
   }


    public void alterarStatus(UUID id, StatusSolicitacao novoStatus)
            throws BusinessException {

        Solicitacao solicitacao =
                solicitacaoRepository.
                        findById(id)

                .orElseThrow(()
                        -> new NotFoundException
                        ("Solicitação não encontrada"));

        solicitacao.getStatus()
                .validarAlteracao();

        solicitacao.getStatus().
                validarTransicaoPara
                        (novoStatus);

        solicitacao.
                setStatus
                        (novoStatus);

        solicitacaoRepository.
               save(solicitacao);
    }


    public List<Solicitacao> listarMinhas(String username) {
        UUID solicitanteId = UUID.nameUUIDFromBytes(username.getBytes(StandardCharsets.UTF_8));
        return solicitacaoRepository.findBySolicitanteId(solicitanteId);
    }

}



