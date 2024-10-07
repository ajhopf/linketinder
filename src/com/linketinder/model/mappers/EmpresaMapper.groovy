package com.linketinder.model.mappers

import com.linketinder.model.Candidato
import com.linketinder.model.Competencia
import com.linketinder.model.Empresa
import com.linketinder.model.Endereco
import com.linketinder.model.dtos.CandidatoDTO
import com.linketinder.model.dtos.EmpresaDTO

class EmpresaMapper {
    static EmpresaDTO toDTO(Empresa empresa) {
        return new EmpresaDTO(
                id: empresa.id,
                nome: empresa.nome,
                email: empresa.email,
                descricao: empresa.descricao,
                cnpj: empresa.cnpj,
                senha: empresa.senha
        )
    }

    static Empresa toEntity(EmpresaDTO empresaDTO, Endereco endereco) {
        return new Empresa(
                id: empresaDTO.id,
                nome: empresaDTO.nome,
                email: empresaDTO.email,
                descricao: empresaDTO.descricao,
                cnpj: empresaDTO.cnpj,
                endereco: endereco,
                senha: empresaDTO.senha,
        )
    }

}
