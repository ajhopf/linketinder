package com.linketinder.model

import groovy.transform.ToString

@ToString(includePackage = false)
abstract class Usuario extends Identificavel {
    String nome, email, descricao
    Endereco endereco
    List<Competencia> competencias
}
