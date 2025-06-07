package com.java.sysweather.dto.response;

import java.util.List;

import com.java.sysweather.model.enums.Clima;
import com.java.sysweather.model.enums.Estado;
import com.java.sysweather.model.enums.Regiao;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MunicipioDetalhadoResponse {
    private Long id;
    private String nome;
    private Estado estado;
    private Integer numero_habitantes;
    private Clima clima;
    private Regiao regiao;
    private Double altitude;
    private Double areaKm2;
    private List<UsuarioSimplesResponse> usuarios;
}