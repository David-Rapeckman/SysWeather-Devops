package com.java.sysweather.dto.response;

import java.time.LocalDateTime;

import com.java.sysweather.model.enums.NivelRisco;
import com.java.sysweather.model.enums.TipoOcorrencia;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcorrenciaResponse {
    private Long id;
    private String descricao;
    private TipoOcorrencia tipo;
    private NivelRisco nivelRisco;
    private LocalDateTime dataOcorrencia;

    private MunicipioResumoResponse municipio; // resumo, sem os usuários
}
