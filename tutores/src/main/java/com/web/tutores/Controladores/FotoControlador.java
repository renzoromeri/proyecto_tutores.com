
package com.web.tutores.Controladores;

import com.web.tutores.Entidades.Foto;
import com.web.tutores.Repositorios.FotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @GetMapping("/cargar/{id}")
    public ResponseEntity<byte[]> cargarFoto(@PathVariable String id) {
        Foto foto = fotoRepositorio.buscarPorIdCliente(id);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(foto.getContenido(), headers, HttpStatus.OK);
    }

}
