package co.diego.mongo;

import co.diego.mongo.data.FlowerData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepository extends ReactiveMongoRepository<FlowerData, String>{
}
