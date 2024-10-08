package linketinder.model.mappers

import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.dtos.EmpresaDTO

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
