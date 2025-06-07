package com.java.sysweather.specification;

import com.java.sysweather.model.Ocorrencia;
import com.java.sysweather.controller.OcorrenciaController.OcorrenciaFilters;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OcorrenciaSpecification {

    public static Specification<Ocorrencia> withFilters(OcorrenciaFilters filters) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            // Filtro por nome do município (case-insensitive, like)
            if (filters.municipioNome() != null && !filters.municipioNome().isBlank()) {
                predicates.add(cb.like(
                    cb.lower(root.get("municipio").get("nome")),
                    "%" + filters.municipioNome().toLowerCase() + "%"
                ));
            }

            // Filtro por nível de risco
            if (filters.nivelRisco() != null) {
                predicates.add(cb.equal(root.get("nivelRisco"), filters.nivelRisco()));
            }

            // Filtro por tipo de ocorrência
            if (filters.tipo() != null) {
                predicates.add(cb.equal(root.get("tipo"), filters.tipo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}