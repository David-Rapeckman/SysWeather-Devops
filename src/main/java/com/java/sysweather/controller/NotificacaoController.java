package com.java.sysweather.controller;

import com.java.sysweather.dto.response.NotificacaoOcorrenciaResponse;
import com.java.sysweather.mapper.NotificacaoMapper;
import com.java.sysweather.model.NotificacaoOcorrencia;
import com.java.sysweather.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @GetMapping
    public List<NotificacaoOcorrenciaResponse> listarTodas() {
        List<NotificacaoOcorrencia> notificacoes = notificacaoRepository.findAllByOrderByDataEnvioDesc();
        return notificacoes.stream()
                .map(NotificacaoMapper::toResponse)
                .collect(Collectors.toList());
    }
}