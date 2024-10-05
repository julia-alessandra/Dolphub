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
public class VideoController {

    @Autowired
    private VideoService videoService;

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

}