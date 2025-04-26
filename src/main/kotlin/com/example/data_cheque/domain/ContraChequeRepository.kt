package com.example.data_cheque.domain

import java.util.UUID

interface ContraChequeRepository {
    fun findAll(): List<Contracheque>

    fun findById(contrachequeId: UUID): Contracheque?

    fun inserir(contracheque: Contracheque): Boolean

    fun atualizar(contracheque: Contracheque): Boolean

    fun excluir(contrachequeId: UUID): Boolean
}