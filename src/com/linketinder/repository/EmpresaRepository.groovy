package com.linketinder.repository

import com.linketinder.model.Empresa

class EmpresaRepository {
    List<Empresa> empresas = []
    void adicionarEmpresa(Empresa empresa) { empresas << empresa }
    List<Empresa> listarEmpresas() { empresas }
}
