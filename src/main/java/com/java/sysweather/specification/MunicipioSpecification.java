package com.java.sysweather.specification;

import com.java.sysweather.controller.MunicipioController.MunicipioFilters;
import com.java.sysweather.model.Municipio;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

public class MunicipioSpecification {

    public static Specification<Municipio> withFilters(MunicipioFilters filters) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (filters.nome() != null && !filters.nome().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%"));
            }

            if (filters.estado() != null) {
                predicates.add(cb.equal(root.get("estado"), filters.estado()));
            }

            if (filters.numeroHabitantes() != null) {
                predicates.add(cb.equal(root.get("numero_habitantes"), filters.numeroHabitantes()));
            } else {
                if (filters.numeroHabitantesMin() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("numero_habitantes"), filters.numeroHabitantesMin()));
                }
                if (filters.numeroHabitantesMax() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("numero_habitantes"), filters.numeroHabitantesMax()));
                }
            }

            if (filters.regiao() != null) {
                predicates.add(cb.equal(root.get("regiao"), filters.regiao()));
            }

            if (filters.areaKm2() != null) {
                predicates.add(cb.equal(root.get("areaKm2"), filters.areaKm2()));
            } else {
                if (filters.areaKm2Min() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("areaKm2"), filters.areaKm2Min()));
                }
                if (filters.areaKm2Max() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("areaKm2"), filters.areaKm2Max()));
                }
            }

            if (filters.altitude() != null) {
                predicates.add(cb.equal(root.get("altitude"), filters.altitude()));
            } else {
                if (filters.altitudeMin() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("altitude"), filters.altitudeMin()));
                }
                if (filters.altitudeMax() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("altitude"), filters.altitudeMax()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}