package com.java.sysweather.controller;

import com.java.sysweather.dto.response.MunicipioDetalhadoResponse;
import com.java.sysweather.dto.response.MunicipioResumoResponse;
import com.java.sysweather.mapper.MunicipioMapper;
import com.java.sysweather.model.Municipio;
import com.java.sysweather.model.enums.Estado;
import com.java.sysweather.model.enums.Regiao;
import com.java.sysweather.repository.MunicipioRepository;
import com.java.sysweather.service.MunicipioService;
import com.java.sysweather.specification.MunicipioSpecification;
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
@RequestMapping("/municipios")
public class MunicipioController {

    public record MunicipioFilters(
        String nome,
        Estado estado,
        Integer numeroHabitantes,
        Integer numeroHabitantesMin,
        Integer numeroHabitantesMax,
        Regiao regiao,
        Double areaKm2,
        Double areaKm2Min,
        Double areaKm2Max,
        Double altitude,
        Double altitudeMin,
        Double altitudeMax
    ) {}

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioService municipioService;

    @GetMapping
    @Cacheable(value = "municipios")
    public Page<MunicipioResumoResponse> index(
        @ModelAttribute MunicipioFilters filters,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return municipioRepository.findAll(MunicipioSpecification.withFilters(filters), pageable)
            .map(MunicipioMapper::toResumo);
    }

    @PostMapping
    @CacheEvict(value = "municipios", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para cadastrar um município no sistema.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de município",
                summary = "Exemplo de um município.",
                value = """
                    {
                        "nome": "São Caetano do Sul",
                        "estado": "SP",
                        "numero_habitantes": 1774000,
                        "clima": "SUBTROPICAL",
                        "regiao": "SUDESTE",
                        "altitude": 760,
                        "areaKm2": 15.3
                    }
                """
            )
        )
    )
    public MunicipioResumoResponse create(@RequestBody @Valid Municipio municipio) {
        Municipio saved = municipioService.save(municipio);
        return MunicipioMapper.toResumo(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<MunicipioDetalhadoResponse> get(@PathVariable Long id) {
        Municipio municipio = getMunicipio(id);
        return ResponseEntity.ok(MunicipioMapper.toDetalhado(municipio));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "municipios", allEntries = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para alterar um município no sistema.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de município",
                summary = "Exemplo de um município.",
                value = """
                    {
                        "nome": "dadosnovos: São Caetano do Sul",
                        "estado": "SP",
                        "numero_habitantes": 1774000,
                        "clima": "SUBTROPICAL",
                        "regiao": "SUDESTE",
                        "altitude": 760,
                        "areaKm2": 15.3
                    }
                """
            )
        )
    )
    public ResponseEntity<MunicipioResumoResponse> update(@PathVariable Long id, @RequestBody @Valid Municipio municipio) {
        Municipio updated = municipioService.update(id, municipio);
        return ResponseEntity.ok(MunicipioMapper.toResumo(updated));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "municipios", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Municipio municipio = getMunicipio(id);
        municipioRepository.delete(municipio);
        return ResponseEntity.noContent().build();
    }

    private Municipio getMunicipio(Long id) {
        return municipioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Município não encontrado"));
    }
}
