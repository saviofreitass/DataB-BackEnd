package com.example.data_cheque.application.usuario.strategy

interface AccountValidationEstrategy <T>{
    fun execute(command: T)
}