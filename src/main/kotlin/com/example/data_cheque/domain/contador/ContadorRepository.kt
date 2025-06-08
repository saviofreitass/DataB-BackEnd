package com.example.data_cheque.domain.contador

import java.util.*

interface ContadorRepository {
    fun findAll(): List<Contador>

    fun findById(contadorId: UUID): Contador?

    fun findByUserId(usuarioId: UUID): Contador?

    fun insert(contador: Contador): Boolean

    fun update(contador: Contador): Boolean

    fun delete(contadorId: UUID): Boolean
}
