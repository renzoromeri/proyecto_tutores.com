package com.web.tutores.Servicio;

import com.web.tutores.Entidades.Zona;
import com.web.tutores.Enums.Asignatura;
import com.web.tutores.Enums.NivelEducativo;
import com.web.tutores.Errores.ErrorServicio;
import com.web.tutores.Repositorios.ZonaRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZonaServicio {
    
    @Autowired
    private ZonaRepositorio zonaRepositorio;
    
    @Transactional
    public void agregarZona(String nombre, String descripcion) throws ErrorServicio{
        
        validar(nombre, descripcion);
        
        Zona zona = new Zona();
        
        zona.setNombre(nombre);
        zona.setDescripcion(descripcion);
        
        zonaRepositorio.save(zona);
        
    }
    
    @Transactional
    public void editarZona(String id, String nombre, String descripcion) throws ErrorServicio{
        validar(nombre,descripcion);
        
        Optional<Zona> respuesta = zonaRepositorio.findById(id);
                
        if (respuesta.isPresent()) {
            
             Zona zona = zonaRepositorio.findById(id).get();
            
            
            zona.setNombre(nombre);
            zona.setDescripcion(descripcion);
            
            zonaRepositorio.save(zona);
    }else
            throw new ErrorServicio("No existe una zona con el identificador solicitado.");
    }
    
    
    

    
    
     public void validar(String nombre, String descripcion) throws ErrorServicio{

        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre no puede ser nulo o vacio");
        }
        
//        if(descripcion == null || nombre.isEmpty()){
//            
//            throw new ErrorServicio("La descripcion no puede ser nula");
//        }
        
    }
     
     
         
        public Zona buscarPorId(String id) throws ErrorServicio {
        Optional<Zona> respuesta = zonaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Zona zona = respuesta.get();
            return zona;

        } else {
            throw new ErrorServicio("No se encontro la zona solicitada.");
        }
    }
    
    
    
}