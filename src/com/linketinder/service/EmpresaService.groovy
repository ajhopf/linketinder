package com.linketinder.service

import com.linketinder.exceptions.RepositoryAccessException
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.EmpresaDTO
import com.linketinder.model.mappers.EmpresaMapper
import com.linketinder.repository.EmpresaRepository


import java.sql.SQLException

class EmpresaService {
    EmpresaRepository repository
    EnderecoService enderecoService

    EmpresaService (EmpresaRepository repository, EnderecoService enderecoService) {
        this.repository = repository
        this.enderecoService = enderecoService
    }

//    Empresa adicionarEmpresa(Empresa empresa) {
//        Integer id = MyUtil.gerarNovoId(listarEmpresas())
//        empresa.id = id
//
//        repository.adicionarEmpresa(empresa)
//
//        empresa
//    }

    List<Empresa> listarEmpresas() throws RepositoryAccessException{
        List<Empresa> empresas = []

        try {
            List<EmpresaDTO> empresaDTOList = repository.listarEmpresas()

            for (empresaDTO in empresaDTOList) {
                Endereco endereco = enderecoService.obterEnderecoDoUsuario(empresaDTO.id)
                empresas << EmpresaMapper.toEntity(empresaDTO, endereco)
            }
            return empresas

        } catch (SQLException sqlException) {
            throw new RepositoryAccessException(sqlException.getMessage(), sqlException.getCause())
        }

    }

}
