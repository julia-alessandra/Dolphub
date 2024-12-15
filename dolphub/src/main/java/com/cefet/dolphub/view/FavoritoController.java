package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Recursos.Favorito;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cefet.dolphub.Service.FavoritoService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.cefet.dolphub.Entidades.Recursos.Recurso;

@Controller
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/favoritos")
    public String listarFavoritos(@AuthenticationPrincipal Usuario usuario, Model model) {
        List<Favorito> favoritos = favoritoService.listarFavoritosPorUsuario(usuario);
        model.addAttribute("favoritos", favoritos);
        return "favoritos";
    }

    @Transactional
    @GetMapping("/favoritos/adicionar/{idRecurso}/{idCurso}/{tipo}")
    public String adicionarFavorito(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long idRecurso,
            @PathVariable Long idCurso, @PathVariable String tipo) {
        try {
            Recurso recurso = favoritoService.buscarRecursoPorId(idRecurso);
            Curso curso = favoritoService.buscarCursoPorId(idCurso);

            if (recurso != null && curso != null) {
                Favorito favorito = new Favorito(usuario, recurso, new java.util.Date(), curso);
                favorito.setTipo(tipo);
                favoritoService.salvarFavorito(favorito);
            }
        } catch (JpaSystemException e) {
            e.printStackTrace();
        }
        return "redirect:/favoritos";
    }
}
