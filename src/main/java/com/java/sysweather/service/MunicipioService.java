package com.java.sysweather.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.java.sysweather.model.Municipio;
import com.java.sysweather.repository.MunicipioRepository;

@Service
public class MunicipioService {

    @Autowired
    private MunicipioRepository municipioRepository;

    public Municipio save(Municipio municipio) {
        // Verifica se já existe um município com o mesmo nome no mesmo estado
        Optional<Municipio> existente = municipioRepository.findByNomeAndEstado(municipio.getNome(), municipio.getEstado());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Já existe um município com esse nome neste estado.");
        }

        return municipioRepository.save(municipio);
    }

    public Municipio update(Long id, Municipio municipio) {
        // Verifica se o município que será atualizado existe
        Municipio existente = municipioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Município não encontrado para atualização."));

        // Verifica se já existe outro município com o mesmo nome e estado
        Optional<Municipio> outro = municipioRepository.findByNomeAndEstado(municipio.getNome(), municipio.getEstado());
        if (outro.isPresent() && !outro.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe um município com esse nome neste estado.");
        }

        // Atualiza os campos (exceto o ID)
        existente.setNome(municipio.getNome());
        existente.setEstado(municipio.getEstado());
        existente.setNumero_habitantes(municipio.getNumero_habitantes());
        existente.setClima(municipio.getClima());
        existente.setRegiao(municipio.getRegiao());
        existente.setAltitude(municipio.getAltitude());
        existente.setAreaKm2(municipio.getAreaKm2());

        return municipioRepository.save(existente);
    }
}