package linketinder.model.mappers

import linketinder.model.Empresa
import linketinder.model.Endereco
import linketinder.model.Vaga
import linketinder.model.dtos.VagaControllerResponseDTO
import linketinder.model.dtos.VagaRequestDTO
import linketinder.model.dtos.VagaResponseDTO

class VagaMapper {
    static VagaRequestDTO toRequestDTO(Vaga vaga, Integer enderecoId) {
        return new VagaRequestDTO(
                id: vaga.id,
                nome: vaga.nome,
                descricao: vaga.descricao,
                empresaId: vaga.empresa.id,
                enderecoId: enderecoId
        )
    }

    static VagaControllerResponseDTO toControllerResponseDTO(Vaga vaga) {
        return new VagaControllerResponseDTO(
                id: vaga.id,
                nome: vaga.nome,
                descricao: vaga.descricao,
                empresa: vaga.empresa.nome,
                empresaId: vaga.empresa.id,
                endereco: vaga.endereco
        )
    }

    static List<VagaControllerResponseDTO> toListControllerResponseDto(List<Vaga> vagas) {
        List<VagaControllerResponseDTO> vagaControllerResponseDTOList = []
        vagas.each {Vaga vaga ->
            VagaControllerResponseDTO vagaDto = toControllerResponseDTO(vaga)
            vagaControllerResponseDTOList << vagaDto
        }

        return vagaControllerResponseDTOList
    }

    static Vaga toEntity(VagaRequestDTO vagaDTO, Endereco endereco) {
        return new Vaga(
                id: vagaDTO.id,
                nome: vagaDTO.nome,
                descricao: vagaDTO.descricao,
                endereco: endereco,
        )
    }

    static Vaga toEntity(VagaResponseDTO vagaResponseDTO) {
        Endereco enderecoSimples = new Endereco()
        enderecoSimples.pais = vagaResponseDTO.pais
        enderecoSimples.cep = vagaResponseDTO.cep
        enderecoSimples.estado = vagaResponseDTO.estado
        enderecoSimples.cidade = vagaResponseDTO.cidade

        return new Vaga(
                id: vagaResponseDTO.id,
                nome: vagaResponseDTO.nome,
                descricao: vagaResponseDTO.descricao,
                endereco: enderecoSimples,
                empresa: new Empresa(
                        id: vagaResponseDTO.empresaId,
                        nome: vagaResponseDTO.empresa
                )
        )
    }
}
