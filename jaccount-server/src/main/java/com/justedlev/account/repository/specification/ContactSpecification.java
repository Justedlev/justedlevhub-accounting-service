package com.justedlev.account.repository.specification;

import com.justedlev.account.repository.entity.Contact;
import com.justedlev.account.repository.entity.Contact_;
import com.justedlev.account.repository.specification.filter.ContactFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactSpecification implements Specification<Contact> {
    private final ContactFilter filter;

    public static ContactSpecification from(ContactFilter filter) {
        return new ContactSpecification(filter);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Contact> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getEmails())) {
            predicates.add(root.get(Contact_.email).in(filter.getEmails()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
