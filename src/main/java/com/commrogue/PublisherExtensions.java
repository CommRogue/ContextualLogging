package com.commrogue;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.context.ContextView;

import java.util.Map;

@Slf4j
public class PublisherExtensions {
    private static void appendContextView(LoggingEventBuilder baseLogBuilder, ContextView contextView) {
        for (Map.Entry<Object, Object> contextEntry: contextView.stream().toList()) {
            baseLogBuilder = baseLogBuilder.addKeyValue(contextEntry.getKey().toString(), contextEntry.getValue());
        }
    }

    private static void logContextual(String message, Level level, ContextView contextView, Object... args) {
        LoggingEventBuilder baseLogBuilder = log.atLevel(level);
        appendContextView(baseLogBuilder, contextView);

        baseLogBuilder.log(message, args);
    }

    private static <EmissionType> Flux<EmissionType> logContextual(Flux<EmissionType> publisher, SignalType signalType,
                                                                   Level level, String message, Object... args) {
        return publisher.doOnEach((signal) -> {
            if (signalType.equals(signal.getType())) {
                logContextual(message, level, signal.getContextView(), args);
            }
        });
    }

    private static <EmissionType> Mono<EmissionType> logContextual(Mono<EmissionType> publisher, SignalType signalType,
                                                                   Level level, String message, Object... args) {
        return publisher.doOnEach((signal) -> {
            if (signalType.equals(signal.getType())) {
                logContextual(message, level, signal.getContextView(), args);
            }
        });
    }

    public static <T> Flux<T> logOnNext(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_NEXT, level, message, args);
    }

    public static <T> Flux<T> logOnCancel(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.CANCEL, level, message, args);
    }

    public static <T> Flux<T> logOnError(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_ERROR, level, message, args);
    }

    public static <T> Flux<T> logAfterTerminate(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.AFTER_TERMINATE, level, message, args);
    }

    public static <T> Flux<T> logOnComplete(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_COMPLETE, level, message, args);
    }

    public static <T> Flux<T> logOnSubscribe(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_SUBSCRIBE, level, message, args);
    }

    public static <T> Flux<T> logOnContext(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_CONTEXT, level, message, args);
    }

    public static <T> Flux<T> logOnRequest(Flux<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.REQUEST, level, message, args);
    }

    public static <T> Mono<T> logOnNext(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_NEXT, level, message, args);
    }

    public static <T> Mono<T> logOnCancel(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.CANCEL, level, message, args);
    }

    public static <T> Mono<T> logOnError(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_ERROR, level, message, args);
    }

    public static <T> Mono<T> logAfterTerminate(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.AFTER_TERMINATE, level, message, args);
    }

    public static <T> Mono<T> logOnComplete(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_COMPLETE, level, message, args);
    }

    public static <T> Mono<T> logOnSubscribe(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_SUBSCRIBE, level, message, args);
    }

    public static <T> Mono<T> logOnContext(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.ON_CONTEXT, level, message, args);
    }

    public static <T> Mono<T> logOnRequest(Mono<T> publisher, Level level, String message, Object... args) {
        return logContextual(publisher, SignalType.REQUEST, level, message, args);
    }
}
