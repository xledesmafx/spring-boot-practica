package com.sistema.trailers.sistemaTrailers.servicio;

import com.sistema.trailers.sistemaTrailers.Excepciones.AlmacenExcepcion;
import com.sistema.trailers.sistemaTrailers.Excepciones.FileNotFoundException;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import javax.xml.xpath.XPath;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AlmacenServicioImplementacion implements AlmacenServicio{

    @Value("${storage.location}")
    private String storageLocation;

    //SIRVE PARA INDICARLE QUE ESTE METODO SE VA A EJECUTAR CADA VEX QUE HAYA UNA NUEVA INSTANCIA DE ESTA CLASE
    @PostConstruct
    @Override
    public void iniciarAlmacenArchivos() {
        try {
            //MOSTRAMOS DONDE ALMACENAREMOS LAS FOTOS
            Files.createDirectories(Paths.get(storageLocation));
        }catch (IOException excepcion){
            throw new AlmacenExcepcion("ERROR AL INCIALIZAR LA UBICACION EN EL ALMACEN DE ARCHIVOS");
        }
    }

    @Override
    public String almacenarArchivo(MultipartFile archivo) {
        String nombreArchivo = archivo.getOriginalFilename();
        if(archivo.isEmpty()){
            throw new AlmacenExcepcion("NO SE PUEDE ALMACENAR UN ARCHIVO VACIO");
        }
        try {
            InputStream inputStream = archivo.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException excepcion){
            throw new AlmacenExcepcion("ERROR AL ALMACENAR EL ARCHIVO" + nombreArchivo,excepcion);
        }
        return nombreArchivo;
    }

    @Override
    public Path cargarArchivo(String nombreArchivo) {
        return Paths.get(storageLocation).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {
        try {
            Path archivo = cargarArchivo(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());

            if (recurso.exists() || recurso.isReadable()){
                return recurso;
            }else {
                throw new FileNotFoundException("NO SE PUDO ENCONTRAR EL ARCHIVO" + nombreArchivo);
            }
        }catch (MalformedURLException excepcion){
            throw new FileNotFoundException("NO SE PUDO ENCONTRAR EL ARCHIVO" + nombreArchivo);
        }
    }

    @Override
    public void eliminarArchivo(String nombreArhivo) {
        Path archivo = cargarArchivo(nombreArhivo);
        try {
            FileSystemUtils.deleteRecursively(archivo);
        }catch (Exception excepcion){
            System.out.println(excepcion);
        }
    }
}
