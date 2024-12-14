package com.cefet.dolphub.Repositorio;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.*;

@Repository
public interface ArquivoBaixadoRepository extends JpaRepository<ArquivosBaixados, Long> {
    List<ArquivosBaixados> findByUsuarioId(Long idUsuario);
}
