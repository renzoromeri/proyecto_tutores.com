package com.web.tutores.Repositorios;

import com.web.tutores.Entidades.Foto;
import com.web.tutores.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {

    @Query(value = "SELECT f.* usuario u JOIN foto f ON u.foto_id = f.id WHERE u.foto_id = :id ", nativeQuery = true)
    public Foto buscarPorIdCliente(@Param("id") String id);

}
