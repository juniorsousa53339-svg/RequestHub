package com.RequestHub.request_hub.solicitacao.repository;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SolicitacaoRepository extends JpaRepository<Solicitacao, UUID> {

  /*
    Solicitante listar suas solicitações
   */
  List<Solicitacao> findBySolicitanteId(UUID solicitanteId);

  /*
    Admin listar por status
   */
  List<Solicitacao> findByStatus(StatusSolicitacao status);

    /*
    Buscar solicitação específica do solicitante
       */
    Optional<Solicitacao> findByIdAndSolicitanteId(UUID id, UUID solicitanteId);




}
