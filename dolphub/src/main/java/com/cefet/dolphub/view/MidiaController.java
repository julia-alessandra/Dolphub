package com.cefet.dolphub.view;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Service.ArquivoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class MidiaController {

    @Autowired
    private ArquivoService arquivoService;

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
