package co.diego.model.flower.gateways;

import co.diego.model.flower.Flower;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlowerGateway {

    Flux<Flower> getAllFlowers();

    Mono<Flower> getFlowerById(String flowerId);

    Mono<Flower> saveFlower(Flower flower);

    Mono<Flower> updateFlower(String flowerId, Flower flower);

    Mono<Void> deleteFlower(String flowerId);
}
