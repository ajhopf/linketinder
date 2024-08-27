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
import com.linketinder.view.MenuInicial

static void main(String[] args) {
    Map services = criarBeans()
    IniciarDB.iniciar(services.candidatoService, services.empresaService)

    MenuInicial.iniciar(services.empresaService, services.candidatoService)
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

