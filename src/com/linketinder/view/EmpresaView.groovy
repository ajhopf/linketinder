package com.linketinder.view

import com.linketinder.model.Empresa
import com.linketinder.service.EmpresaService

class EmpresaView {
    static void listarEmpresas(EmpresaService service) {
        List<Empresa> empresas = service.listarEmpresas()
        println "Empresas: "
        empresas.each {empresa -> println """
            |-----------------------------------------------
            |Nome: $empresa.nome
            |E-mail corporativo: $empresa.email
            |CNPJ: $empresa.cnpj
            |País: $empresa.endereco.pais
            |Estado: $empresa.endereco.estado
            |Descrição: $empresa.descricao
            |Tem interesse nas competências: $empresa.competencias
            """.stripMargin()
        }
    }
}
