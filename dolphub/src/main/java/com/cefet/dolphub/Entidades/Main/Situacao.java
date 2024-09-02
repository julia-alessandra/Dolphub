package com.cefet.dolphub.Entidades.Main;

public enum Situacao {
    PUBLICO("Denúncia de usuario"), 
    PROTEGIDO("Denúncia de curso"),
    BLOQUEADO("Problema de pagamento"),
    ARQUIVADO("Dificuldades técnicas");

    private String situacao;

    Situacao(String situacao){
        this.situacao = situacao;
    }

    public String getSituacao(){
        return situacao;
    }
}
