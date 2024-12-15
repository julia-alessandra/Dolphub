package com.cefet.dolphub.view;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.AulaAssistida;
import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private AulaAssistidaRepository aulaAssistidaRepository;

    @GetMapping("/videos/{id}")
    public ResponseEntity<byte[]> acessarVideo(@PathVariable Long id) {
        Video video = videoService.buscar(id);

        if (video == null || video.getConteudo() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("video/mp4"));
        headers.setContentLength(video.getConteudo().length);

        return new ResponseEntity<>(video.getConteudo(), headers, HttpStatus.OK);
    }

    @PostMapping("/acessoCurso/marcar-video/{idVideo}/")
    @ResponseBody
    public ResponseEntity<?> adicionarVideo(@PathVariable("idVideo") Long idVideo,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            Video video = videoService.buscar(idVideo);

            if (video == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vídeo não encontrado.");
            }

            AulaAssistida aulaAssistida = new AulaAssistida();
            aulaAssistida.setUsuario(usuarioLogado);
            aulaAssistida.setVideo(video);
            aulaAssistida.setDataAssistida(Date.valueOf(LocalDate.now())); 
            aulaAssistidaRepository.save(aulaAssistida);

            return ResponseEntity.ok("Vídeo marcado como assistido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao marcar o vídeo como assistido.");
        }
    }

}