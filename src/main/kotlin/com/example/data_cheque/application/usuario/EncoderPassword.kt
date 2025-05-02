package com.example.data_cheque.application.usuario

interface EncoderPassword {
    fun encode(senha: String): String
    fun matches(rawPassword: String, encoderPassword: String): Boolean
}