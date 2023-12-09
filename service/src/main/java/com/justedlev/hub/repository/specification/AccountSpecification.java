package com.justedlev.hub.repository.specification;

import com.justedlev.hub.repository.entity.AbstractPersistable_;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Account_;
import com.justedlev.hub.repository.filter.AccountFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Builder
public record AccountSpecification(
        @Singular(ignoreNullCollections = true)
        Collection<UUID> ids,
        @Singular(ignoreNullCollections = true)
        Collection<String> nicknames,
        @Singular(ignoreNullCollections = true)
        Collection<String> statuses,
        @Singular(ignoreNullCollections = true)
        Collection<String> excludeStatuses,
        @Singular(ignoreNullCollections = true)
        Collection<String> modes,
        @Singular(ignoreNullCollections = true)
        Collection<String> confirmCodes,
        String freeText
) implements Specification<Account> {
    @NonNull
    public static AccountSpecification from(@NonNull AccountFilter filter) {
        return AccountSpecification.builder()
                .ids(filter.getIds())
                .nicknames(filter.getNicknames())
                .statuses(filter.getStatuses())
                .excludeStatuses(filter.getExcludeStatuses())
                .modes(filter.getModes())
                .confirmCodes(filter.getConfirmCodes())
                .freeText(filter.getFreeText())
                .build();
    }

    @NonNull
    @Override
    public Predicate toPredicate(@NonNull Root<Account> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(ids())) {
            predicates.add(root.get(AbstractPersistable_.id).in(ids()));
        }

        if (CollectionUtils.isNotEmpty(nicknames())) {
            predicates.add(root.get(Account_.nickname).in(nicknames()));
        }

        if (CollectionUtils.isNotEmpty(modes())) {
            predicates.add(root.get(Account_.mode).in(modes()));
        }

        if (CollectionUtils.isNotEmpty(statuses())) {
            predicates.add(root.get(Account_.status).in(statuses()));
        }

        if (CollectionUtils.isNotEmpty(excludeStatuses())) {
            predicates.add(cb.not(root.get(Account_.status).in(excludeStatuses())));
        }

        if (CollectionUtils.isNotEmpty(confirmCodes())) {
            predicates.add(root.get(Account_.confirmCode).in(confirmCodes()));
        }

        if (StringUtils.isNotBlank(freeText())) {
            Pattern.compile("\\s+")
                    .splitAsStream(freeText())
                    .filter(StringUtils::isNotBlank)
                    .map(cb::literal)
                    .map(cb::trim)
                    .map(exp -> cb.concat("%", exp))
                    .map(exp -> cb.concat(exp, "%"))
                    .forEach(exp -> predicates.add(cb.or(
                            cb.like(root.get(Account_.nickname), exp),
                            cb.like(root.get(Account_.firstName), exp),
                            cb.like(root.get(Account_.lastName), exp)
                    )));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
