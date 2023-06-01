package com.justedlev.account.repository.specification;

import com.justedlev.account.repository.entity.AccountJournal;
import com.justedlev.account.repository.entity.AccountJournal_;
import com.justedlev.account.repository.specification.filter.AccountJournalFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountJournalSpecification implements Specification<AccountJournal> {
    private final AccountJournalFilter filter;

    public static AccountJournalSpecification from(AccountJournalFilter filter) {
        return new AccountJournalSpecification(filter);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<AccountJournal> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getIds())) {
            predicates.add(root.get(AccountJournal_.id).in(filter.getIds()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
