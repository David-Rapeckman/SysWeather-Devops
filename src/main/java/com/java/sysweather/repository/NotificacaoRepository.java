package com.java.sysweather.repository;

import com.java.sysweather.model.NotificacaoOcorrencia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<NotificacaoOcorrencia, Long> {
    List<NotificacaoOcorrencia> findAllByOrderByDataEnvioDesc();
}