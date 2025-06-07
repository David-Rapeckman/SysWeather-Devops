package com.java.sysweather.specification;

import com.java.sysweather.model.Usuario;
import com.java.sysweather.controller.UsuarioController.UsuarioFilters;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

public class UsuarioSpecification {

    public static Specification<Usuario> withFilters(UsuarioFilters filters) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            // Filtro por nome do município (case-insensitive, like)
            if (filters.municipioNome() != null && !filters.municipioNome().isBlank()) {
                predicates.add(cb.like(
                    cb.lower(root.get("municipio").get("nome")),
                    "%" + filters.municipioNome().toLowerCase() + "%"
                ));
            }

            // Filtro por data de cadastro exata
            if (filters.dataCadastro() != null) {
                predicates.add(cb.equal(root.get("dataCadastro"), filters.dataCadastro()));
            } else {
                // Filtro por intervalo dataCadastro
                if (filters.dataCadastroInicio() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("dataCadastro"), filters.dataCadastroInicio()));
                }
                if (filters.dataCadastroFim() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("dataCadastro"), filters.dataCadastroFim()));
                }
            }

            // Filtro por data de nascimento exata
            if (filters.dataNascimento() != null) {
                predicates.add(cb.equal(root.get("dataNascimento"), filters.dataNascimento()));
            } else {
                // Filtro por intervalo dataNascimento
                if (filters.dataNascimentoInicio() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("dataNascimento"), filters.dataNascimentoInicio()));
                }
                if (filters.dataNascimentoFim() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("dataNascimento"), filters.dataNascimentoFim()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
