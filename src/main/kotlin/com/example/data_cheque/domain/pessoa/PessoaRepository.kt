package com.example.data_cheque.domain.pessoa

import java.util.UUID

interface PessoaRepository {

    fun findAll(): List<Pessoa>

    fun findById(id: UUID): Pessoa?

    fun insert(pessoa: Pessoa): Boolean

    fun update(pessoa: Pessoa): Boolean

    fun delete(id: UUID): Boolean
}