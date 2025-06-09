package com.example.data_cheque.adapters.http.security

import com.example.data_cheque.adapters.http.error.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class JwtPayload(
    val email: String,
    val tipo: String,
    val nome: String,
    @Serializable(UUIDSerializer::class) val id: UUID? = null,
    @Serializable(UUIDSerializer::class) val contador_id: UUID? = null
)