package com.justedlev.hub.repository.specification;

import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Account_;
import com.justedlev.hub.repository.filter.AccountFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountSpecification implements Specification<Account> {
    private final AccountFilter filter;

    public static AccountSpecification from(AccountFilter filter) {
        return new AccountSpecification(filter);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Account> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getIds())) {
            predicates.add(root.get(Account_.id).in(filter.getIds()));
        }

        if (CollectionUtils.isNotEmpty(filter.getNicknames())) {
            predicates.add(root.get(Account_.nickname).in(filter.getNicknames()));
        }

        if (CollectionUtils.isNotEmpty(filter.getModes())) {
            predicates.add(root.get(Account_.mode).in(filter.getModes()));
        }

        if (CollectionUtils.isNotEmpty(filter.getStatuses())) {
            predicates.add(root.get(Account_.status).in(filter.getStatuses()));
        }

        if (CollectionUtils.isNotEmpty(filter.getExcludeStatuses())) {
            predicates.add(cb.not(root.get(Account_.status).in(filter.getExcludeStatuses())));
        }

        if (CollectionUtils.isNotEmpty(filter.getConfirmCodes())) {
            predicates.add(root.get(Account_.confirmCode).in(filter.getConfirmCodes()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
