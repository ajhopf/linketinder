package com.linketinder.repository.interfaces

import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.dtos.EmpresaDTO

interface IEmpresaDAO {
    List<EmpresaDTO> listarEmpresas()
    Integer adicionarEmpresa(EmpresaDTO empresa)
    EmpresaDTO obterEmpresaPeloId(Integer id)
    EmpresaDTO updateEmpresa(EmpresaDTO)
    void deleteEmpresaPeloId(Integer id)
}