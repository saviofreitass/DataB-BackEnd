package com.example.data_cheque.domain.contracheque

import java.util.UUID

interface ContraChequeRepository {
    fun findAllByFuncionario(funcionarioId: UUID): List<Contracheque>

    fun findById(contrachequeId: UUID, funcionarioId: UUID): Contracheque?

    fun inserir(contracheque: Contracheque): Boolean

    fun atualizar(contracheque: Contracheque): Boolean

    fun excluir(contrachequeId: UUID): Boolean
}