package com.justedlev.account.repository.specification;

import com.justedlev.account.repository.entity.ContactJournal;
import com.justedlev.account.repository.specification.filter.ContactJournalFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactJournalSpecification implements Specification<ContactJournal> {
    private final ContactJournalFilter filter;

    public static ContactJournalSpecification from(ContactJournalFilter filter) {
        return new ContactJournalSpecification(filter);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<ContactJournal> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();


        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
