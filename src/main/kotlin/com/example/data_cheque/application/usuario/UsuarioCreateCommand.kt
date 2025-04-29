package com.example.data_cheque.application.usuario

import com.example.data_cheque.domain.empregador.Empregador
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Clock
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.util.*

@Serializable
data class UsuarioCommand(
    val id: @Contextual UUID,
    val email: String,
    val senha: String,
    val tipoUsuario: Role,
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val ativo: Boolean,
    val crc: String?,
    val empregador: Empregador?,
    val contadorId: UUID?,
    val usuarioId: UUID?,
    val cargo: String?,
    val setor:  String?,
    val salario: Double?,
    val dataAdmissao: Timestamp?,
)

fun UsuarioCommand.toUsuario(id: UUID) = Usuario(
    id = id,
    email = email,
    senha = senha,
    tipoUsuario = tipoUsuario,
    atualizadoEm = null,
    usuarioCriacao = "",
    criadoEm = Clock.System.now(),
    usuarioAtualizacao = null
)

