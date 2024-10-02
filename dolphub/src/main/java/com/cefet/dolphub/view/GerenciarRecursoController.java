package com.cefet.dolphub.view;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
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
import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Service.AcessoService;
import com.cefet.dolphub.Service.ArquivoService;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.RecursoService;
import com.cefet.dolphub.Service.VideoService;

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
public class GerenciarRecursoController {

    @Autowired
    private ArquivoService arquivoService;
    @Autowired
    private VideoService videoService;
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
        model.addAttribute("recursos", recursos);

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
        model.addAttribute("curso", pai.getCurso());

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

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, selecione um arquivo.");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
            redirectAttributes.addFlashAttribute("notificacao", "Nenhum arquivo identificado.");
            return "redirect:/editarCurso/" + cursoId;
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
        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Arquivo salvo com successo.");
        return "redirect:/editarCurso/" + cursoId;
    }

    @GetMapping("/{cursoId}/baixarArquivo/{id}")
    public ResponseEntity<ByteArrayResource> baixarArquivo(@PathVariable Long cursoId,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        Arquivo arquivo = arquivoService.encontrarArquivoPorId(id);

        if (arquivo == null) {
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
            redirectAttributes.addFlashAttribute("notificacao", "Arquivo não encontrado.");

            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/editarCurso/" + cursoId)
                    .build();
        }

        if (arquivo.getConteudo() == null || arquivo.getConteudo().length == 0) {
            redirectAttributes.addFlashAttribute("tipoNotificacao", "warning");
            redirectAttributes.addFlashAttribute("notificacao", "Arquivo sem conteúdo. Tente baixar outro");

            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/dolphub/editarCurso/" + cursoId)
                    .build();
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
            redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
            redirectAttributes.addFlashAttribute("notificacao", "Arquivo apagado com successo.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Arquivo não encontrado.");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "warning");
            redirectAttributes.addFlashAttribute("notificacao", "Nenhum arquivo correspondente encontrado.");
        }
        return "redirect:/editarCurso/" + idCurso;
    }

    @GetMapping("{idCurso}/enviarVideo/{idPai}")
    public String enviarVideo(@PathVariable Long idCurso, @PathVariable Long idPai, Model model) {
        Video novo = new Video();
        Topico pai = recursoService.buscarTopicoPai(idPai);
        novo.setTopicoPai(pai);
        System.out.println(pai.getTitulo());
        novo.setCurso(cursoService.buscar(idPai));

        model.addAttribute("video", novo);
        model.addAttribute("topicoPai", pai.getId());
        model.addAttribute("curso", pai.getCurso());
        System.out.println("Chegou aqui");
        return "enviar_video";
    }

    @PostMapping("/salvarVideo")
    public String salvarVideo(@ModelAttribute Video video,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("dificuldade") int dificuldade,
            @RequestParam("topicoPai") Long topicoPaiId,
            @RequestParam("curso") Long cursoId,
            RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, selecione um vídeo.");
            return "redirect:/editarCurso/enviarVideo";
        }

        String contentType = file.getContentType();
        if (!contentType.startsWith("video/")) {
            redirectAttributes.addFlashAttribute("error", "Por favor, selecione um arquivo de vídeo válido.");
            return "redirect:/editarCurso/enviarVideo";
        }

        video.setNome(file.getOriginalFilename());
        video.setConteudo(file.getBytes());
        video.setDescricao(descricao);
        video.setDificuldade(dificuldade);
        video.setTitulo(titulo);

        Topico topicoPai = recursoService.buscarTopicoPai(topicoPaiId);
        Curso curso = cursoService.buscar(cursoId);

        if (topicoPai == null || curso == null) {
            redirectAttributes.addFlashAttribute("error", "Tópico pai ou curso não encontrado.");
            return "redirect:/editarCurso/enviarVideo";
        }

        video.setTopicoPai(topicoPai);
        video.setCurso(curso);

        videoService.salvarVideo(video);
        redirectAttributes.addFlashAttribute("message", "Vídeo salvo com sucesso!");
        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Vídeo salvo com successo.");

        return "redirect:/editarCurso/" + cursoId;
    }

    @GetMapping("{idCurso}/acessoVideo/{idVideo}")
    public String acessarVideo(@PathVariable Long idCurso, @PathVariable Long idVideo, Model model) {

        Video video = videoService.buscar(idVideo);

        if (video == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/acessoCurso/" + idCurso;
        }

        model.addAttribute("video", video);
        model.addAttribute("cursoId", idCurso);
        model.addAttribute("roleAcess", "edit");

        return "acesso_video";
    }

    @GetMapping("{idCurso}/editarVideo/{idVideo}")
    public String editarVideo(@PathVariable Long idCurso, @PathVariable Long idVideo, Model model) {
        Video video = videoService.buscar(idVideo);

        if (video == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/editarCurso/" + idCurso;
        }

        model.addAttribute("video", video);
        model.addAttribute("cursoId", idCurso);

        return "editar_video";
    }

}
