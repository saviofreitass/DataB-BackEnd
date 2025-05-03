package com.example.data_cheque.adapters.http.security.response

import kotlinx.serialization.Serializable

@Serializable
data class Token (
    val acessToken: String
)