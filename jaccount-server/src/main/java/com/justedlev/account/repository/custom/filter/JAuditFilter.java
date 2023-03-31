package com.justedlev.account.repository.custom.filter;

import com.justedlev.account.repository.entity.JAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JAuditFilter implements Filter<JAudit> {
    private Collection<JAudit.Operation> operations;

    @Override
    public List<Predicate> build(CriteriaBuilder cb, Path<JAudit> path) {
        return null;
    }
}
