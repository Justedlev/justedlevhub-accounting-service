package com.justedlev.hub.account.audit;

import com.justedlev.hub.account.audit.repository.entity.base.Auditable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        this.nodes = getInnerNodes();
    }

    public Collection<AuditableNode> getAllNodes() {
        var nodeStream = Stream.ofNullable(nodes)
                .flatMap(Collection::stream)
                .map(AuditableNode::getNodes)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(Collection::stream);

        return Stream.concat(Stream.of(this), nodeStream)
                .collect(Collectors.toList());
    }

    public Collection<Object> getObjects() {
        return getAllNodes()
                .stream()
                .map(AuditableNode::getObj)
                .collect(Collectors.toList());
    }

    private AuditableNode(Object parent, Object obj) {
        this.parent = parent;
        this.obj = obj;
        this.type = obj.getClass();
    }

    @SuppressWarnings("unchecked")
    private List<AuditableNode> getInnerNodes() {
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
                .map(e -> new AuditableNode(obj, e))
                .toList();
    }

    private Stream<?> getStream(Object o) {
        if (Objects.isNull(o)) return Stream.empty();

        return switch (o) {
            case Collection<?> s -> s.stream();
            case Iterable<?> i -> StreamSupport.stream(i.spliterator(), false);
            case Auditable a -> Stream.of(a);
            default -> Stream.of(o);
        };
    }

    @SuppressWarnings("unchecked")
    private Optional<Method> getNecessaryMethod(Field field) {
        var name = METHOD_PREFIX + StringUtils.capitalize(field.getName());

        return getMethods(type, withName(name).and(withParametersCount(0)))
                .stream()
                .findFirst();
    }
}
