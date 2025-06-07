package com.java.sysweather.repository;

import com.java.sysweather.model.Ocorrencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long>, JpaSpecificationExecutor<Ocorrencia> {
    List<Ocorrencia> findByMunicipioId(Long municipioId);
}