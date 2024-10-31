package linketinder.view

import linketinder.model.Candidato
import linketinder.model.Empresa
import linketinder.model.Usuario
import linketinder.service.CandidatoService
import linketinder.service.EmpresaService


class ListagemView {
    static void listarEmpresas(EmpresaService service) {
        List<Empresa> empresas = service.listarEmpresas()
        println "Empresas: "
        empresas.each {empresa -> printInfosDePessoa(empresa)}
    }

    static <T extends Usuario> void printInfosDePessoa(T pessoa) {
        boolean isEmpresa = pessoa.getClass() == Empresa
        boolean isCandidato = pessoa.getClass() == Candidato
        String infosEspecificas = "Não foi possível obter o cpf / cnpj deste cadastro"

        if (isEmpresa) {
            Empresa empresa = pessoa as Empresa
            infosEspecificas = "CNPJ: $empresa.cnpj"
        } else if (isCandidato) {
            Candidato candidato = pessoa as Candidato
            infosEspecificas = "CPF: $candidato.cpf\n|Data de Nascimento: $candidato.dataNascimento\n|Telefone: $candidato.telefone|Tem interesse nas competências: $candidato.competencias"
        }

        println """
            |-----------------------------------------------
            |ID: $pessoa.id
            |Nome: $pessoa.nome
            |E-mail corporativo: $pessoa.email
            |$infosEspecificas
            |País: $pessoa.endereco.pais
            |Estado: $pessoa.endereco.estado
            |Descrição: $pessoa.descricao
            
            """.stripMargin()
    }


}
