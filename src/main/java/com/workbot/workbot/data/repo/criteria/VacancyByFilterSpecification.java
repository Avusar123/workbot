package com.workbot.workbot.data.repo.criteria;

import com.workbot.workbot.data.model.Vacancy;
import com.workbot.workbot.data.model.dto.FilterDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VacancyByFilterSpecification implements Specification<Vacancy> {
    private final FilterDto filter;

    public VacancyByFilterSpecification(FilterDto filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Vacancy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
            criteriaBuilder
                .equal(root.get("area"), filter.getArea()
        ));

        predicates.add(
                criteriaBuilder.greaterThanOrEqualTo(root.get("added"), filter.getDate())
        );

        if (!filter.getKeywords().isEmpty()) {
            List<Predicate> keywordPredicates = new ArrayList<>();
            for (String keyword : filter.getKeywords()) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                Predicate titleLike = criteriaBuilder
                                        .like(criteriaBuilder
                                                .lower(root.get("title")), pattern);
                Predicate descLike = criteriaBuilder
                                        .like(criteriaBuilder
                                                .lower(root.get("description")), pattern);
                keywordPredicates.add(criteriaBuilder.or(titleLike, descLike));
            }

            predicates
                    .add(criteriaBuilder
                            .or(keywordPredicates
                                    .toArray(new Predicate[0])));
        }

        if (!filter.getCompanies().isEmpty()) {
            predicates.add(root.get("company").in(filter.getCompanies()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
