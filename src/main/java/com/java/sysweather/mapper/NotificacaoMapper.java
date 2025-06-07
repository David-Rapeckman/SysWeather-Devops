package com.java.sysweather.mapper;

import com.java.sysweather.dto.response.NotificacaoOcorrenciaResponse;
import com.java.sysweather.model.NotificacaoOcorrencia;

public class NotificacaoMapper {

    public static NotificacaoOcorrenciaResponse toResponse(NotificacaoOcorrencia notificacao) {
        return NotificacaoOcorrenciaResponse.builder()
            .id(notificacao.getId())
            .mensagem(notificacao.getMensagem())
            .dataEnvio(notificacao.getDataEnvio())
            .usuario(UsuarioMapper.toResumo(notificacao.getUsuario()))
            .ocorrencia(OcorrenciaMapper.toResumo(notificacao.getOcorrencia()))
            .build();
    }
}
