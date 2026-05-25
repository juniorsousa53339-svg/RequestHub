package com.RequestHub.request_hub.solicitacao.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @NotBlank
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotBlank
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
