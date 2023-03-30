package co.diego.usecase.getflowerbyid;

import co.diego.model.flower.Flower;
import co.diego.model.flower.gateways.FlowerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetFLowerByIDUseCase implements Function<String, Mono<Flower>> {

    private final FlowerGateway flowerGateway;

    @Override
    public Mono<Flower> apply(String flowerId) {
        return flowerGateway.getFlowerById(flowerId);
    }
}
