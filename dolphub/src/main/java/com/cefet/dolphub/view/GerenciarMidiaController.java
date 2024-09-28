package com.cefet.dolphub.view;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Service.AcessoService;
import com.cefet.dolphub.Service.ArquivoService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/editarCurso")
public class GerenciarMidiaController {

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private AcessoService acessoService;

    @GetMapping
    public String editarCurso() {
        return "editar_curso";
    }

    @GetMapping("/{id}")
    public String acessarGerenciarCurso(@PathVariable Long id, Model model) {
        List<Recurso> recursos = acessoService.recuperarRecursosPorCurso(id);

        model.addAttribute("recursos", recursos);
        return "editar_curso";
    }

    @GetMapping("listarArquivos")
    public String listarArquivos(Model model) {
        List<Arquivo> arquivos = arquivoService.listarArquivos();
        model.addAttribute("arquivos", arquivos);
        return "listar_arquivos"; // Nome da página HTML
    }

    @GetMapping("/enviarArquivo")
    public String enviarArquivo(Model model) {
        model.addAttribute("arquivo", new Arquivo());
        return "enviar_arquivo";
    }

    @PostMapping("/salvarArquivo")
    public String salvarArquivo(@ModelAttribute Arquivo arquivo,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("dificuldade") int dificuldade,
            RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, selecione um arquivo.");
            return "redirect:/editarCurso/enviarArquivo";
        }

        arquivo.setNome(file.getOriginalFilename());
        arquivo.setConteudo(file.getBytes());
        arquivo.setDescricao(descricao);
        arquivo.setDificuldade(dificuldade);
        arquivo.setTitulo(titulo);

        arquivoService.salvarArquivo(arquivo);
        redirectAttributes.addFlashAttribute("message", "Arquivo salvo com sucesso!");
        return "redirect:/editarCurso/listarArquivos"; // Redireciona para a lista de arquivos
    }

    @GetMapping("/baixarArquivo/{id}")
    public ResponseEntity<ByteArrayResource> baixarArquivo(@PathVariable Long id) {
        Arquivo arquivo = arquivoService.encontrarArquivoPorId(id);

        if (arquivo == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(arquivo.getConteudo());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getNome() + "\"")
                .contentLength(arquivo.getConteudo().length)
                .body(resource);
    }

}
