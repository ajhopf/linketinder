package com.linketinder

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.repository.CandidatoRepository
import com.linketinder.repository.EmpresaRepository
import com.linketinder.service.CandidatoService
import com.linketinder.service.EmpresaService
import com.linketinder.util.IniciarDB

static void main(String[] args) {
    Map services = criarBeans()
    CandidatoService candidatoService = services.candidatoService
    EmpresaService empresaService = services.empresaService

    IniciarDB.iniciar(candidatoService, empresaService)

    List<Candidato> candidatos = candidatoService.listarCandidatos()
    List<Empresa> empresas = empresaService.listarEmpresas()
    println "Candidatos: "
    candidatos.each {println it}
    println "--------------------------"
    println "Empresas: "
    empresas.each { println it}
}

static Map criarBeans() {
    CandidatoRepository candidatoRepository = new CandidatoRepository()
    CandidatoService candidatoService = new CandidatoService(candidatoRepository)
    EmpresaRepository empresaRepository = new EmpresaRepository()
    EmpresaService empresaService = new EmpresaService(empresaRepository)

    [
            candidatoService: candidatoService,
            empresaService: empresaService
    ]

}

