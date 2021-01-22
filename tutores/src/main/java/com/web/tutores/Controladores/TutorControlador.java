package com.web.tutores.Controladores;

import com.web.tutores.Entidades.Materia;
import com.web.tutores.Entidades.Tutor;
import com.web.tutores.Entidades.Zona;
import com.web.tutores.Errores.ErrorServicio;
import com.web.tutores.Repositorios.MateriaRepositorio;
import com.web.tutores.Repositorios.ZonaRepositorio;
import com.web.tutores.Servicio.TutorServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/tutor")
public class TutorControlador extends Controlador {

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private TutorServicio tutorServicio;

    @PreAuthorize("hasAnyRole('ROLE_TUTOR')")
    @GetMapping("/inicioTutor")
    public String inicioTutor(HttpSession session) {

        session.setAttribute("clientesession", tutorLogueado());
        return "inicioTutor.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_TUTOR')")
    @GetMapping("/altaTutor")
    public String altaTutor(HttpSession session) {

        session.setAttribute("clientesession", tutorLogueado());
        return "altaTutor.html";
    }

    @GetMapping("/registroTutor")
    public String registro(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        List<Materia> materias = materiaRepositorio.findAll();
        modelo.put("materias", materias);

        modelo.put("titulo", "¡Bienvenido nuevamente !");

        return "registroTutor.html";
    }

    @GetMapping("/editar-tutor")
    public String modificar(@RequestParam String id, ModelMap model) throws ErrorServicio {
        List<Zona> zonas = zonaRepositorio.findAll();
        model.put("zonas", zonas);

        List<Materia> materias = materiaRepositorio.findAll();
        model.put("materias", materias);

        Tutor tutor = tutorServicio.buscarPorId(id);
        model.addAttribute("perfil", tutor);

        return "perfilTutor4.html";
    }

    @GetMapping("/deshabilitar")
    public String deshabilitar(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        return "deshabilitar.html";
    }

    @GetMapping("/habilitar")
    public String habilitar(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        return "habilitar.html";
    }

    @PostMapping("/registrarTutor")
    public String registrar(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave, @RequestParam String clave2, @RequestParam String telefono, String descripcion, String idZona, String idMateria) {
        try {
            tutorServicio.crearTutor(archivo, nombre, apellido, mail, clave, clave2, telefono, idZona, idMateria, descripcion);
        } catch (ErrorServicio ex) {
            List<Zona> zonas = zonaRepositorio.findAll();
            modelo.put("zonas", zonas);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave", clave);
            modelo.put("clave2", clave2);
            modelo.put("telefono", telefono);
            List<Materia> materias = materiaRepositorio.findAll();
            modelo.put("materias", materias);
            modelo.put("descripcion", descripcion);

            return "registroTutor.html";
        }

        modelo.put("titulo", "¡Bienvenido a la comunidad de Tutores.com !");
        modelo.put("descripcion", "Te has registrado correctamene como Tutor, ¡¡Bienvenido!!");
        return "exito.html";

    }

    @PostMapping("/actualizar-perfilTutor")
    public String actualizar(ModelMap modelo,
            @RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String mail,
            @RequestParam String clave,
            @RequestParam String clave2,
            @RequestParam String telefono,
            @RequestParam String descripcion,
            @RequestParam String idZona, @RequestParam String idMateria) {
        Tutor tutor = null;

        try {
            tutor = tutorServicio.buscarPorId(id);
            tutorServicio.modificarTutor(id, nombre, apellido, mail, clave, clave2, telefono, idZona, idMateria, descripcion);

        } catch (ErrorServicio ex) {
            List<Zona> zonas = zonaRepositorio.findAll();
            modelo.put("zonas", zonas);
            List<Materia> materias = materiaRepositorio.findAll();
            modelo.put("materias", materias);
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", tutor);

            return "perfilTutor4.html";
        }
        return "redirect:/tutor/inicioTutor";
    }

    @GetMapping("/enviarTutor/{idTutor}")
    public String enviarTutor(@PathVariable String idTutor, ModelMap modelo) throws ErrorServicio {
//        String id = idTutor;
        Tutor tutor = tutorServicio.buscarPorId(idTutor);
        modelo.addAttribute("tutor", tutor);
        return "mostrarTutor.html";
    }

    @GetMapping("/mostrarTutor/{id}")
    public String mostrarTutor(@PathVariable String id, ModelMap modelo, HttpSession session) {
        Tutor tutor = null;
        try {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++" + id);
            tutor = tutorServicio.buscarPorId(id);
            modelo.put("tutor", tutor);
            session.setAttribute("clientesession", usuarioLogueado());

        } catch (ErrorServicio e) {

            return "error.html";

        }
        return "mostrarTutor.html";
    }

    @GetMapping("/elimina-Tutor")
    public String elimina(@RequestParam String id, ModelMap model) throws ErrorServicio {
        Tutor tutor = tutorServicio.buscarPorId(id);
        model.addAttribute("perfil", tutor);
        return "eliminaTutor.html";
    }

    @PostMapping("/bajaTutor")
    public String bajaTutor(@RequestParam String id, ModelMap modelo) {
        Tutor tutor = null;

        try {
            tutor = tutorServicio.buscarPorId(id);
            tutorServicio.darDeBajaTutor(id);
            modelo.put("perfil", tutor);

        } catch (ErrorServicio e) {

            return "error.html";
        }
        modelo.put("titulo", "¡Ya no pertences a la comunidad de Tutores.com !");
        modelo.put("descripcion", "Puedes volver cuando quieras!! Te esperamos!!");
        return "exito.html";
    }
    
    @GetMapping("/altaTutor2")
    public String altaTutor(@RequestParam String id, ModelMap model) throws ErrorServicio {
        Tutor tutor = tutorServicio.buscarPorId(id);
        model.addAttribute("perfil", tutor);
        return "altaTutor2.html";
    }
    
    @PostMapping("/darDeAltaTutor")
    public String darDeAltaTutor(@RequestParam String id, ModelMap modelo) {
        Tutor tutor = null;

        try {
            tutor = tutorServicio.buscarPorId(id);
            tutorServicio.darDeAltaTutor(id);
            modelo.put("perfil", tutor);

        } catch (ErrorServicio e) {

            return "error.html";
        }
        modelo.put("titulo", "¡Volviste a pertencer a la comunidad de Tutores.com !");
        modelo.put("descripcion", "Bienvenido!!");
        return "exito.html";
    }
}