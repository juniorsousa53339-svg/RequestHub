package com.RequestHub.request_hub.solicitacao.service;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import com.RequestHub.request_hub.infrastructure.exception.BusinessException;
import com.RequestHub.request_hub.infrastructure.exception.NotFoundException;
import com.RequestHub.request_hub.solicitacao.repository.SolicitacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitacaoServiceTest {

    @Mock
    private SolicitacaoRepository solicitacaoRepository;


    @InjectMocks
    private SolicitacaoService solicitacaoService;

    @Test
    void deveLancarNotFound_quandoSolicitacaoNaoExistir() {

        UUID id = UUID.randomUUID();

        when(
                solicitacaoRepository.
                findById(id)).
                thenReturn(Optional.empty()
                );

        assertThrows(NotFoundException.class,
                () -> solicitacaoService.
                        alterarStatus(
                                id,
                                StatusSolicitacao.
                                        EM_ANDAMENTO)
        );

        verify(solicitacaoRepository, never()).save(any());
    }

    @Test
    void deveAlterarStatusESalvar_quandoTransicaoForValida() throws Exception {
        UUID id = UUID.randomUUID();

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(id);
        solicitacao.setStatus(StatusSolicitacao.ABERTA);

        assertEquals(StatusSolicitacao.ABERTA, solicitacao.getStatus());

        when
                (
                solicitacaoRepository.
                        findById(id)).
                thenReturn
                        (Optional.of
                                (solicitacao)
                        );

        solicitacaoService.
                alterarStatus(
                        id,
                        StatusSolicitacao.
                                EM_ANDAMENTO
                );

        ArgumentCaptor<Solicitacao> captor =
                ArgumentCaptor.
                        forClass
                                (Solicitacao.class);

        verify(solicitacaoRepository).
                save(captor.capture());

        assertEquals(StatusSolicitacao.EM_ANDAMENTO,
                captor.getValue().getStatus()
        );
    }

    @Test
    void deveLancarBusinessException_quandoStatusNaoPermitirAlteracao() throws Exception {
        UUID id = UUID.randomUUID();

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(id);
        solicitacao.setStatus(StatusSolicitacao.FINALIZADA);

        assertEquals(
                StatusSolicitacao.FINALIZADA,
                solicitacao.
                        getStatus());

        when
                (
                        solicitacaoRepository.
                                findById(id)).
                thenReturn
                        (Optional.of
                                (solicitacao));

        assertThrows(BusinessException.class,
                () -> solicitacaoService.
                        alterarStatus
                                (id, StatusSolicitacao.EM_ANDAMENTO));

        verify(solicitacaoRepository,
                never()).
                save(any());
    }

    @Test
    void sanity_check_setter_status_funciona() {
        Solicitacao s = new Solicitacao();
        s.setStatus(StatusSolicitacao.FINALIZADA);
        assertEquals(StatusSolicitacao.FINALIZADA, s.getStatus());
    }

    @Test
    void deveAlterarSolicitacaoESalvara() throws Exception {

        UUID id = UUID.randomUUID();

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(id);
        solicitacao.setStatus(StatusSolicitacao.ABERTA);
        solicitacao.setNome("antigo");
        solicitacao.setDescricao("antiga");

        when(solicitacaoRepository.
                findById(id)).
                thenReturn(Optional.of
                        (solicitacao));

        solicitacaoService.alterarSolicitacao(id,"Novo","Nova");

        ArgumentCaptor<Solicitacao> captor =
                ArgumentCaptor.
                        forClass
                                (Solicitacao.class);

        verify(solicitacaoRepository).
                save(captor.capture());


        assertEquals("Novo",
                solicitacao.getNome(),
                "Nome deve ser atualizado");

        assertEquals("Nova",
                captor.getValue().getDescricao(),
                "Descrição deve ser atualizada");

        verify(solicitacaoRepository,
                times(1))
                .save(captor.capture());
    }

    @Test
    void deveLancarNotFound_quandoAlterarSolicitacaoNaoExistir() {

        UUID id = UUID.randomUUID();
        when(solicitacaoRepository.findById(id)).
                thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> solicitacaoService.
                        alterarSolicitacao
                                (id,"Antigo","Antiga"));

        verify(solicitacaoRepository,
                never()).save(any());

        verify(solicitacaoRepository,
                times(1)).
                findById(id);
    }

    @Test
    void deveDeletarSolicitacao() throws Exception {

        UUID id = UUID.randomUUID();

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(id);
        solicitacao.setStatus(StatusSolicitacao.ABERTA);

        when(solicitacaoRepository.
                findById(id)).
                thenReturn(Optional.of
                        (solicitacao));

        solicitacaoService.deletarSolicitacao(id);

        ArgumentCaptor<Solicitacao>
                captor = ArgumentCaptor.
                forClass(Solicitacao.class);

        verify(solicitacaoRepository).
                delete(captor.capture());

        assertEquals(id,captor.getValue().getId());

        verify(solicitacaoRepository,
                times(1))
                .delete(captor.capture());
    }


    @Test
    void deveLancarNotFound_quandoDeletarSolicitacaoNaoExistir() {

        UUID id = UUID.randomUUID();

        when
                (
                        solicitacaoRepository.
                                findById(id)).
                thenReturn(Optional.empty()
                );

        assertThrows(NotFoundException.class,
                () -> solicitacaoService.
                        deletarSolicitacao(id));

        verify(solicitacaoRepository,
                times(1))
                .findById(id);
    }

    @Test
    void deveLancarBusinessException_quandoStatusNaoPermitirExcluir() {

        UUID id = UUID.randomUUID();

         Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(id);
        solicitacao.setStatus(StatusSolicitacao.EM_ANDAMENTO);

        assertEquals(StatusSolicitacao.EM_ANDAMENTO, solicitacao.getStatus());

        when
                (
                        solicitacaoRepository.
                                findById(id)).
                thenReturn(Optional.of(solicitacao)
                );

        assertThrows(BusinessException.class,
                () -> solicitacaoService.deletarSolicitacao(id));

        verify(solicitacaoRepository, never()).delete(any());
    }
}
