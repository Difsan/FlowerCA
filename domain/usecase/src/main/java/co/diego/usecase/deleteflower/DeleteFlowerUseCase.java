package co.diego.usecase.deleteflower;

import co.diego.model.flower.gateways.FlowerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteFlowerUseCase implements Function<String, Mono<String>> {

    private final FlowerGateway flowerGateway;

    @Override
    public Mono<String> apply(String flowerId) {
        return flowerGateway.deleteFlower(flowerId);
    }
}
