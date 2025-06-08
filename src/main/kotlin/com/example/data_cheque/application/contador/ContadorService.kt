package com.example.data_cheque.application.contador

import com.example.data_cheque.application.contador.exception.ContadorNaoEncontradoException
import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoCadastrado
import com.example.data_cheque.application.pessoa.PessoaService
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioService
import com.example.data_cheque.domain.contador.Contador
import com.example.data_cheque.domain.contador.ContadorRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ContadorService (
    private val contadorRepository: ContadorRepository,
    private val usuarioService: UsuarioService,
    private val pessoaService: PessoaService,
    private val encoderPassword: EncoderPassword
){
    fun findAll(): List<Contador> {
        return contadorRepository.findAll()
    }
    
    fun findById(contadorId: UUID): Contador {
        return contadorRepository.findById(contadorId) ?: throw ContadorNaoEncontradoException(contadorId)
    }

    fun findByUserId(usuarioId: UUID): Contador? {
        return contadorRepository.findById(usuarioId)
    }
    
    fun insert(contadorCreateCommand: ContadorCreateCommand): Contador?{

        usuarioService.createValidations(contadorCreateCommand.usuario)
        pessoaService.createValidation(contadorCreateCommand.pessoa)

        try {
            val novoUsuario = usuarioService.insert(contadorCreateCommand.usuario)
            val novaPessoa = pessoaService.insert(contadorCreateCommand.pessoa)

            val novoContador = contadorCreateCommand.toContador(novaPessoa, novoUsuario)
            contadorRepository.insert(novoContador)
            return contadorRepository.findById(novoContador.id)
        }catch (e: FuncionarioNaoCadastrado){
            throw e
        }
    }

    fun update(contadorUpdateCommand: ContadorUpdateCommand, contadorId: UUID): Contador{

        val usuarioAtualizado = contadorUpdateCommand.usuario?.let { usuarioDTO ->
            usuarioService.updateValidation(usuarioDTO)
            usuarioService.update(usuarioDTO, usuarioDTO.id)
        }

        val pessoaAtualizada = contadorUpdateCommand.pessoa?.let { pessoaDTO ->
            pessoaService.updateValidation(pessoaDTO)
            pessoaService.update(pessoaDTO, pessoaDTO.id)
        }

        val contadorEncontrado = contadorRepository.findById(contadorId)
        contadorEncontrado?.let { contadorUpdateCommand.toContadorAtualizado(contadorEncontrado, encoderPassword) }
            ?.let { contadorRepository.update(it) }
        return findById(contadorId)
    }

    fun delete(contadorId: UUID){
        val contadorEncontrado = contadorRepository.findById(contadorId) ?: throw ContadorNaoEncontradoException(contadorId)
        val usuarioId = contadorEncontrado.usuario.id
        val pessoaId = contadorEncontrado.pessoa.id
        contadorRepository.delete(contadorId)
        usuarioService.delete(usuarioId)
        pessoaService.delete(pessoaId)
    }
}