package com.sistema.trailers.sistemaTrailers.respositorios;

import com.sistema.trailers.sistemaTrailers.modelo.Genero;
import com.sistema.trailers.sistemaTrailers.modelo.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer> {
}
