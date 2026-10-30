package com.ecommerce.api_gateway.filter;

import com.ecommerce.api_gateway.client.AuthClient;
import com.ecommerce.api_gateway.dto.ValidateTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class JwtAuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    private final AuthClient authClient;

    public JwtAuthenticationGatewayFilterFactory(
            @Lazy AuthClient authClient) {

        super(Config.class);
        this.authClient = authClient;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String path = exchange.getRequest()
                    .getPath()
                    .toString();

            // Skip auth endpoints
            if (path.startsWith("/api/auth")) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            if (authHeader == null ||
                    !authHeader.startsWith("Bearer ")) {

                log.error("Missing or invalid Authorization header");

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

            log.info("Authorization Header: {}", authHeader);

            return Mono
                    .fromCallable(() -> authClient.validateToken(authHeader))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(response -> {

                        log.info("Token Validation Response: {}",
                                response.isValid());

                        if (!response.isValid()) {

                            exchange.getResponse()
                                    .setStatusCode(HttpStatus.UNAUTHORIZED);

                            return exchange.getResponse()
                                    .setComplete();
                        }

                        return chain.filter(exchange);
                    })
                    .onErrorResume(ex -> {

                        log.error("Token Validation Error", ex);

                        exchange.getResponse()
                                .setStatusCode(HttpStatus.UNAUTHORIZED);

                        return exchange.getResponse()
                                .setComplete();
                    });
        };
    }

    public static class Config {
    }
}