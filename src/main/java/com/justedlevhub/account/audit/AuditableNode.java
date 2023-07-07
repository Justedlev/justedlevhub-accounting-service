package com.justedlevhub.account.audit;

import com.justedlevhub.account.audit.repository.entity.base.Auditable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.reflections.ReflectionUtils.*;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditableNode {
    private Object parent;
    private Object obj;
    private Class<?> type;
    private List<AuditableNode> nodes;

    private static final String METHOD_PREFIX = "get";

    public AuditableNode(Object obj) {
        this(null, obj);
        this.nodes = getNodes();
    }

    private AuditableNode(Object parent, Object obj) {
        this.parent = parent;
        this.obj = obj;
        this.type = obj.getClass();
    }

    @SuppressWarnings("unchecked")
    private List<AuditableNode> getNodes() {
        var fields = getFields(type);

        return fields.stream()
                .map(this::getNecessaryMethod)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(m -> invoke(m, obj))
                .filter(o -> !Objects.equals(o, obj))
                .filter(o -> !Objects.equals(o, parent))
                .filter(o -> !Objects.equals(obj, parent))
                .flatMap(this::getStream)
                .filter(Auditable.class::isInstance)
                .map(o -> new AuditableNode(obj, o))
                .toList();
    }

    private Stream<?> getStream(Object o) {
        if (Objects.isNull(o)) return Stream.empty();

        return switch (o) {
            case Collection<?> s -> s.stream();
            case Iterable<?> i -> StreamSupport.stream(i.spliterator(), false);
            case Auditable a -> Stream.of(a);
            default -> Stream.empty();
        };
    }

    @SuppressWarnings("unchecked")
    private Optional<Method> getNecessaryMethod(Field field) {
        return getMethods(type, getMethodPredicate(field))
                .stream()
                .findFirst();
    }

    private Predicate<? super Method> getMethodPredicate(Field field) {
        var name = METHOD_PREFIX + StringUtils.capitalize(field.getName());
        return withName(name).and(withParametersCount(0));
    }

    @SuppressWarnings("unchecked")
//    private Set<Field> getNecessaryFields() {
//        return getFields(type, fieldPredicate);
//    }

    public List<Object> getObjects() {
        var all = nodes.stream()
                .map(AuditableNode::getObjects)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        all.add(this.getObj());

        return all;
    }
}
