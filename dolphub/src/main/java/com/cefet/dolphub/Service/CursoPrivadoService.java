package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cefet.dolphub.Repositorio.CursoPrivadoRepository;
import com.cefet.dolphub.Entidades.Main.CursoPrivado;
import java.util.Optional;

@Service
public class CursoPrivadoService {
    @Autowired
    private CursoPrivadoRepository cursoPrivadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isCursoPrivado(Long cursoId) {
        Optional<CursoPrivado> cursoPrivado = cursoPrivadoRepository.findByCursoId(cursoId);
        return cursoPrivado.isPresent();
    }

    public boolean verificarSenha(Long cursoId, String senhaFornecida) {
        Optional<CursoPrivado> cursoPrivadoOpt = cursoPrivadoRepository.findByCursoId(cursoId);

        if (cursoPrivadoOpt.isPresent()) {
            CursoPrivado cursoPrivado = cursoPrivadoOpt.get();
            return passwordEncoder.matches(senhaFornecida, cursoPrivado.getSenha());
        }
        return false;
    }

    public void salvarCursoPrivado(CursoPrivado cursoPrivado) {
        String senhaCriptografada = passwordEncoder.encode(cursoPrivado.getSenha());
        cursoPrivado.setSenha(senhaCriptografada);
        cursoPrivadoRepository.save(cursoPrivado);

    }

    public List<CursoPrivado> listarCursosPrivados() {
        return cursoPrivadoRepository.findAll();

    }

    public void deletarCurso(Long cursoId) {
        Optional<CursoPrivado> cursoPrivadoOpt = cursoPrivadoRepository.findByCursoId(cursoId);

        if (cursoPrivadoOpt.isPresent()) {
            cursoPrivadoRepository.delete(cursoPrivadoOpt.get());
        }
    }

}
