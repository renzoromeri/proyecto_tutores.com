package com.web.tutores.Controladores;

import com.web.tutores.Entidades.Zona;
import com.web.tutores.Errores.ErrorServicio;
import com.web.tutores.Repositorios.ZonaRepositorio;
import com.web.tutores.Servicio.ZonaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/zona")
public class ZonaControlador {

    @Autowired
    private ZonaServicio zonaServicio;

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @PostMapping("/registraZona")
    public String registraZona(ModelMap modelo, @RequestParam String nombre, @RequestParam String descripcion) {
        try {
            zonaServicio.agregarZona(nombre, descripcion);

        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);

            return "crearZona.html";
        }

        modelo.put("titulo", "Hay una nueva zona disponible!");

        return "configuracionGral.html";
    }

    @PostMapping("/cambioZona")
    public String cambioZona(@RequestParam String id, @RequestParam String nombre, @RequestParam String descripcion, ModelMap modelo) {

        Zona zona = null;
        System.out.println("+++++++++++++++++" + id);

        try {
            System.out.println("+++++++++++++++++" + id);
            zona = zonaServicio.buscarPorId(id);

            modelo.put("zona", zona);

            zonaServicio.editarZona(id, nombre, descripcion);

//            modelo.put("titulo", "Tu zona fue modificada correctamente!");
//            return "redirect:/configuracionGral.html";
//              return "editarZona.html";
        } catch (ErrorServicio ex) {

//            List<Materia> materias = materiaRepositorio.findAll();
//            modelo.put("materias", materias);
//            modelo.put("error", ex.getMessage());
//            modelo.put("materia", materia);
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);

            return "editarZona.html";

        }

        modelo.put("titulo", "Tu zona fue modificada correctamente!");
        return "configuracionGral.html";
    }

}
