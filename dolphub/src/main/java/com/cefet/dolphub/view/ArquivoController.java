package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/arquivos")
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    @GetMapping("/upar")
    public String exibirFormularioDeUpload() {
        return "<form method='POST' action='/arquivos/upar' enctype='multipart/form-data'>" +
                "    <input type='file' name='file' />" +
                "    <button type='submit'>Enviar</button>" +
                "</form>";
    }

    @PostMapping
    public ResponseEntity<Arquivo> criarArquivo(@RequestBody Arquivo arquivo) {
        Arquivo savedArquivo = arquivoService.salvarArquivo(arquivo);
        return ResponseEntity.ok(savedArquivo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arquivo> buscarArquivo(@PathVariable Long id) {
        Optional<Arquivo> arquivo = arquivoService.buscarArquivoPorId(id);
        return arquivo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArquivo(@PathVariable Long id) {
        arquivoService.deletarArquivo(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping
    // public ResponseEntity<List<Arquivo>> listarArquivos() {
    // List<Arquivo> arquivos = arquivoService.;
    // return ResponseEntity.ok(arquivos);
    // }
}
