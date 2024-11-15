package com.cefet.dolphub.view;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ForumController {
    @GetMapping("/novaPergunta")
    public String exibirPergunta(Model model) {
        return "nova_pergunta_forum";
    }
}
