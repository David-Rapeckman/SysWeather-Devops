package com.java.sysweather.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private LocalDateTime dataCadastro;

    private MunicipioResumoResponse municipio;
}