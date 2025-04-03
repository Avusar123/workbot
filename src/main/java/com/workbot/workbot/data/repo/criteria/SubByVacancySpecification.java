package com.workbot.workbot.data.repo.criteria;

import com.workbot.workbot.data.model.Filter;
import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.dto.VacancyDto;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SubByVacancySpecification implements Specification<Subscription> {
    private final VacancyDto vacancy;

    public SubByVacancySpecification(VacancyDto vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public Predicate toPredicate(Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Subscription, Filter> uniteFilter = root.join("filter");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(uniteFilter.get("area"), vacancy.getArea()));

        predicates.add(criteriaBuilder.or(criteriaBuilder.lessThanOrEqualTo(uniteFilter.get("date"), vacancy.getAdded())));

        predicates.add(criteriaBuilder.isMember(vacancy.getCompany(), uniteFilter.get("companies")));

        Subquery<String> subquery = query.subquery(String.class);

        Root<Filter> filterRoot = subquery.from(Filter.class);

        Join<Filter, String> joinedRoot = filterRoot.join("keywords", JoinType.LEFT);

        subquery
                .select(joinedRoot)
                .where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(filterRoot, uniteFilter),
                            criteriaBuilder.or(
                                    criteriaBuilder.isNull(joinedRoot),
                                    criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.literal(vacancy.getTitle().getUnsafe())),
                                            criteriaBuilder.concat("%", criteriaBuilder.concat(criteriaBuilder.lower(joinedRoot), "%"))),
                                    criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.literal(vacancy.getDescription().getUnsafe())),
                                            criteriaBuilder.concat("%", criteriaBuilder.concat(criteriaBuilder.lower(joinedRoot), "%")))
                            )
                    )
                );

        predicates.add(criteriaBuilder.exists(subquery));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}