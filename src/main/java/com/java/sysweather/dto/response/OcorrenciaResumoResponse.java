package com.java.sysweather.dto.response;

import lombok.*;
import java.time.LocalDateTime;

import com.java.sysweather.model.enums.NivelRisco;
import com.java.sysweather.model.enums.TipoOcorrencia;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcorrenciaResumoResponse {
    private Long id;
    private TipoOcorrencia tipo;
    private NivelRisco nivelRisco;
    private LocalDateTime dataOcorrencia;
}