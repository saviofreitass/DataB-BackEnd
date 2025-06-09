package com.example.data_cheque.domain.empregador

import java.util.*

interface EmpregadorRepository {
    fun findAllByContador(contadorId: UUID): List<Empregador>

    fun findByIdContador(contadorId: UUID, empregadorId: UUID): Empregador?

    fun inserir(empregador: Empregador): Boolean

    fun atualizar(empregador: Empregador): Boolean

    fun excluir(empregadorId: UUID): Boolean
}