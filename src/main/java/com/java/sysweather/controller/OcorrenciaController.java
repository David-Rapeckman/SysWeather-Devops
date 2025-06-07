package com.java.sysweather.controller;

import com.java.sysweather.dto.response.OcorrenciaResponse;
import com.java.sysweather.mapper.OcorrenciaMapper;
import com.java.sysweather.model.Ocorrencia;
import com.java.sysweather.model.enums.NivelRisco;
import com.java.sysweather.model.enums.TipoOcorrencia;
import com.java.sysweather.repository.OcorrenciaRepository;
import com.java.sysweather.service.OcorrenciaService;
import com.java.sysweather.specification.OcorrenciaSpecification;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
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
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    public record OcorrenciaFilters(
    String municipioNome,
    NivelRisco nivelRisco,
    TipoOcorrencia tipo
    ) {}

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @GetMapping
    @Cacheable(value = "ocorrencias")
    public Page<OcorrenciaResponse> index(
        @ModelAttribute OcorrenciaFilters filters,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ocorrenciaRepository.findAll(OcorrenciaSpecification.withFilters(filters), pageable)
            .map(OcorrenciaMapper::toResponse);
    }

    @PostMapping
    @CacheEvict(value = "ocorrencias", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para cadastrar uma ocorrência em um município do sistema.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de uma ocorrência",
                summary = "Exemplo de uma ocorrência.",
                value = """
                    {
                        "descricao": "Deslizamento na região sul",
                        "tipo": "DESLIZAMENTO",
                        "nivelRisco": "MEDIO",
                        "dataOcorrencia": "2025-06-01T20:35:00",
                        "municipio": {
                            "id": 4
                        }
                    }
                """
            )
        )
    )
    public OcorrenciaResponse create(@RequestBody @Valid Ocorrencia ocorrencia) {
        Ocorrencia nova = ocorrenciaService.saveOcorrencia(ocorrencia);
        return OcorrenciaMapper.toResponse(nova);
    }

    @GetMapping("{id}")
    public ResponseEntity<OcorrenciaResponse> get(@PathVariable Long id) {
        Ocorrencia ocorrencia = getOcorrencia(id);
        return ResponseEntity.ok(OcorrenciaMapper.toResponse(ocorrencia));
    }

    private Ocorrencia getOcorrencia(Long id) {
        return ocorrenciaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ocorrência não encontrada"));
    }
}