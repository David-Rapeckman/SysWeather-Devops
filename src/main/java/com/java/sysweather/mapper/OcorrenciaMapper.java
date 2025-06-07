package com.java.sysweather.mapper;

import com.java.sysweather.dto.response.OcorrenciaResponse;
import com.java.sysweather.dto.response.OcorrenciaResumoResponse;
import com.java.sysweather.model.Ocorrencia;

public class OcorrenciaMapper {

    public static OcorrenciaResponse toResponse(Ocorrencia ocorrencia) {
        return OcorrenciaResponse.builder()
            .id(ocorrencia.getId())
            .descricao(ocorrencia.getDescricao())
            .tipo(ocorrencia.getTipo())
            .nivelRisco(ocorrencia.getNivelRisco())
            .dataOcorrencia(ocorrencia.getDataOcorrencia())
            .municipio(MunicipioMapper.toResumo(ocorrencia.getMunicipio()))
            .build();
    }

    public static OcorrenciaResumoResponse toResumo(Ocorrencia ocorrencia) {
        return OcorrenciaResumoResponse.builder()
            .id(ocorrencia.getId())
            .tipo(ocorrencia.getTipo())
            .nivelRisco(ocorrencia.getNivelRisco())
            .build();
    }
}
