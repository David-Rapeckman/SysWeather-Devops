package com.java.sysweather.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioSimplesResponse {
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String email;
    private LocalDateTime dataCadastro;
}