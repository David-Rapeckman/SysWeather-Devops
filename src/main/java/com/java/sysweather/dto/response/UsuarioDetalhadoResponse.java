package com.java.sysweather.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDetalhadoResponse {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private LocalDateTime dataCadastro;
    private MunicipioSimplesResponse municipio;
}