package com.web.tutores.Controladores;

import com.web.tutores.Entidades.Usuario;
import com.web.tutores.Entidades.Zona;
import com.web.tutores.Errores.ErrorServicio;
import com.web.tutores.Repositorios.ZonaRepositorio;
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
@RequestMapping("/usuario")
public class UsuarioControlador extends Controlador {

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @GetMapping("/registroAlumno")
    public String registro(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        return "registroAlumno.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO') || hasAnyRole('ROLE_TUTOR')")
    @GetMapping("/editar-perfil/{id}")
    public String editarPerfil(@PathVariable String id, ModelMap model) throws ErrorServicio {

        List<Zona> zonas = zonaRepositorio.findAll();
        model.put("zonas", zonas);

        Usuario usuario = usuarioServicio.buscarPorId(id);

        model.addAttribute("perfil", usuario);

        return "perfilAlumno.html";
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

    @PostMapping("/registrarAlumno")
    public String registrar(ModelMap modelo, MultipartFile archivo,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String mail,
            @RequestParam String clave,
            @RequestParam String clave2, @RequestParam String telefono, String idZona) {
        try {
            usuarioServicio.registrar(archivo, nombre, apellido, mail, clave, clave2, telefono, idZona);
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

            return "registroAlumno.html";
        }
        modelo.put("titulo", "¡Bienvenido a la comunidad de Tutores.com !");
        modelo.put("descripcion", "Tu usuario fue registrado correctamene, ¡¡Bienvenido!!");
        return "exito.html";
    }

    @PostMapping("/actualizar-perfil")
    public String actualizar(ModelMap modelo,
            MultipartFile archivo, @RequestParam String id,
            @RequestParam String nombre, String apellido,
            String mail, String clave1, String clave2,
            String idZona, String telefono) {
        Usuario usuario = null;

        try {
            usuario = usuarioServicio.buscarPorId(id);
            usuarioServicio.modificar(archivo, id, nombre, apellido, mail, clave1, clave2, idZona, telefono);
            return "redirect:/inicio";
        } catch (ErrorServicio ex) {
            List<Zona> zonas = zonaRepositorio.findAll();
            modelo.put("zonas", zonas);
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);

            return "perfilAlumno.html";
        }

    }
    
    @GetMapping("/elimina-Alumno")
    public String elimina(@RequestParam String id, ModelMap model) throws ErrorServicio {
        Usuario usuario = usuarioServicio.buscarPorId(id);
        model.addAttribute("perfil", usuario);
        return "eliminaAlumno.html";
    }

    @PostMapping("/bajaAlumno")
    public String bajaAlumno(@RequestParam String id, ModelMap modelo) {
        Usuario usuario = null;

        try {
            usuario = usuarioServicio.buscarPorId(id);
            usuarioServicio.darDeBajaTutor(id);
            modelo.put("perfil", usuario);

        } catch (ErrorServicio e) {

            return "error.html";
        }
        modelo.put("titulo", "¡Ya no pertences a la comunidad de Tutores.com !");
        modelo.put("descripcion", "Puedes volver cuando quieras!! Te esperamos!!");
        return "exito.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @GetMapping("/altaAlumno")
    public String altaAlumno(HttpSession session) {

        session.setAttribute("clientesession", usuarioLogueado());
        return "altaAlumno.html";
    }
    
    @GetMapping("/altaAlumno2")
    public String altaAlumno(@RequestParam String id, ModelMap model) throws ErrorServicio {
        Usuario usuario = usuarioServicio.buscarPorId(id);
        model.addAttribute("perfil", usuario);
        return "altaAlumno2.html";
    }
    
    @PostMapping("/darDeAltaAlumno")
    public String darDeAltaAlumno(@RequestParam String id, ModelMap modelo) {
        Usuario usuario = null;

        try {
            usuario = usuarioServicio.buscarPorId(id);
            usuarioServicio.darDeAltaAlumno(id);
            modelo.put("perfil", usuario);

        } catch (ErrorServicio e) {

            return "error.html";
        }
        modelo.put("titulo", "¡Volviste a pertencer a la comunidad de Tutores.com !");
        modelo.put("descripcion", "Bienvenido!!");
        return "exito.html";
    }
}


