package com.RequestHub.request_hub.solicitacao.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "solicitacoes")
@NoArgsConstructor
@Getter @Setter
public class Solicitacao {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;


    @Column(nullable = false)
    private UUID solicitanteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @NotBlank
    private String descricao;

   @NotNull
   @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void setStatus(StatusSolicitacao novoStatus) {

    }

    public Solicitacao orElseThrow(Object solicitaçãoNãoEncontrada) {
        return null;
    }

    public  void  alterardados(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
