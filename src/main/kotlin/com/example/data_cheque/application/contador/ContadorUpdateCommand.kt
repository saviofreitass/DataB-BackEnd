package com.example.data_cheque.application.contador

import com.example.data_cheque.application.pessoa.PessoaUpdateCommand
import com.example.data_cheque.application.pessoa.toPessoaAtualizada
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioUpdateCommand
import com.example.data_cheque.application.usuario.toUsuarioAtualizado
import com.example.data_cheque.domain.contador.Contador
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Usuario
import java.util.UUID

data class ContadorUpdateCommand(
    val id: UUID,
    val crc: String?,
    val pessoa: PessoaUpdateCommand?,
    val usuario: UsuarioUpdateCommand?
)

fun ContadorUpdateCommand.toContadorAtualizado(
    contador: Contador,
    encoderPassword: EncoderPassword
): Contador {
    return contador.copy(
        crc = this.crc ?: contador.crc,
        pessoa = this.pessoa?.toPessoaAtualizada(contador.pessoa) ?: contador.pessoa,
        usuario = this.usuario?.toUsuarioAtualizado(contador.usuario, encoderPassword) ?: contador.usuario
    )
}
