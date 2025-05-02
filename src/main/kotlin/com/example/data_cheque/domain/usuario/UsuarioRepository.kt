package com.example.data_cheque.domain.usuario

import java.util.*

interface UsuarioRepository {
    fun findAll(): List<Usuario>

    fun findById(usuarioId: UUID): Usuario?

    fun findByEmail(email: String): Usuario?

    fun insert(usuario: Usuario): Boolean

    fun update(usuario: Usuario): Boolean

    fun delete(usuarioId: UUID): Boolean
}