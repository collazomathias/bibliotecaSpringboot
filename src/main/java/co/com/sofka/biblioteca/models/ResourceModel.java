package co.com.sofka.biblioteca.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resources")
public class ResourceModel {

    @Id
    private String id = UUID.randomUUID().toString();

    private String fechaPrestamo;
    private Boolean disponible;
    private String nombre;
    private String tipo;
    private String tema;

    public ResourceModel(){}

    public ResourceModel(String fechaPrestamo, Boolean disponible, String nombre, String tipo, String tema) {
        this.fechaPrestamo = fechaPrestamo;
        this.disponible = disponible;
        this.nombre = nombre;
        this.tipo = tipo;
        this.tema = tema;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

}
