package com.example.data_cheque.application.contador

import com.example.data_cheque.application.pessoa.PessoaCreateCommand
import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.domain.contador.Contador
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ContadorCreateCommand(
    val usuario: UsuarioCreateCommand,
    val pessoa: PessoaCreateCommand,
    val crc: String
)

fun ContadorCreateCommand.toContador(pessoa: Pessoa, usuario: Usuario) = Contador(
    id = UUID.randomUUID(),
    usuario = usuario,
    pessoa = pessoa,
    crc = crc
)