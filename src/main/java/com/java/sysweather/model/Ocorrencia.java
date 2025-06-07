package com.java.sysweather.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

import com.java.sysweather.model.enums.NivelRisco;
import com.java.sysweather.model.enums.TipoOcorrencia;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da ocorrência é obrigatória.")
    private String descricao;

    @NotNull(message = "O tipo da ocorrência é obrigatório.")
    @Enumerated(EnumType.STRING)
    private TipoOcorrencia tipo;

    @NotNull(message = "O nível de risco é obrigatório.")
    @Enumerated(EnumType.STRING)
    private NivelRisco nivelRisco;

    @NotNull(message = "A data da ocorrência é obrigatória.")
    @PastOrPresent(message = "A data da ocorrência não pode estar no futuro.")
    private LocalDateTime dataOcorrencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;
}