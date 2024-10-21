package com.cefet.dolphub.Repositorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cefet.dolphub.Entidades.Main.Matricula;
import com.cefet.dolphub.Entidades.Main.Usuario;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByUsuario(Usuario usuario);
}
