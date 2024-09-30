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

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Entidades.Recursos.Topico;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Service.AcessoService;
import com.cefet.dolphub.Service.ArquivoService;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.RecursoService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/editarCurso")
public class GerenciarMidiaController {

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public String editarCurso() {
        return "editar_curso";
    }

    @GetMapping("/{id}")
    public String acessarGerenciarCurso(@PathVariable Long id, Model model) {
        System.out.println("hfsuhfusdhfausahfas11111");
        Curso curso = cursoService.buscar(id);
        System.out.println("hfsuhfusdhfausahfa2222222");
        List<Recurso> recursos = acessoService.recuperarRecursosPorCurso(id);
        System.out.println("hfsuhfusdhfausahfas3333333");

        model.addAttribute("curso", curso);
        System.out.println("hfsuhfusdhfausahfas44444444");
        model.addAttribute("recursos", recursos);
        System.out.println("hfsuhfusdhfausahfas555555");

        return "editar_curso";
    }

    @GetMapping("listarArquivos")
    public String listarArquivos(Model model) {
        List<Arquivo> arquivos = arquivoService.listarArquivos();
        model.addAttribute("arquivos", arquivos);
        return "listar_arquivos";
    }

    @GetMapping("{idCurso}/enviarArquivo/{idPai}")
    public String enviarArquivo(@PathVariable Long idCurso, @PathVariable Long idPai, Model model) {
        Arquivo novo = new Arquivo();
        Topico pai = recursoService.buscarTopicoPai(idPai);
        novo.setTopicoPai(pai);
        System.out.println(pai.getTitulo());
        novo.setCurso(cursoService.buscar(idPai));

        model.addAttribute("arquivo", novo);
        model.addAttribute("topicoPai", pai.getId());
        model.addAttribute("curso", pai.getCurso().getId());
        System.out.println("Chegou aqui");
        return "enviar_arquivo";
    }

    @PostMapping("/salvarArquivo")
    public String salvarArquivo(@ModelAttribute Arquivo arquivo,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("dificuldade") int dificuldade,
            @RequestParam("topicoPai") Long topicoPaiId,
            @RequestParam("curso") Long cursoId,
            RedirectAttributes redirectAttributes) throws IOException {

        System.out.println("Chegou aquu fawfjasifjasdoifasdf");
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, selecione um arquivo.");
            return "redirect:/editarCurso/enviarArquivo";
        }

        arquivo.setNome(file.getOriginalFilename());
        arquivo.setConteudo(file.getBytes());
        arquivo.setDescricao(descricao);
        arquivo.setDificuldade(dificuldade);
        arquivo.setTitulo(titulo);
        // arquivo.setData(LocalDateTime.now());

        Topico topicoPai = recursoService.buscarTopicoPai(topicoPaiId);
        Curso curso = cursoService.buscar(cursoId);

        if (topicoPai == null || curso == null) {
            System.out.println("Topico ou curso não encontrado");
        }

        arquivo.setTopicoPai(topicoPai);
        arquivo.setCurso(curso);

        arquivoService.salvarArquivo(arquivo);
        redirectAttributes.addFlashAttribute("message", "Arquivo salvo com sucesso!");
        return "redirect:/editarCurso/" + cursoId;
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

    @GetMapping("{idCurso}/apagarArquivo/{idArquivo}")
    public String apagarArquivo(@PathVariable Long idCurso, @PathVariable Long idArquivo,
            RedirectAttributes redirectAttributes) {

        Arquivo arquivo = arquivoService.buscar(idArquivo);

        if (arquivo != null) {
            arquivoService.deletar(idArquivo);
            redirectAttributes.addFlashAttribute("message", "Arquivo apagado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Arquivo não encontrado.");
        }
        return "redirect:/editarCurso/" + idCurso;
    }

}
