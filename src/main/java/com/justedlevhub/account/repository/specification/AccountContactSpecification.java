package com.justedlevhub.account.repository.specification;

import com.justedlevhub.account.repository.entity.AccountContact;
import com.justedlevhub.account.repository.entity.AccountContact_;
import com.justedlevhub.account.repository.specification.filter.AccountContactFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountContactSpecification implements Specification<AccountContact> {
    private final AccountContactFilter filter;

    public static AccountContactSpecification from(AccountContactFilter filter) {
        return new AccountContactSpecification(filter);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<AccountContact> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getIds())) {
            predicates.add(root.get(AccountContact_.id).in(filter.getIds()));
        }

        if (ObjectUtils.isNotEmpty(filter.getMain())) {
            predicates.add(cb.equal(root.get(AccountContact_.id), filter.getMain()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
