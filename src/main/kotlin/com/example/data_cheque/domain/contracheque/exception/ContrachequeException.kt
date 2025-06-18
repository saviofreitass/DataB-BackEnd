package com.example.data_cheque.domain.contracheque.exception

import java.util.UUID

sealed class ContrachequeException(message: String) : Exception(message) {
    abstract val contrachequeId: UUID?
}

data class ContrachequeNaoEncontradoException(
    override val contrachequeId: UUID?,
) : ContrachequeException("Contracheque $contrachequeId não encontrado")

