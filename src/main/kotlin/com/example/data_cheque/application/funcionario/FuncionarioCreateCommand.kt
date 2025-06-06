package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.pessoa.PessoaCreateCommand
import com.example.data_cheque.application.pessoa.toPessoa
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.application.usuario.toUsuario
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.util.*

@Serializable
data class FuncionarioCreateCommand(
    val usuario: UsuarioCreateCommand,
    val pessoa: PessoaCreateCommand,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Timestamp,
)

fun FuncionarioCreateCommand.toFuncionario(pessoa: Pessoa, usuario: Usuario) = Funcionario(
    id = UUID.randomUUID(),
    usuario = usuario,
    pessoa = pessoa,
    cargo = cargo,
    setor = setor,
    salario = salario,
    dataAdmissao = dataAdmissao
)


