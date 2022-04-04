package co.com.sofka.biblioteca.repositories;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import co.com.sofka.biblioteca.models.ResourceModel;
import reactor.core.publisher.Flux;
public interface ResourceRepository extends  ReactiveMongoRepository<ResourceModel, String> {
    public Flux<ResourceModel> findByType(String tipo);
}
