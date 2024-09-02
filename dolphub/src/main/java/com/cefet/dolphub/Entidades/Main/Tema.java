package com.cefet.dolphub.Entidades.Main;

public enum Tema {
    CLARO("Tema claro"), 
    ESCURO("Tema escuro");

    private String tema;

    Tema(String tema){
        this.tema = tema;
    }

    public String getTema(){
        return tema;
    }
}
