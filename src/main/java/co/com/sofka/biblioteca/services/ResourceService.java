package co.com.sofka.biblioteca.services;

import java.util.concurrent.ExecutionException;

import co.com.sofka.biblioteca.models.ResourceModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface ResourceService {
    public Mono<ResourceModel> getResource(String id);
    public Mono<String> isBorrowed(String id);
    public Mono<ResourceModel> addNewResource(ResourceModel resource);
    public Mono<Object> lendResource(String id);
    public Mono<Object> giveBackResource(String id);
    public Mono<Void> deleteResource(String id);
    public Flux<ResourceModel> RecommendResourceByTypeAndArea(String type, String area);
    public Flux<ResourceModel> getAllResources();
    public Mono<ResourceModel> updateResource(String id, ResourceModel resource);
    public Mono<String> preConsult(Mono<ResourceModel> resource) throws InterruptedException, ExecutionException;
}