package com.linketinder.exceptions

class VagaNotFoundException extends RuntimeException{
    VagaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
