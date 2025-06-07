package com.java.sysweather.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacaoOcorrenciaResponse {
    private Long id;
    private String mensagem;
    private LocalDateTime dataEnvio;

    private UsuarioResumoResponse usuario;
    private OcorrenciaResumoResponse ocorrencia;
}