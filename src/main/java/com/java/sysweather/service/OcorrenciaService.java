package com.java.sysweather.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.sysweather.model.NotificacaoOcorrencia;
import com.java.sysweather.model.Ocorrencia;
import com.java.sysweather.model.Usuario;
import com.java.sysweather.model.Municipio;
import com.java.sysweather.repository.MunicipioRepository;
import com.java.sysweather.repository.NotificacaoRepository;
import com.java.sysweather.repository.OcorrenciaRepository;
import com.java.sysweather.repository.UsuarioRepository;

@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    public Ocorrencia saveOcorrencia(Ocorrencia ocorrencia) {
        var municipioId = ocorrencia.getMunicipio().getId();
        Municipio municipio = municipioRepository.findById(municipioId)
            .orElseThrow(() -> new IllegalArgumentException("Município associado não existe."));

        // Garante que o município está completamente carregado
        ocorrencia.setMunicipio(municipio);

        // Salvar a ocorrência
        Ocorrencia saved = ocorrenciaRepository.save(ocorrencia);

        // Buscar usuários daquele município
        List<Usuario> usuarios = usuarioRepository.findByMunicipioId(municipioId);

        // Criar notificações para cada usuário
        List<NotificacaoOcorrencia> notificacoes = usuarios.stream().map(usuario -> {
            NotificacaoOcorrencia n = new NotificacaoOcorrencia();
            n.setUsuario(usuario);
            n.setOcorrencia(saved);
            n.setMensagem("Nova ocorrência: " + saved.getTipo().name() +
                " com risco " + saved.getNivelRisco().name() +
                " no município " + saved.getMunicipio().getNome());
            n.setDataEnvio(LocalDateTime.now());
            return n;
        }).toList();

        // Salvar todas notificações
        notificacaoRepository.saveAll(notificacoes);

        return saved;
    }

    public Ocorrencia updateOcorrencia(Long id, Ocorrencia novaOcorrencia) {
        // Verificar se a ocorrência existe
        Ocorrencia existente = ocorrenciaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ocorrência com ID " + id + " não encontrada."));

        // Verificar se o município informado na nova ocorrência existe
        Long municipioId = novaOcorrencia.getMunicipio().getId();
        Municipio municipio = municipioRepository.findById(municipioId)
            .orElseThrow(() -> new IllegalArgumentException("Município com ID " + municipioId + " não existe."));

        // Atualizar os dados
        existente.setDescricao(novaOcorrencia.getDescricao());
        existente.setTipo(novaOcorrencia.getTipo());
        existente.setNivelRisco(novaOcorrencia.getNivelRisco());
        existente.setDataOcorrencia(novaOcorrencia.getDataOcorrencia());
        existente.setMunicipio(municipio); // garante que estado não será nulo

        return ocorrenciaRepository.save(existente);
    }
}
