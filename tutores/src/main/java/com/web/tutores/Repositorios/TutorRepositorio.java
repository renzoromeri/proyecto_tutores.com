package com.web.tutores.Repositorios;

import com.web.tutores.Entidades.Tutor;
import com.web.tutores.Entidades.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepositorio extends JpaRepository<Tutor, String> {

    @Query("SELECT a FROM Tutor a WHERE a.nombre = :nombre AND a.apellido = :apellido")
    public Tutor buscarPorNombre(@Param("nombre") String nombre, @Param("apellido") String apellido);

    @Query("SELECT a FROM Tutor a ORDER BY a.apellido ")
    public List<Tutor> listaTutores();

    @Query("SELECT a FROM Tutor a WHERE a.zona.nombre = :nombre")
    public List<Tutor> buscarPorZona(@Param("nombre") String nombre);

    @Query("SELECT a FROM Tutor a, IN(a.materia) m WHERE m.nombre = :nombre")
    public List<Tutor> buscarPorMateria(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM Tutor c WHERE c.mail = :mail")
    public Tutor buscarPorMail(@Param("mail") String mail);


//    @Query("SELECT a from Tutor a WHERE a.eliminado IS NULL AND a.nombre LIKE :nombre")
//    public Page<Actividad> buscarActivos(Pageable pageable, @Param("nombre") String nombre);
//
//    @Query("SELECT a from Actividad a WHERE a.eliminado IS NULL AND a.nombre LIKE :nombre")
//    public Page<Actividad> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

//    public Page<Actividad> listarActivos(String q) {
//        return actividadRepository.buscarActivos(paginable, "%" + q + "%");
//    }
//
//    @Query("SELECT h from Tutor h, IN(h.Materia) v, IN(h.Zona) a "
//            + "WHERE h.baja IS NULL "
//            + "AND h.nombre LIKE :q "
//            + "OR h.apellido LIKE :q "
////            + "OR v.dni LIKE :q "
//            + "OR a.nombre LIKE :q "
//            + "OR v.nombre LIKE :q "
//            + "OR v.nivel LIKE :q "
//            + "OR v.asignatura LIKE :q "
////            + "OR a.lastName LIKE :q "
////            + "OR a.dni LIKE :q "
////            + "OR h.factTimeString LIKE :q "
//            + "ORDER BY h.factTimeString ")
//    public Page<HelpRequest> searchActives(@Param("q") String q);
//
//    public Page<HelpRequest> toList(Pageable paginable, String q) {
//        return helpRequestRepository.searchActives(paginable, "%" + q + "%");
//    }

//    @Query("SELECT a from Tutor a WHERE a.baja IS NULL AND a.nombre LIKE :nombre")
//    public Page<Tutor> buscarActivos(Pageable pageable, @Param("nombre") String nombre);
    
    @Query("SELECT a from Tutor a WHERE a.baja IS NULL")
    public List<Tutor> buscarActivos();

    @Query("SELECT h from Tutor h "
            + ", IN(h.materia) v"
            + " WHERE h.baja IS NULL "
            + " AND (h.nombre LIKE :q "
            + " OR h.apellido LIKE :q "
            +  " OR h.zona.nombre LIKE :q "
            + " OR v.nombre LIKE :q "
            + " OR v.nivelEducativo LIKE :q "
            + " OR v.asignatura LIKE :q )")
    public List<Tutor> buscarActivos(@Param("q") String q);


}
