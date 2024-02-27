package com.sistema.trailers.sistemaTrailers.servicio;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface AlmacenServicio {

    public void iniciarAlmacenArchivos();

    public String almacenarArchivo(MultipartFile archivo);

    public Path cargarArchivo(String nombreArchivo);

    public org.springframework.core.io.Resource cargarComoRecurso(String nombreArchivo);

    public void eliminarArchivo(String nombreArhivo);
}
