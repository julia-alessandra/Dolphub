package com.cefet.dolphub.view;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.cefet.dolphub.Entidades.Comunicacao.Pergunta;
import com.cefet.dolphub.Entidades.Comunicacao.Resposta;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.PerguntaService;
import com.cefet.dolphub.Service.RespostaService;

@Controller
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @Autowired
    private PerguntaService perguntaService;

    @PostMapping("/acessoCurso/{idCurso}/forum/pergunta/{perguntaId}")
    public String responderPergunta(@PathVariable("idCurso") Long idCurso, @PathVariable Long perguntaId,
            @ModelAttribute Resposta resposta, @AuthenticationPrincipal Usuario usuarioLogado) {
        Pergunta pergunta = perguntaService.buscarPorId(perguntaId);
        resposta.setPergunta(pergunta);
        resposta.setAutor(usuarioLogado.getNome());
        Date now = new Date(System.currentTimeMillis());
        resposta.setData(now);
        respostaService.salvarResposta(resposta);
        return "redirect:/acessoCurso/" + idCurso + "/forum";
    }
}
