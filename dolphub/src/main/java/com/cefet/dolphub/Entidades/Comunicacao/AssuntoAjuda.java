package com.cefet.dolphub.Entidades.Comunicacao;

public enum AssuntoAjuda {
    DENUNCIA_USUARIO("Denúncia de usuario"), 
    DENUNCIA_CURSO("Denúncia de curso"),
    PROBLEMA_PAGAMENTO("Problema de pagamento"),
    DIFICULDADE_TECNICA("Dificuldades técnicas"),
    FEEDBACK_SUGESTOES("Feedback e sugestões"),
    OUTROS("Outros");

    private String assunto;

    AssuntoAjuda(String assunto){
        this.assunto = assunto;
    }

    public String getAssunto(){
        return assunto;
    }
}
