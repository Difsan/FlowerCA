package co.diego.mongo;

import co.diego.model.flower.Flower;
import co.diego.model.flower.gateways.FlowerGateway;
import co.diego.mongo.data.FlowerData;
import co.diego.mongo.helper.AdapterOperations;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapter implements FlowerGateway {

    private final MongoDBRepository repository;

    private final ObjectMapper mapper;
    @Override
    public Flux<Flower> getAllFlowers() {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(flowerData -> mapper.map(flowerData, Flower.class));
    }

    @Override
    public Mono<Flower> getFlowerById(String flowerId) {
        return this.repository
                .findById(flowerId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "flower with id: " + flowerId)))
                .map(flowerData -> mapper.map(flowerData, Flower.class));
    }

    @Override
    public Mono<Flower> saveFlower( Flower flower) {
        return this.repository
                .save(mapper.map(flower, FlowerData.class))
                .switchIfEmpty(Mono.empty())
                .map(flowerData -> mapper.map(flowerData, Flower.class));
    }

    @Override
    public Mono<Flower> updateFlower(String flowerId, Flower flower) {
        return this.repository
                .findById(flowerId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "flower with id: " + flowerId)))
                .flatMap(flowerData -> {
                    flower.setId(flowerData.getId());
                    return repository.save(mapper.map(flower, FlowerData.class));
                })
                .map(flowerData -> mapper.map(flowerData, Flower.class));
    }

    @Override
    public Mono<Void> deleteFlower(String flowerId) {
        return this.repository
                .findById(flowerId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                "flower with id: " + flowerId)))
                .flatMap(flowerData -> this.repository.deleteById(flowerData.getId()));
    }
}
