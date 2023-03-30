package co.diego.api;

import co.diego.model.flower.Flower;
import co.diego.usecase.deleteflower.DeleteFlowerUseCase;
import co.diego.usecase.getallflowers.GetAllFLowersUseCase;
import co.diego.usecase.getflowerbyid.GetFLowerByIDUseCase;
import co.diego.usecase.saveflower.SaveFlowerUseCase;
import co.diego.usecase.updateflower.UpdateFlowerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> getAllFlowers (GetAllFLowersUseCase getAllFLowersUseCase){
        return route(GET("/flowers"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllFLowersUseCase.get(), Flower.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
                );
    }

    @Bean
    public RouterFunction<ServerResponse> getFlowerById(GetFLowerByIDUseCase getFLowerByIDUseCase){
        return route(GET("flowers/{id}"),
                request -> getFLowerByIDUseCase.apply(request.pathVariable("id"))
                        .flatMap(flower -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(flower))
                        //.onErrorResume(throwable -> ServerResponse.notFound().build())
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> saveFlower (SaveFlowerUseCase saveFlowerUseCase){
        return route(POST("/flowers").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Flower.class)
                        .flatMap(flower -> saveFlowerUseCase.apply(flower)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateFlower(UpdateFlowerUseCase updateFlowerUseCase){
        return route(PUT("/flowers/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Flower.class)
                        .flatMap(flower -> updateFlowerUseCase.apply(request.pathVariable("id"),
                                        flower)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteFlower (DeleteFlowerUseCase deleteFlowerUseCase){
        return route(DELETE("/flowers/{id}"),
                request -> deleteFlowerUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Flower deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        //.onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
