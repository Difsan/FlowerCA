package co.diego.usecase.saveflower;

import co.diego.model.flower.Flower;
import co.diego.model.flower.gateways.FlowerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveFlowerUseCase implements Function<Flower, Mono<Flower>> {

    private final FlowerGateway flowerGateway;

    @Override
    public Mono<Flower> apply(Flower flower) {
        return flowerGateway.saveFlower(flower);
    }
}
