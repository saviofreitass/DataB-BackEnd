package com.example.data_cheque.application.funcionario

import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.util.*


@Serializable
data class FuncionarioCreateCommand(
//    val contadorId: UUID,
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Timestamp,
    val email: String,
    val senha: String,
    val criadoEm: Instant = Clock.System.now(),
    val usuarioCriacao: String,
    val atualizadoEm: Instant = Clock.System.now(),
    val usuarioAtualizacao: String
)

data class FuncionarioUpdateCommand(
    var id: UUID? = null,
    var pessoaId: UUID? = null,
    var usuarioId: UUID? = null,
//    val contadorId: UUID,
    val nome: String? = null,
    val cpfcnpj: String? = null,
    val telefone: String? = null,
    var cargo: String? = null,
    var setor: String? = null,
    var salario: Double? = null,
    var dataAdmissao: Timestamp? = null,
    val email: String? = null,
    val senha: String? = null,
    val usuarioAtualizacao: String? = null,
    val ativo: Boolean? = true
) {
    fun toFuncionario(funcionarioAtualizado: FuncionarioCommandResponse) = Funcionario (
            id = funcionarioAtualizado.id,
            pessoaId = funcionarioAtualizado.pessoaId,
            usuarioId = funcionarioAtualizado.usuarioId,
//          contadorId = contadorId,
            cargo = funcionarioAtualizado.cargo,
            setor = funcionarioAtualizado.setor,
            salario = funcionarioAtualizado.salario,
            dataAdmissao = funcionarioAtualizado.dataAdmissao
    )

    fun toUsuario(usuarioAtualizado: Usuario) = Usuario(
        id = usuarioAtualizado.id,
        email = usuarioAtualizado.email,
        senha = usuarioAtualizado.senha,
        tipoUsuario = usuarioAtualizado.tipoUsuario,
        criadoEm = usuarioAtualizado.criadoEm,
        usuarioCriacao = usuarioAtualizado.usuarioCriacao,
        atualizadoEm = usuarioAtualizado.atualizadoEm,
        usuarioAtualizacao = usuarioAtualizado.usuarioAtualizacao,
    )

    fun toPessoa(pessoaAtualizada: Pessoa) = Pessoa(
        id = pessoaAtualizada.id,
        usuarioId = pessoaAtualizada.usuarioId,
        nome = pessoaAtualizada.nome,
        cpfcnpj = pessoaAtualizada.cpfcnpj,
        telefone = pessoaAtualizada.telefone,
        ativo = pessoaAtualizada.ativo,
    )
}

@Serializable
data class FuncionarioCommandResponse(
    val id: UUID,
//    val contadorId: UUID,
    val pessoaId: UUID,
    val usuarioId: UUID,
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Timestamp,
    val email: String,
    val criadoEm: Instant,
    val usuarioCriacao: String,
    val atualizadoEm: Instant,
    val usuarioAtualizacao: String,
    val ativo: Boolean
)

fun FuncionarioCreateCommand.toFuncionario(pessoaId: UUID, usuarioId: UUID) = Funcionario(
    id = UUID.randomUUID(),
    pessoaId = pessoaId,
    usuarioId = usuarioId,
//    contadorId = contadorId,
    cargo = cargo,
    setor = setor,
    salario = salario,
    dataAdmissao = dataAdmissao,
)
