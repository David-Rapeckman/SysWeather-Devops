package com.java.sysweather.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MunicipioSimplesResponse {
    private Long id;
    private String nome;
    private String estado;
    private Integer numeroHabitantes;
    private String clima;
    private String regiao;
    private Double altitude;
    private Double areaKm2;
}