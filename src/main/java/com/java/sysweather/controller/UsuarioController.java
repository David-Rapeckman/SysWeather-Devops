package com.java.sysweather.controller;

import com.java.sysweather.dto.response.UsuarioDetalhadoResponse;
import com.java.sysweather.dto.response.UsuarioResponse;
import com.java.sysweather.mapper.UsuarioMapper;
import com.java.sysweather.model.Usuario;
import com.java.sysweather.repository.UsuarioRepository;
import com.java.sysweather.service.UsuarioService;
import com.java.sysweather.specification.UsuarioSpecification;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public record UsuarioFilters(
    String municipioNome,
    LocalDate dataCadastro,
    LocalDate dataCadastroInicio,
    LocalDate dataCadastroFim,
    LocalDate dataNascimento,
    LocalDate dataNascimentoInicio,
    LocalDate dataNascimentoFim
    ) {}

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Cacheable(value = "usuarios")
    public Page<UsuarioResponse> index(
            @ModelAttribute UsuarioFilters filters,
            @PageableDefault(size = 10) Pageable pageable) {

        var specification = UsuarioSpecification.withFilters(filters);
        Page<Usuario> usuarios = usuarioRepository.findAll(specification, pageable);
        return usuarios.map(UsuarioMapper::toResponse);
    }

    @PostMapping
    @CacheEvict(value = "usuarios", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para cadastrar um usuário no sistema.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de usuário",
                summary = "Exemplo de um usuário.",
                value = """
                    {
                        "nome": "Teste",
                        "email": "teste@gmail.com",
                        "senha": "senha123",
                        "cpf": "12345678901",
                        "dataNascimento": "2000-01-22",
                        "municipio": {
                            "id": 1
                        }
                    }
                """
            )
        )
    )
    public UsuarioResponse create(@RequestBody @Valid Usuario usuario) {
        Usuario saved = usuarioService.saveUsuario(usuario);
        return UsuarioMapper.toResponse(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDetalhadoResponse> get(@PathVariable Long id) {
        Usuario usuario = getUsuario(id);
        return ResponseEntity.ok(UsuarioMapper.toDetalhado(usuario));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "usuarios", allEntries = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para alterar um usuário no sistema.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de usuário",
                summary = "Exemplo de um usuário.",
                value = """
                    {
                        "nome": "AlterarTeste",
                        "email": "teste@gmail.com",
                        "senha": "senha123",
                        "cpf": "12345678901",
                        "municipio": {
                            "id": 1
                        }
                    }
                """
            )
        )
    )
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        Usuario updated = usuarioService.updateUsuario(id, usuario);
        return ResponseEntity.ok(UsuarioMapper.toResponse(updated));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Usuario usuario = getUsuario(id);
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }

    private Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }
}