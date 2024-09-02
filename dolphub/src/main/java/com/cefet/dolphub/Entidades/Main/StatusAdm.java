package com.cefet.dolphub.Entidades.Main;

public enum StatusAdm {
    ATIVO("Ativo"), 
    DESATIVADO("Desativado"),
    BLOQUEADO("Bloqueado");

    private String status;

    StatusAdm(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
