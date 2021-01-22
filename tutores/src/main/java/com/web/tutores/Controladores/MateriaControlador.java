package com.web.tutores.Controladores;

import com.web.tutores.Entidades.Materia;
import com.web.tutores.Enums.Asignatura;
import com.web.tutores.Enums.NivelEducativo;
import com.web.tutores.Errores.ErrorServicio;
import com.web.tutores.Repositorios.MateriaRepositorio;
import com.web.tutores.Servicio.MateriaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/materia")
public class MateriaControlador {

    @Autowired
    private MateriaServicio materiaServicio;
    
    @Autowired
    private MateriaRepositorio materiaRepositorio;
    
    @PostMapping("/registraMateria")
    public String registraMateria(ModelMap modelo, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam Asignatura asignatura, @RequestParam NivelEducativo nivel) {
        try {
            materiaServicio.agregarMateria(nombre, descripcion, asignatura, nivel);
        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);
            modelo.put("asignatura", asignatura);
            modelo.put("nivel", nivel);
            
            return "crearMateria.html";
        }
        
        modelo.put("titulo", "Tu materia fue registrado correctamene!");

        return "configuracionGral.html";
    }
    
    
    @PostMapping("/cambioMateria")
    public String cambioMateria(@RequestParam String id, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam Asignatura asignatura, @RequestParam NivelEducativo nivel, ModelMap modelo) {
        
        Materia materia = null;
        System.out.println("+++++++++++++++++"+id);
        
        try {
            System.out.println("+++++++++++++++++"+id);
            materia = materiaServicio.buscarPorId(id);
            
            modelo.put("materia", materia);
            
            materiaServicio.modificar(id, nombre, descripcion, asignatura, nivel);
            

            
        } catch (ErrorServicio ex) {
            
//            List<Materia> materias = materiaRepositorio.findAll();
//            modelo.put("materias", materias);
//            modelo.put("error", ex.getMessage());
//            modelo.put("materia", materia);
            
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);
            modelo.put("asignatura", asignatura);
            modelo.put("nivel", nivel);
            
            return "editarMateria.html";
        }    
        
        modelo.put("titulo", "Tu materia fue modificada exitosamente!");

        return "configuracionGral.html";
    }
    
    
    
//    
//    
//        @PostMapping("/actualizar-perfil")
//    public String actualizar(ModelMap modelo,
//            MultipartFile archivo, @RequestParam String id,
//            @RequestParam String nombre, String apellido,
//            String mail, String clave1, String clave2,
//            String idZona) {
//        Usuario usuario = null;
//
//        try {
//            usuario = usuarioServicio.buscarPorId(id);
//            usuarioServicio.modificar(archivo, id, nombre, apellido, mail, clave1, clave2, idZona);
//            return "redirect:/inicio";
//        } catch (ErrorServicio ex) {
//            List<Zona> zonas = zonaRepositorio.findAll();
//            modelo.put("zonas", zonas);
//            modelo.put("error", ex.getMessage());
//            modelo.put("perfil", usuario);
//
//            return "perfilAlumno.html";
//        }
//
//    }
    
    
    

    
    
    
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo) {

        return "modificar.html";
    }

    @GetMapping("/deshabilitar")
    public String deshabilitar(ModelMap modelo) {

        return "deshabilitar.html";

    }

    @GetMapping("/habilitar")
    public String habilitar(ModelMap modelo) {

        return "habilitar.html";
    }

}