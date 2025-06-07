package com.java.sysweather.mapper;

import com.java.sysweather.dto.response.MunicipioResumoResponse;
import com.java.sysweather.dto.response.MunicipioDetalhadoResponse;
import com.java.sysweather.dto.response.MunicipioSimplesResponse;
import com.java.sysweather.dto.response.UsuarioSimplesResponse;
import com.java.sysweather.model.Municipio;
import com.java.sysweather.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class MunicipioMapper {

    public static MunicipioResumoResponse toResumo(Municipio municipio) {
        return MunicipioResumoResponse.builder()
            .id(municipio.getId())
            .nome(municipio.getNome())
            .estado(municipio.getEstado().name())
            .build();
    }

    public static MunicipioDetalhadoResponse toDetalhado(Municipio municipio) {
        List<UsuarioSimplesResponse> usuarios = municipio.getUsuarios().stream()
            .map(MunicipioMapper::toUsuarioSimples)
            .collect(Collectors.toList());

        return new MunicipioDetalhadoResponse(
            municipio.getId(),
            municipio.getNome(),
            municipio.getEstado(),
            municipio.getNumero_habitantes(),
            municipio.getClima(),
            municipio.getRegiao(),
            municipio.getAltitude(),
            municipio.getAreaKm2(),
            usuarios
        );
    }

    public static MunicipioSimplesResponse toSimples(Municipio municipio) {
        return MunicipioSimplesResponse.builder()
            .id(municipio.getId())
            .nome(municipio.getNome())
            .estado(municipio.getEstado().name())
            .numeroHabitantes(municipio.getNumero_habitantes())
            .clima(municipio.getClima().name())
            .regiao(municipio.getRegiao().name())
            .altitude(municipio.getAltitude())
            .areaKm2(municipio.getAreaKm2())
            .build();
    }

    private static UsuarioSimplesResponse toUsuarioSimples(Usuario usuario) {
        return new UsuarioSimplesResponse(
            usuario.getNome(),
            usuario.getDataNascimento(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getDataCadastro()
        );
    }
}