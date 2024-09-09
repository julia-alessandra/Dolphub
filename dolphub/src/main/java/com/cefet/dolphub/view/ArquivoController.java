package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Service.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    @GetMapping("/editarCurso")
    public String exibirFormularioDeUpload() {
        return "editar_curso";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile original, RedirectAttributes redirect) {
        try {

            Arquivo file = new Arquivo();
            file.setConteudo(original.getBytes());
            file.setNome(original.getOriginalFilename());
            file.setData(new Date());

            arquivoService.salvarArquivo(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("jsfadiojasopfnsa0idfnoiasnfoiasdfnasoidj");
        return "redirect:/";
    }

    // @PostMapping
    // public ResponseEntity<Arquivo> criarArquivo(@RequestBody Arquivo arquivo) {
    // Arquivo savedArquivo = arquivoService.salvarArquivo(arquivo);
    // return ResponseEntity.ok(savedArquivo);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Arquivo> buscarArquivo(@PathVariable Long id) {
    // Optional<Arquivo> arquivo = arquivoService.buscarArquivoPorId(id);
    // return arquivo.map(ResponseEntity::ok).orElseGet(() ->
    // ResponseEntity.notFound().build());
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletarArquivo(@PathVariable Long id) {
    // arquivoService.deletarArquivo(id);
    // return ResponseEntity.noContent().build();
    // }

    // @GetMapping
    // public ResponseEntity<List<Arquivo>> listarArquivos() {
    // List<Arquivo> arquivos = arquivoService.;
    // return ResponseEntity.ok(arquivos);
    // }
}
