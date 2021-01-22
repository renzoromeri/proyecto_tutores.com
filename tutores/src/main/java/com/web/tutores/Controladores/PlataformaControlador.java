package com.web.tutores.Controladores;

import com.web.tutores.Entidades.Materia;
import com.web.tutores.Entidades.Tutor;
import com.web.tutores.Entidades.Usuario;
import com.web.tutores.Entidades.Zona;
import com.web.tutores.Errores.ErrorServicio;
import com.web.tutores.Repositorios.MateriaRepositorio;
import com.web.tutores.Repositorios.FotoRepositorio;
import com.web.tutores.Repositorios.UsuarioRepositorio;
import com.web.tutores.Repositorios.ZonaRepositorio;
import com.web.tutores.Servicio.MateriaServicio;
import com.web.tutores.Servicio.TutorServicio;
import com.web.tutores.Servicio.ZonaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PlataformaControlador extends Controlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TutorServicio tutorServicio;

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private MateriaServicio materiaServicio;

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Autowired
    private ZonaServicio zonaServicio;

    @Autowired
    private FotoRepositorio fotoRepositorio;

//    @Autowired
//    private ZonaRepositorio zonaRepositorio;
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o Clave incorrecto.");
        }
        if (logout != null) {
            model.put("logout", "Ha cerrado sesion correctamente.");

        }
        return "login.html";
    }

    @GetMapping("/registro")
    public String registro(ModelMap modelo) {
//        List<Zona> zonas = zonaRepositorio.findAll();
//        modelo.put("zonas", zonas);
        return "registro2.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO') || hasAnyRole('ROLE_TUTOR')")
    @GetMapping("/inicio")
    public String inicio(ModelMap model, HttpSession session) {
        try {
            if (tutorLogueado().getRol().toString().equals("TUTOR") && tutorLogueado().getBaja() == null) {
                session.setAttribute("clientesession", tutorLogueado());
                return "redirect:/tutor/inicioTutor";
            } else {
                if (tutorLogueado().getRol().toString().equals("TUTOR")) {
                    session.setAttribute("clientesession", tutorLogueado());
                    return "redirect:/tutor/altaTutor";
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            if (usuarioLogueado().getRol().toString().equals("USUARIO") && usuarioLogueado().getBaja() == null) {
                session.setAttribute("clientesession", usuarioLogueado());
                return "inicio.html";
            } else {
                session.setAttribute("clientesession", usuarioLogueado());
                return "redirect:/usuario/altaAlumno";
            }
        }

    }

    @GetMapping("/perfil")
    public String perfil() {
        return "perfil.html";
    }

    @GetMapping("/exito")
    public String exito(ModelMap modelo) {

        //modelo.put("titulo", "¡Bienvenido nuevamente !");
        //modelo.put("descripcion", "Tu usuario fue registrado correctamene, ¡¡Bienvenido!!");
        return "exito.html";
    }

    @GetMapping("/configuracion")
    public String configuracion(ModelMap modelo) {

        //modelo.put("titulo", "¡Bienvenido nuevamente !");
        return "configuracionGral.html";
    }

    public Usuario autentificacion() {

        Usuario usuario;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            usuario = usuarioRepositorio.buscarPorMail(auth.getName());
            return usuario;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/listar")
    public String listar(HttpSession session, @RequestParam(required = false) String q, ModelMap model) {

        ModelAndView modelV = new ModelAndView("inicio");

        List<Tutor> tutores;

        if (q == null || q.isEmpty()) {
            tutores = tutorServicio.listarActivos();
        } else {
            tutores = tutorServicio.listarActivos(q);
        }

        model.put("tutores", tutores);
        session.setAttribute("clientesession", usuarioLogueado());

        return "inicio.html";
    }

    @GetMapping("/crearMateria")
    public String crearMateria() {

        return "crearMateria.html";
    }

    @GetMapping("/crearZona")
    public String crearZona() {

        return "crearZona.html";
    }

    @GetMapping("/editarMateria")
    public String editarMateria(@RequestParam String id, ModelMap modelo) throws ErrorServicio {

//        List<Materia> materias = materiaRepositorio.findAll();
//        modelo.put("materias", materias);
        Materia materia = materiaServicio.buscarPorId(id);
        modelo.addAttribute("materia", materia);

        return "editarMateria.html";
    }

    @GetMapping("/seleccionarMateria")
    public String seleccionarMateria(ModelMap modelo) {

        List<Materia> materias = materiaRepositorio.findAll();
        modelo.put("materias", materias);

//        Materia materia = materiaServicio.buscarPorId(id);
//        modelo.addAttribute("materia", materia);
        return "seleccionarMateria.html";
    }

    @GetMapping("/editarZona")
    public String editarZona(@RequestParam String id, ModelMap modelo) throws ErrorServicio {

//        List<Zona> zonas = zonaRepositorio.findAll();
//        modelo.put("zonas", zonas);
        Zona zona = zonaServicio.buscarPorId(id);
        modelo.addAttribute("zona", zona);

        return "editarZona.html";
    }

    @GetMapping("/seleccionarZona")
    public String seleccionarZona(ModelMap modelo) {

        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);

//        Materia materia = materiaServicio.buscarPorId(id);
//        modelo.addAttribute("materia", materia);
        return "seleccionarZona.html";
    }

}
