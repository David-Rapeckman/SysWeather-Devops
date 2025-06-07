package com.java.sysweather.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MunicipioResumoResponse {
    private Long id;
    private String nome;
    private String estado;
}