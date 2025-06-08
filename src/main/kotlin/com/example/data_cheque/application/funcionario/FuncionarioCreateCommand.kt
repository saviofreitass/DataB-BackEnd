package com.example.data_cheque.application.funcionario

import InstantSerializer
import com.example.data_cheque.application.pessoa.PessoaCreateCommand
import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class FuncionarioCreateCommand(
    val contador: String,
    val usuario: UsuarioCreateCommand,
    val pessoa: PessoaCreateCommand,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Instant,
)

fun FuncionarioCreateCommand.toFuncionario(pessoa: Pessoa, usuario: Usuario) = Funcionario(
    id = UUID.randomUUID(),
    contador = UUID.fromString(contador),
    usuario = usuario,
    pessoa = pessoa,
    cargo = cargo,
    setor = setor,
    salario = salario,
    dataAdmissao = dataAdmissao
)


