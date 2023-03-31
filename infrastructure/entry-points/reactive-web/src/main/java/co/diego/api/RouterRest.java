package co.diego.api;

import co.diego.model.flower.Flower;
import co.diego.usecase.deleteflower.DeleteFlowerUseCase;
import co.diego.usecase.getallflowers.GetAllFLowersUseCase;
import co.diego.usecase.getflowerbyid.GetFLowerByIDUseCase;
import co.diego.usecase.saveflower.SaveFlowerUseCase;
import co.diego.usecase.updateflower.UpdateFlowerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperation(path = "/flowers", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllFLowersUseCase.class, method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllFLowers", tags = "Flowers usecases",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content (schema = @Schema(implementation = Flower.class))),
                    @ApiResponse(responseCode = "204", description = "Nothing to show")
            }))
    public RouterFunction<ServerResponse> getAllFlowers (GetAllFLowersUseCase getAllFLowersUseCase){
        return route(GET("/flowers"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllFLowersUseCase.get(), Flower.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
                );
    }

    @Bean
    @RouterOperation(path = "/flowers/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetFLowerByIDUseCase.class, method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getFlowerById", tags = "Flowers usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Flower.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
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
    @RouterOperation(path = "/flowers", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveFlowerUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveFlower", tags = "Flowers usecases",
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Flower.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
                    //requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Flower.class)))))
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
    @RouterOperation(path = "/flowers", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateFlowerUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateFlower", tags = "Flowers usecases",
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Flower.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
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
    @RouterOperation(path = "/flowers/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteFlowerUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteFlowerById", tags = "Flowers usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Flower.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
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
