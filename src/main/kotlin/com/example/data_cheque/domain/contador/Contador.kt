package com.example.data_cheque.domain.contador

import java.util.*

data class Contador(
    val id: UUID = UUID.randomUUID(),
    val crc: String?
)
