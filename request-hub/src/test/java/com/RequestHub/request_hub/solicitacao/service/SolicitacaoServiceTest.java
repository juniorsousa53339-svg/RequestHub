package com.RequestHub.request_hub.solicitacao.service;

import com.RequestHub.request_hub.solicitacao.domain.Solicitacao;
import com.RequestHub.request_hub.solicitacao.domain.StatusSolicitacao;
import com.RequestHub.request_hub.solicitacao.exception.NotFoundException;
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
        // Arrange
        UUID id = UUID.randomUUID();
        when(solicitacaoRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert (exceção)
        assertThrows(NotFoundException.class,
                () -> solicitacaoService.alterarStatus(id, StatusSolicitacao.EM_ANDAMENTO));

        // Assert (interação)
        verify(solicitacaoRepository, never()).save(any());
    }

    @Test
    void deveAlterarStatusESalvar_quandoTransicaoForValida() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(id);
        solicitacao.setStatus(StatusSolicitacao.ABERTA);

        when(solicitacaoRepository.findById(id)).thenReturn(Optional.of(solicitacao));

        solicitacaoService.alterarStatus(id, StatusSolicitacao.EM_ANDAMENTO);

        // Assert (capturar o que foi salvo)
        ArgumentCaptor<Solicitacao> captor = ArgumentCaptor.forClass(Solicitacao.class);
        verify(solicitacaoRepository).save(captor.capture());

        Solicitacao salvo = captor.getValue();
        assertEquals(StatusSolicitacao.EM_ANDAMENTO, salvo.getStatus());
    }

}
