package com.linketinder.exceptions

class OpcaoInvalidaException extends Exception{
    String mensagem;

    OpcaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
