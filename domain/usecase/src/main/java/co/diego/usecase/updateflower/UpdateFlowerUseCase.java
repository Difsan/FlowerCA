package co.diego.usecase.updateflower;

import co.diego.model.flower.Flower;
import co.diego.model.flower.gateways.FlowerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.function.Function3;

import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
public class UpdateFlowerUseCase implements BiFunction<String, Flower, Mono<Flower>> {

    private final FlowerGateway flowerGateway;

    @Override
    public Mono<Flower> apply(String flowerId, Flower flower) {
        return flowerGateway.updateFlower(flowerId, flower);
    }
}
