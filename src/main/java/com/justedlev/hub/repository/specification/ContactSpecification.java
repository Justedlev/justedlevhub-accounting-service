package com.justedlev.hub.repository.specification;

import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.repository.entity.Contact_;
import com.justedlev.hub.repository.specification.filter.ContactFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactSpecification implements Specification<Contact> {
    private final ContactFilter filter;

    public static ContactSpecification from(@NonNull ContactFilter filter) {
        return new ContactSpecification(filter);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Contact> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getValues())) {
            predicates.add(root.get(Contact_.value).in(filter.getValues()));
        }

        if (CollectionUtils.isNotEmpty(filter.getTypes())) {
            predicates.add(root.get(Contact_.type).in(filter.getTypes()));
        }

        if (StringUtils.isNotBlank(filter.getFreeSearch())) {
            var q = "%" + filter.getFreeSearch().toLowerCase() + "%";

            predicates.add(cb.and(
                    cb.like(cb.lower(root.get(Contact_.value)), q)
            ));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
