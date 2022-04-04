package co.com.sofka.biblioteca.services.Impl;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.sofka.biblioteca.models.ResourceModel;
import co.com.sofka.biblioteca.repositories.ResourceRepository;
import co.com.sofka.biblioteca.services.ResourceService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class ResourceServiceImplementation implements ResourceService {

    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Mono<ResourceModel> getResource(String id) { 
        return this.resourceRepository.findById(id);
    }

    @Override
    public Mono<String> isBorrowed(String id) {
        Mono<ResourceModel> resource = resourceRepository.findById(id);
        try {
            return preConsult(resource);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    @Override
    public Mono<Object> lendResource(String id) {
        return resourceRepository.findById(id)
            .flatMap(resource -> {
                if(resource.getDisponible()) {
                    return Mono.just("The resource is on loan, sorry.");
                } 
                resource.setDisponible(true);
                LocalDate today = LocalDate.now();
                resource.setFechaPrestamo(today.toString());
                return resourceRepository.save(resource); 
            });
    }

    @Override
    public Mono<ResourceModel> addNewResource(ResourceModel Recurso) {
        return this.resourceRepository.save(Recurso);
    }

    @Override
    public Mono<Object> giveBackResource(String id) {
        return resourceRepository.findById(id)
            .flatMap(resource -> {
                if(!resource.getDisponible()) {
                    return Mono.just("The resource has already been given back.");
                } 
                resource.setDisponible(false);
                return resourceRepository.save(resource); 
            });
    }

    @Override
    public Mono<Void> deleteResource(String id) {
     return this.resourceRepository.deleteById(id);
    }

    @Override
    public Flux<ResourceModel> RecommendResourceByTypeAndArea(String type, String area) {
        return this.resourceRepository.findByType(type)
            .map(resource -> {
                return resource;
            })
            .filter(resource -> resource.getTema().equals(area));
    }

    @Override
    public Flux<ResourceModel> getAllResources() {
        return this.resourceRepository.findAll();
    }
    
    @Override
    public Mono<ResourceModel> updateResource(String id, ResourceModel resource) {
        return this.resourceRepository.findById(id)
            .flatMap(res -> {
                resource.setId(id);
                return addNewResource(resource);
            })
            .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<String> preConsult(Mono<ResourceModel> resource) throws InterruptedException, ExecutionException {
        return resource.map(libro -> {
            var resourceDisponibility = libro.getDisponible() ? "The resource is no available from date " + libro.getFechaPrestamo() : "The resource is available";
            return Mono.just(resourceDisponibility);
        }).toFuture().get();
    }
}
