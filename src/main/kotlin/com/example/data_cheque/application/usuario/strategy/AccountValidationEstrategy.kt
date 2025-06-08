package com.example.data_cheque.application.usuario.strategy

import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.domain.usuario.Usuario

interface AccountValidationEstrategy <T>{
    fun execute(command: T)
}