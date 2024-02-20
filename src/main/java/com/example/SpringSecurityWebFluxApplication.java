package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

@SpringBootApplication
public class SpringSecurityWebFluxApplication {

    public static void main(String[] args) {

//        Flux<String> sequence = Flux.just("Hello", "Reactor") ;
//
//        sequence.map(String::toLowerCase)
//                .subscribe(System.out::println) ;

//        SpringApplication.run(SpringSecurityWebFluxApplication.class, args);

        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri() ;

        RestTemplate restTemplate = new RestTemplate() ;
        HttpHeaders headers = new HttpHeaders() ;
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(
                restTemplate.exchange(worldTimeUri,
                        HttpMethod.GET,
                        new HttpEntity<String>(headers),
                        String.class)
        ).map(response -> {
            return response.getBody() ;
        })
                .subscribe(data-> System.out.println("# enmitted data: "+ data),
                        System.out::println,
                        ()-> System.out.println("# emitted onComplete signal")
                    ) ;

    }
}






