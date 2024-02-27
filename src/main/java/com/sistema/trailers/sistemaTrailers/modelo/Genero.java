package com.sistema.trailers.sistemaTrailers.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Genero {

    @Id
    @Column(name = "id_genero")
    private Integer id;

    private String titulo;
}
