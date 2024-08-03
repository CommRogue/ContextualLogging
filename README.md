
# Reactor Contextual Logging

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Overview

Reactor Context Logger is a Java library designed to assist with logging Reactor Contexts. This library enhances logging capabilities within Reactor by appending the current Context as key-value pairs to log messages. It also includes an optional feature for automatic context propagation to the MDC (Mapped Diagnostic Context) on every operator call.

## Usage

### Manual Context Propagation
This library includes methods such as `logOnNext()`, `logOnSubscribe()`, `logOnCancel()` and more that integrate with `Flux` and `Mono`.
They allow you to pass a log message and arguments, and will append the current `Context` to your log statement. 

To get started, use Lombok's `@ExtensionMethod` to integrate these functions into `Mono` and `Flux`. You may then use these functions in your Reactor pipeline. 

#### Example Usage
```java
@ExtensionMethod(PublisherExtensions.class)
public class Example {
    public static void main(String[] args) {
        Flux.range(0, 2)
                .logOnNext(INFO, "message")
                .contextWrite(Context.of("context_key", "context_value"))
                .subscribeOn(Schedulers.boundedElastic()).blockLast();
    }
}
```

### Automatic Context Propagation
This library includes an optional feature that allows automatic propagation of Reactor's Context to the MDC (Mapped Diagnostic Context). It lets you write log messages without using the above methods, and within any Reactor operator, and still have the Context appear in your log messages.
It uses Reactor's `Hooks.enableAutomaticContextPropagation()` internally.\\
Using this feature is discouraged as it greatly impacts performance, due to the burden of having to write and remove from the MDC on every operator call. 

#### Example Usage
You may enable this feature in your Spring project by specifying fields to be automatically propagated in `logging.propagate-context.fields`: 
```yaml
logging:
  propagate-context:
    fields: request_id, item_id
```

In a vanilla Java project, you can instead call `ContextPropagator.propagate(String... fields)`. 
