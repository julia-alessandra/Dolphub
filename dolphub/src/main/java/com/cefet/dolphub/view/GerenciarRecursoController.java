package com.cefet.dolphub.view;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Professor;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Entidades.Recursos.ArquivosBaixados;
import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Entidades.Recursos.Dificuldade;
import com.cefet.dolphub.Entidades.Recursos.Questao;
import com.cefet.dolphub.Entidades.Recursos.QuestaoAtividade;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Entidades.Recursos.Topico;
import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Service.AcessoService;
import com.cefet.dolphub.Service.ArquivoBaixadoService;
import com.cefet.dolphub.Service.ArquivoService;
import com.cefet.dolphub.Service.AtividadeService;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.ProfessorService;
import com.cefet.dolphub.Service.QuestaoAtividadeService;
import com.cefet.dolphub.Service.QuestaoService;
import com.cefet.dolphub.Service.RecursoService;
import com.cefet.dolphub.Service.TagService;
import com.cefet.dolphub.Service.TopicoService;
import com.cefet.dolphub.Service.VideoService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    @Autowired
    private TopicoService topicoService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private AtividadeService atividadeService;
    @Autowired
    private QuestaoService questaoService;
    @Autowired
    private QuestaoAtividadeService questaoAtividadeService;
    @Autowired
    private ArquivoBaixadoService arquivoBaixadoService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public String editarCurso() {
        return "editar_curso";
    }

    @GetMapping("{idCurso}")
    public String acessarGerenciarCurso(@PathVariable Long idCurso, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Curso curso = cursoService.buscar(idCurso);
        List<Recurso> recursos = acessoService.recuperarRecursosPorCurso(idCurso);

        if (!cursoService.getEditAcess(curso, usuarioLogado)) {
            Optional<Professor> professorOpt = professorService.buscarProfessorPorIdUsuario(usuarioLogado);
            if (professorOpt.isPresent()) {
                List<Curso> cursos = cursoService.listarCursosPorProfessor(professorOpt.get());
                List<Curso> limiteCurso = new ArrayList<>();
                for (int i = 0; i < Math.min(cursos.size(), 10); i++) {
                    limiteCurso.add(cursos.get(i));
                }
                model.addAttribute("cursos", limiteCurso);
                model.addAttribute("usuarioLogado", usuarioLogado);
                model.addAttribute("tipoNotificacao", "error");
                model.addAttribute("notificacao", "Acesso negado à edição do curso");
                return "pagina_inicial";
            }
            model.addAttribute("usuarioLogado", usuarioLogado);
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Acesso negado à edição do curso");
            return "pagina_inicial";
        }

        model.addAttribute("curso", curso);
        model.addAttribute("recursos", recursos);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "editar_curso";
    }

    @GetMapping("listarArquivos")
    public String listarArquivos(Model model) {
        List<Arquivo> arquivos = arquivoService.listarArquivos();
        model.addAttribute("arquivos", arquivos);
        return "listar_arquivos";
    }

    @GetMapping("{idCurso}/enviarArquivo/{idPai}")
    public String enviarArquivo(@PathVariable Long idCurso, @PathVariable Long idPai, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Arquivo novo = new Arquivo();
        Topico pai = recursoService.buscarTopicoPai(idPai);
        Curso curso = cursoService.buscar(idCurso);
        novo.setTopicoPai(pai);
        // System.out.println(pai.getTitulo());
        novo.setCurso(curso);

        model.addAttribute("arquivo", novo);
        model.addAttribute("topicoPai", pai.getId());
        model.addAttribute("curso", curso);
        model.addAttribute("operation", "enviar");
        model.addAttribute("usuarioLogado", usuarioLogado);

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
            RedirectAttributes redirectAttributes, @AuthenticationPrincipal Usuario usuarioLogado) {
        Arquivo arquivo = arquivoService.encontrarArquivoPorId(id);
        ArquivosBaixados arquivoBaixado = new ArquivosBaixados();
        arquivoBaixado.setArquivo(arquivo);
        arquivoBaixado.setUsuario(usuarioLogado);
        arquivoBaixado.setData(new java.sql.Date(System.currentTimeMillis()));
        arquivoBaixadoService.salvar(arquivoBaixado);
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

    @GetMapping("{idCurso}/editarArquivo/{idArquivo}")
    public String editarArquivo(@PathVariable Long idCurso, @PathVariable Long idArquivo, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Arquivo arquivo = arquivoService.buscar(idArquivo);
        Curso curso = cursoService.buscar(idCurso);

        if (arquivo == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Arquivo não encontrado");
            return "redirect:/error";
        }

        model.addAttribute("arquivo", arquivo);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("curso", curso);
        model.addAttribute("operation", "editar");
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "enviar_arquivo";
    }

    @PostMapping("/atualizarArquivo")
    public String atualizarArquivo(@RequestParam Long cursoId,
            @RequestParam Long idArquivo,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam int dificuldade,
            RedirectAttributes redirectAttributes) {

        Arquivo arquivo = arquivoService.buscar(idArquivo);

        if (arquivo == null) {
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
            redirectAttributes.addFlashAttribute("notificacao", "Arquivo não encontrado");
            return "redirect:/editarCurso/" + cursoId;
        }

        arquivo.setTitulo(titulo);
        arquivo.setDescricao(descricao);
        arquivo.setDificuldade(dificuldade);

        arquivoService.salvarArquivo(arquivo);
        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Arquivo atualizado");

        return "redirect:/editarCurso/" + cursoId;
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
    public String enviarVideo(@PathVariable Long idCurso, @PathVariable Long idPai, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Video novo = new Video();
        Topico pai = recursoService.buscarTopicoPai(idPai);
        Curso curso = cursoService.buscar(idCurso);
        novo.setTopicoPai(pai);
        // System.out.println(pai.getTitulo());
        novo.setCurso(curso);

        model.addAttribute("video", novo);
        model.addAttribute("topicoPai", pai.getId());
        model.addAttribute("curso", curso);
        model.addAttribute("operation", "enviar");
        model.addAttribute("usuarioLogado", usuarioLogado);
        System.out.println("Chegou aqui");
        return "enviar_video";
    }

    @PostMapping("/salvarVideo")
    public String salvarVideo(@ModelAttribute Video video,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("dificuldade") int dificuldade,
            @RequestParam("anotacao") String anotacao,
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
        video.setAnotacao(anotacao);

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
    public String acessarVideo(@PathVariable Long idCurso, @PathVariable Long idVideo, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Video video = videoService.buscar(idVideo);
        Curso curso = cursoService.buscar(idCurso);

        if (video == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/acessoCurso/" + idCurso;
        }

        model.addAttribute("video", video);
        model.addAttribute("cursoId", idCurso);
        model.addAttribute("curso", curso);
        model.addAttribute("roleAcess", "edit");
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "acesso_video";
    }

    @GetMapping("{idCurso}/editarVideo/{idVideo}")
    public String editarVideo(@PathVariable Long idCurso, @PathVariable Long idVideo, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Video video = videoService.buscar(idVideo);
        Curso curso = cursoService.buscar(idCurso);

        if (video == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/error";
        }

        model.addAttribute("video", video);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("curso", curso);
        model.addAttribute("operation", "editar");
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "enviar_video";
    }

    @PostMapping("/atualizarVideo")
    public String atualizarVideo(@RequestParam Long cursoId,
            @RequestParam Long idVideo,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam int dificuldade,
            @RequestParam String anotacao,
            RedirectAttributes redirectAttributes) {

        Video video = videoService.buscar(idVideo);

        if (video == null) {
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
            redirectAttributes.addFlashAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/editarCurso/" + cursoId;
        }

        video.setTitulo(titulo);
        video.setDescricao(descricao);
        video.setDificuldade(dificuldade);
        video.setAnotacao(anotacao);

        videoService.salvarVideo(video);
        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Vídeo atualizado");

        return "redirect:/editarCurso/" + cursoId;
    }

    @GetMapping("{idCurso}/apagarVideo/{idVideo}")
    public String apagarVideo(@PathVariable Long idCurso, @PathVariable Long idVideo,
            RedirectAttributes redirectAttributes) {

        Video video = videoService.buscar(idVideo);

        if (video != null) {
            videoService.deletar(idVideo);
            redirectAttributes.addFlashAttribute("message", "Arquivo apagado com sucesso!");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
            redirectAttributes.addFlashAttribute("notificacao", "Vídeo apagado com successo");
        } else {
            redirectAttributes.addFlashAttribute("error", "Arquivo não encontrado.");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "warning");
            redirectAttributes.addFlashAttribute("notificacao", "Nenhum vídeo correspondente encontrado");
        }
        return "redirect:/editarCurso/" + idCurso;
    }

    @GetMapping("{idCurso}/gerarTopico")
    public String gerarTopico(@PathVariable Long idCurso, @RequestParam String titulo,
            RedirectAttributes redirectAttributes) {
        Topico novo = new Topico();
        novo.setCurso(cursoService.buscar(idCurso));
        novo.setTopicoPai(null);
        novo.setTitulo(titulo);
        topicoService.salvarTopico(novo);
        return "redirect:/editarCurso/" + idCurso;
    }

    @GetMapping("{idCurso}/gerarTopico/{idPai}")
    public String gerarTopico(@PathVariable Long idCurso, @RequestParam String titulo, @PathVariable Long idPai,
            RedirectAttributes redirectAttributes) {
        Topico novo = new Topico();
        novo.setCurso(cursoService.buscar(idCurso));
        novo.setTopicoPai(topicoService.buscar(idPai));
        novo.setTitulo(titulo);
        topicoService.salvarTopico(novo);
        return "redirect:/editarCurso/" + idCurso;
    }

    @GetMapping("{idCurso}/enviarAtividade/{idPai}")
    public String enviarAtividade(@PathVariable Long idCurso, @PathVariable Long idPai, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Atividade novo = new Atividade();
        Topico pai = recursoService.buscarTopicoPai(idPai);
        Curso curso = cursoService.buscar(idCurso);
        novo.setTopicoPai(pai);
        novo.setCurso(curso);

        model.addAttribute("atividade", novo);
        model.addAttribute("topicoPai", pai.getId());
        model.addAttribute("curso", curso);
        model.addAttribute("operation", "enviar");
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<Questao> questoes = questaoService.listarTodas();

        model.addAttribute("questoes", questoes);
        model.addAttribute("role", "professor");

        return "enviar_atividade";
    }

    @PostMapping("/salvarAtividade")
    public String salvarAtividade(@ModelAttribute Atividade atividade,
            @RequestParam("nome") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("dificuldade") int dificuldade,
            @RequestParam("topicoPai") Long topicoPaiId,
            @RequestParam("curso") Long cursoId,
            RedirectAttributes redirectAttributes) throws IOException {

        atividade.setDescricao(descricao);
        atividade.setDificuldade(dificuldade);
        atividade.setTitulo(titulo);

        Topico topicoPai = recursoService.buscarTopicoPai(topicoPaiId);
        Curso curso = cursoService.buscar(cursoId);

        if (topicoPai == null || curso == null) {
            redirectAttributes.addFlashAttribute("error", "Tópico pai ou curso não encontrado.");
            return "redirect:/editarCurso/enviarAtividade";
        }

        atividade.setTopicoPai(topicoPai);
        atividade.setCurso(curso);

        atividadeService.salvarAtividade(atividade);
        redirectAttributes.addFlashAttribute("message", "Atividade salva com sucesso!");
        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Atividade salva com successo.");

        return "redirect:/editarCurso/" + cursoId;
    }

    @GetMapping("{idCurso}/editarAtividade/{idAtividade}")
    public String editarAtividade(@PathVariable Long idCurso, @PathVariable Long idAtividade, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Atividade atv = atividadeService.buscar(idAtividade);
        Curso curso = cursoService.buscar(idCurso);

        if (atv == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Atividade não encontrada");
            return "redirect:/error";
        }

        model.addAttribute("atividade", atv);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("curso", curso);
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<Questao> questoes = questaoService.listarTodas();

        List<QuestaoAtividade> listaQuestaoAtv = atv.getQuestaoAtividades();
        List<Questao> questoesAtv = new ArrayList<>();
        for (QuestaoAtividade q : listaQuestaoAtv) {
            System.out.println("Id da questao da atividade" + q.getId());
            questoesAtv.add(q.getQuestao());
        }
        model.addAttribute("questoesAtv", questoesAtv);

        model.addAttribute("questoes", questoes);
        model.addAttribute("role", "professor");
        model.addAttribute("tags", tagService.findAllTags());

        return "editar_atividade";
    }

    @GetMapping("{idCurso}/editarAtividade/{idAtividade}/adicionarQuestao/{idQuestao}")
    public String adicionarQuestao(@PathVariable Long idCurso, @PathVariable Long idAtividade,
            @PathVariable Long idQuestao, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado, RedirectAttributes redirectAttributes) {
        Atividade atv = atividadeService.buscar(idAtividade);
        Curso curso = cursoService.buscar(idCurso);

        if (questaoAtividadeService.buscarPorQuestaoAtividadeEmAtividade(idAtividade, idQuestao) != null) {
            redirectAttributes.addFlashAttribute("message", "Essa questão já foi adicionada");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
            redirectAttributes.addFlashAttribute("notificacao", "Essa questão já foi adicionada");
            return "redirect:/editarCurso/" + idCurso + "/editarAtividade/" + idAtividade;
        }
        if (atv == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Atividade não encontrada");
            return "redirect:/error";
        }

        Questao novaQuestao = questaoService.buscar(idQuestao);
        QuestaoAtividade novaQuestaoAtividade = new QuestaoAtividade();
        novaQuestaoAtividade.setQuestao(novaQuestao);
        novaQuestaoAtividade.setAtividade(atv);

        List<QuestaoAtividade> listaQuestaoAtv = atv.getQuestaoAtividades();
        listaQuestaoAtv.add(novaQuestaoAtividade);
        atv.setQuestaoAtividades(listaQuestaoAtv);

        atividadeService.salvarAtividade(atv);
        redirectAttributes.addFlashAttribute("message", "Questão adicionada com sucesso!");
        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Questão adicionada com successo.");

        List<Questao> questoesAtv = new ArrayList<>();
        for (QuestaoAtividade q : listaQuestaoAtv) {
            questoesAtv.add(q.getQuestao());
        }
        model.addAttribute("questoesAtv", questoesAtv);

        model.addAttribute("atividade", atv);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("curso", curso);
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<Questao> questoes = questaoService.listarTodas();
        model.addAttribute("questoes", questoes);
        model.addAttribute("role", "professor");
        model.addAttribute("tags", tagService.findAllTags());

        return "editar_atividade";
    }

    @GetMapping("{idCurso}/editarAtividade/{idAtividade}/removerQuestao/{idQuestao}")
    public String removerQuestao(@PathVariable Long idCurso, @PathVariable Long idAtividade,
            @PathVariable Long idQuestao, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado, RedirectAttributes redirectAttributes) {
        Atividade atv = atividadeService.buscar(idAtividade);
        Curso curso = cursoService.buscar(idCurso);

        if (atv == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Atividade não encontrada");
            return "redirect:/error";
        }

        List<QuestaoAtividade> listaQuestaoAtv = atv.getQuestaoAtividades();
        QuestaoAtividade questaoDelete = questaoAtividadeService.buscarPorQuestaoAtividadeEmAtividade(idAtividade,
                idQuestao);
        questaoAtividadeService.deletar(questaoDelete.getId());

        redirectAttributes.addFlashAttribute("message", "Questão removida com sucesso!");
        redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
        redirectAttributes.addFlashAttribute("notificacao", "Questão removida com successo.");

        List<Questao> questoesAtv = new ArrayList<>();
        for (QuestaoAtividade q : listaQuestaoAtv) {
            questoesAtv.add(q.getQuestao());
        }
        model.addAttribute("questoesAtv", questoesAtv);

        model.addAttribute("atividade", atv);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("curso", curso);
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<Questao> questoes = questaoService.listarTodas();
        model.addAttribute("questoes", questoes);
        model.addAttribute("role", "professor");
        model.addAttribute("tags", tagService.findAllTags());
        return "editar_atividade";
    }

    @GetMapping("{idCurso}/editarAtividade/{idAtividade}/filtrarQuestao")
    public String filtrarQuestoes(
            @PathVariable Long idCurso,
            @PathVariable Long idAtividade,
            @RequestParam(required = false) String chave,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) List<String> tags,
            Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Date dataInicioDate = (dataInicio != null)
                ? Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant())
                : null;
        Date dataFimDate = (dataFim != null) ? Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant())
                : null;

        List<Questao> questoesFiltradas = questaoService.buscarQuestoesFiltradas(idCurso, dataInicioDate, dataFimDate,
                chave, tags, "todas");

        Atividade atv = atividadeService.buscar(idAtividade);
        Curso curso = cursoService.buscar(idCurso);
        model.addAttribute("atividade", atv);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("curso", curso);
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("filtro", "true");
        List<QuestaoAtividade> listaQuestaoAtv = atv.getQuestaoAtividades();
        List<Questao> questoesAtv = new ArrayList<>();
        for (QuestaoAtividade q : listaQuestaoAtv) {
            System.out.println("Id da questao da atividade" + q.getId());
            questoesAtv.add(q.getQuestao());
        }
        model.addAttribute("questoesAtv", questoesAtv);
        model.addAttribute("questoes", questoesFiltradas);
        model.addAttribute("tags", tagService.findAllTags());
        return "editar_atividade";
    }

    @PostMapping("/atualizarAtividade")
    public String atualizarAtividade(@RequestParam Long cursoId,
            @RequestParam Long idAtividade,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam int dificuldade,
            RedirectAttributes redirectAttributes) {

        Atividade atv = atividadeService.buscar(idAtividade);

        if (atv == null) {
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
            redirectAttributes.addFlashAttribute("notificacao", "Atividade não encontrada");
            return "redirect:/editarCurso/" + cursoId;
        }

        atv.setTitulo(titulo);
        atv.setDescricao(descricao);
        atv.setDificuldade(dificuldade);

        atividadeService.salvarAtividade(atv);
        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Atividade atualizada");

        return "redirect:/editarCurso/" + cursoId;
    }

    @GetMapping("{idCurso}/apagarAtividade/{idAtividade}")
    public String apagarAtividade(@PathVariable Long idCurso, @PathVariable Long idAtividade,
            RedirectAttributes redirectAttributes) {

        Atividade atv = atividadeService.buscar(idAtividade);

        if (atv != null) {
            atividadeService.deletar(idAtividade);
            redirectAttributes.addFlashAttribute("message", "Atividade apagada com sucesso!");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
            redirectAttributes.addFlashAttribute("notificacao", "Atividade apagada com successo");
        } else {
            redirectAttributes.addFlashAttribute("error", "Atividade não encontrada.");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "warning");
            redirectAttributes.addFlashAttribute("notificacao", "Nenhuma Atividade correspondente encontrada");
        }
        return "redirect:/editarCurso/" + idCurso;
    }

}
