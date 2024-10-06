package com.linketinder.exceptions

class CompetenciaNotFoundException extends RuntimeException{
    CompetenciaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
