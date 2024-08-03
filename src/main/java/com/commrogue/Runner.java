package com.commrogue;

import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static org.slf4j.event.Level.INFO;

@Component
@Slf4j
@ExtensionMethod(PublisherExtensions.class)
public class Runner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        Flux.range(0, 2).logOnNext(INFO, "ewew", kv("structureargumets", "value")).contextWrite(Context.of(
                "x",
                "ewew")).subscribeOn(Schedulers.boundedElastic()).blockLast();
    }
}
