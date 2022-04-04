package co.com.sofka.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.sofka.biblioteca.models.ResourceModel;
import co.com.sofka.biblioteca.services.ResourceService;

import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/resources")
@CrossOrigin(origins = "*")

public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @GetMapping("/borrowed/{id}")
    public Mono<String> isBorrowed(@PathVariable("id") String id){
        return this.resourceService.isBorrowed(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResourceModel> addNewResource(@RequestBody ResourceModel resource){
        return this.resourceService.addNewResource(resource);
    }

    @PutMapping("/lend/{id}")
    public Mono<Object> lendResource(@PathVariable("id") String id){
        return this.resourceService.lendResource(id);
    }

    @PutMapping("/giveback/{id}")
    public Mono<Object> giveBackResource(@PathVariable("id") String id){
        return this.resourceService.giveBackResource(id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteResource(@PathVariable("id") String id){
        return this.resourceService.deleteResource(id);
    }

    @GetMapping("/recommend/{type}/{area}")
    public Flux<ResourceModel> RecommendResourceByTypeAndArea(@PathVariable("type") String type, @PathVariable("area") String area){
        return this.resourceService.RecommendResourceByTypeAndArea(type, area);
    }

    @GetMapping
    public Flux<ResourceModel> getAllResources() {
        return this.resourceService.getAllResources();
    }

    @PutMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Mono<ResponseEntity<ResourceModel>> updateResource(@PathVariable("id") String id, @RequestBody ResourceModel resource) {
        return this.resourceService.updateResource(id, resource)
                .flatMap(resource1 -> Mono.just(ResponseEntity.ok(resource1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

}
