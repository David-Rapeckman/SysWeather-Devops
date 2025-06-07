package com.java.sysweather.service;

import com.java.sysweather.model.Municipio;
import com.java.sysweather.model.Usuario;
import com.java.sysweather.repository.MunicipioRepository;
import com.java.sysweather.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MunicipioRepository municipioRepository;  // precisa injetar

    public Usuario saveUsuario(Usuario usuario) {
        // Verificar se o CPF já existe
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com esse CPF.");
        }

        // Verificar se o e-mail já existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com esse e-mail.");
        }

        // Carregar municipio completo para garantir que estado não seja nulo
        if (usuario.getMunicipio() != null && usuario.getMunicipio().getId() != null) {
            Municipio municipio = municipioRepository.findById(usuario.getMunicipio().getId())
                .orElseThrow(() -> new IllegalArgumentException("Município não encontrado."));
            usuario.setMunicipio(municipio);
        } else {
            usuario.setMunicipio(null);  // ou lance exceção se município for obrigatório
        }

        // Define automaticamente a data de cadastro se não vier preenchida
        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(LocalDateTime.now());
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario novoUsuario) {
        // Buscar usuário existente
        Usuario existente = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));

        // Verificar se está tentando alterar o CPF
        if (!existente.getCpf().equals(novoUsuario.getCpf())) {
            throw new IllegalArgumentException("O CPF não pode ser alterado.");
        }

        // Verificar se o novo e-mail já existe para outro usuário
        Optional<Usuario> usuarioComMesmoEmail = usuarioRepository.findByEmail(novoUsuario.getEmail());
        if (usuarioComMesmoEmail.isPresent() && !usuarioComMesmoEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe outro usuário com esse e-mail.");
        }

        // Carregar municipio completo para garantir que estado não seja nulo
        if (novoUsuario.getMunicipio() != null && novoUsuario.getMunicipio().getId() != null) {
            Municipio municipio = municipioRepository.findById(novoUsuario.getMunicipio().getId())
                .orElseThrow(() -> new IllegalArgumentException("Município não encontrado."));
            existente.setMunicipio(municipio);
        } else {
            existente.setMunicipio(null);  // ou lance exceção se município for obrigatório
        }

        // Atualizar os demais campos permitidos
        existente.setNome(novoUsuario.getNome());
        existente.setEmail(novoUsuario.getEmail());
        existente.setSenha(novoUsuario.getSenha());

        return usuarioRepository.save(existente);
    }
}
