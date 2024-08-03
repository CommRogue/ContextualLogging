package com.commrogue;

import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ContextSnapshotFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Hooks;

import java.util.Arrays;

@Component
@ConditionalOnClass({ContextRegistry.class, ContextSnapshotFactory.class})
@ConditionalOnProperty(value = "logging.propagate-context.fields")
public class ContextPropagator {
    public static void xx(String claim) {
        MDC.remove(claim);
    }
    public ContextPropagator(@Value("${logging.propagate-context.fields}") String[] fields) {
        if (fields.length > 0) {
            Arrays.stream(fields).forEach(claim -> ContextRegistry.getInstance()
                    .registerThreadLocalAccessor(claim,
                            () -> MDC.get(claim),
                            value -> MDC.put(claim, value),
                            () -> xx(claim)));

            Hooks.enableAutomaticContextPropagation();
        }

    }
}