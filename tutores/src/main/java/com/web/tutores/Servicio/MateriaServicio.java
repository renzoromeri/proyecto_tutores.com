
package com.web.tutores.Servicio;

import com.web.tutores.Entidades.Materia;
import com.web.tutores.Enums.Asignatura;
import com.web.tutores.Enums.NivelEducativo;
import com.web.tutores.Errores.ErrorServicio;
import com.web.tutores.Repositorios.MateriaRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MateriaServicio {
    
    @Autowired
    private MateriaRepositorio materiaRepositorio;
    
    @Transactional
    public void agregarMateria(String nombre, String descripcion, Asignatura asignatura, NivelEducativo nivel) throws ErrorServicio{
        
        validar(nombre, asignatura, nivel);
        
        Materia materia = new Materia();
        
        materia.setNombre(nombre);
        materia.setDescripcion(descripcion);
        materia.setAlta(new Date());
        materia.setAsignatura(asignatura);
        materia.setNivelEducativo(nivel);
        
        materiaRepositorio.save(materia);
        
    }
    
    @Transactional
    public void modificar(String idMateria, String nombre, String descripcion, Asignatura asignatura, NivelEducativo nivel) throws ErrorServicio{
        validar(nombre, asignatura, nivel);
        
        Optional<Materia> respuesta = materiaRepositorio.findById(idMateria);
        
        if(respuesta.isPresent()){
            Materia materia = materiaRepositorio.findById(idMateria).get();
            
            materia.setNombre(nombre);
            materia.setDescripcion(descripcion);
            materia.setAsignatura(asignatura);
            materia.setNivelEducativo(nivel);
            
            materiaRepositorio.save(materia);
        }else{
            throw new ErrorServicio("No existe una materia con el identificador solicitado.");
        }
    }
    
    public void elimiar(String idMateria) throws ErrorServicio{
        
        Optional<Materia> respuesta = materiaRepositorio.findById(idMateria);
        if(respuesta.isPresent()){
            Materia materia = respuesta.get();
            materia.setBaja(new Date());
            materiaRepositorio.save(materia);
        }else{
            throw new ErrorServicio("No existe materia con el identificador solicitado.");
        }
    }
    

    public void validar(String nombre, Asignatura asignatura, NivelEducativo nivel) throws ErrorServicio{

        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre de la mascota no puede ser nulo o vacio");
        }
        
        if(asignatura == null){
            
            throw new ErrorServicio("La asignatura no puede ser nula");
        }
        
        if(nivel == null){
            
            throw new ErrorServicio("El nivel educativo no puede ser nulo");
        }
    }
    
    
        public Materia buscarPorId(String id) throws ErrorServicio {
            
        Optional<Materia> respuesta = materiaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Materia materia = respuesta.get();
            return materia;

        } else {
            throw new ErrorServicio("No se encontro la materia solicitada.");
        }
    }
    
}