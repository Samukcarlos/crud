package com.scm.crud.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{ // RuntimeException -> não exige o bloco try cath
    public ResourceNotFoundException(String msg){ // Classe de exção customizada que exige uma mensagem
        super(msg);

    }
}
