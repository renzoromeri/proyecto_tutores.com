
package com.web.tutores.Repositorios;

import com.web.tutores.Entidades.Materia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia, String>{
    
//    @Query("SELECT c FROM Materia c WHERE c.materia.id = :id")
//    public List<Materia> buscarMateriaPorId(@Param("id") String id);
//    
//    @Query("SELECT c FROM Materia c WHERE c.materia.nombre = :nombre")
//    public List<Materia> buscarMateriaPorNombre(@Param("nombre") String nombre);
//    
//    //@Query("SELECT c FROM Materia c WHERE c.materia.asignatura")
//    
//    
}
