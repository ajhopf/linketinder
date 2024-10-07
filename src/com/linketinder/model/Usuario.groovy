package com.linketinder.model

import groovy.transform.ToString

@ToString(includePackage = false)
abstract class Usuario extends Identificavel {
    String nome
    String email
    String descricao
    String senha
    Endereco endereco
}
