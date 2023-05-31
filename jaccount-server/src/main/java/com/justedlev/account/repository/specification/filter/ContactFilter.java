package com.justedlev.account.repository.specification.filter;

import com.justedlev.account.repository.entity.Contact;
import com.justedlev.account.repository.entity.Contact_;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactFilter implements Filter<Contact> {
    private Collection<String> emails;
    private String searchText;
    private AccountFilter accountFilter;

    @Override
    public List<Predicate> build(CriteriaBuilder cb, Path<Contact> path) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(getEmails())) {
            predicates.add(path.get(Contact_.email).in(getEmails()));
        }

        return predicates;
    }
}
