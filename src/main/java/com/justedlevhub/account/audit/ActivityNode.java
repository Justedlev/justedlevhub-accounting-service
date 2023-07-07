package com.justedlevhub.account.audit;

import com.justedlevhub.account.audit.repository.entity.base.Auditable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.reflections.ReflectionUtils.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityNode {
    private Object parent;
    private Object obj;
    private Class<?> type;
    private List<ActivityNode> nodes;
    @Setter
    private Predicate<Field> fieldPredicate;

    private static final String METHOD_PREFIX = "get";

    public ActivityNode(Object obj) {
        this(null, obj);
    }

    public ActivityNode(Object obj, Predicate<Field> fieldPredicate) {
        this(null, obj, null);
        this.fieldPredicate = fieldPredicate;
        this.nodes = getNodes();
    }

    private ActivityNode(Object parent, Object obj) {
        this(parent, obj, null);
        this.nodes = getNodes();
    }

    private ActivityNode(Object parent, Object obj, List<ActivityNode> nodes) {
        this(parent, obj, obj.getClass(), nodes);
    }

    private ActivityNode(Object parent, Object obj, Class<?> type, List<ActivityNode> nodes) {
        this(parent, obj, type, nodes, withTypeAssignableTo(Objects.class));
    }

    private List<ActivityNode> getNodes() {
        var fields = getNecessaryFields();

        return fields.stream()
                .map(this::getNecessaryMethod)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(m -> invoke(m, obj))
                .filter(o -> !Objects.equals(o, obj))
                .filter(o -> !Objects.equals(o, parent))
                .filter(o -> !Objects.equals(obj, parent))
                .flatMap(this::getStream)
                .map(o -> new ActivityNode(obj, o))
                .toList();
    }

    private Stream<?> getStream(Object o) {
        if (Objects.isNull(o)) return Stream.empty();

        return switch (o) {
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
    private Set<Field> getNecessaryFields() {
        return getFields(type, fieldPredicate);
    }

    public List<Object> getObjects() {
        var all = nodes.stream()
                .map(ActivityNode::getObjects)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        all.add(this.getObj());

        return all;
    }
}
