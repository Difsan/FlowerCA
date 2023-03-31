package co.diego.usecase.deleteflower;

import co.diego.model.flower.gateways.FlowerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteFlowerUseCase implements Function<String, Mono<Void>> {

    private final FlowerGateway flowerGateway;

    @Override
    public Mono<Void> apply( String flowerId) {
        return flowerGateway.deleteFlower(flowerId);
    }
}
