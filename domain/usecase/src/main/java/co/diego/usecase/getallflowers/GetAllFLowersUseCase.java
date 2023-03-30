package co.diego.usecase.getallflowers;

import co.diego.model.flower.Flower;
import co.diego.model.flower.gateways.FlowerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllFLowersUseCase implements Supplier<Flux<Flower>> {

    private final FlowerGateway flowerGateway;

    @Override
    public Flux<Flower> get() {
        return flowerGateway.getAllFlowers();
    }
}
