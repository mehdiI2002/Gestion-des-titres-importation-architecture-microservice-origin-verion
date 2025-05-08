package org.gestiondestitresimportationbcp.web;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
public class SseController {
    private final Sinks.Many<String> sink;

    public SseController() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }
    @GetMapping(value = "/sse/files", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFiles() {
        return sink.asFlux();
    }

    public void notifyFileCreated(String fileName) {
        sink.tryEmitNext("Fichier détecté : " + fileName);
    }
}
