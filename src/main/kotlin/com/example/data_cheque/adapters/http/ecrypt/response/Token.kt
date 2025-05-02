package com.example.data_cheque.adapters.http.ecrypt.response

import kotlinx.serialization.Serializable

@Serializable
data class Token (
    val acessToken: String
)