package com.example.data_cheque.application.usuario

import com.example.data_cheque.application.funcionario.FuncionarioCommand
import com.example.data_cheque.application.funcionario.FuncionarioService
import com.example.data_cheque.application.funcionario.toFuncionario
import com.example.data_cheque.application.usuario.exception.UsuarioNaoEncontradoException
import com.example.data_cheque.domain.contador.Contador
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import kotlinx.datetime.Clock
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository
) {
    fun findAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }
    fun findById(usuarioId: UUID): Usuario {
        return usuarioRepository.findById(usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId)
    }

    fun insert(usuarioCommand: UsuarioCommand): Usuario {
        val usuarioDomain = usuarioCommand.toUsuario()

        usuarioRepository.insert(usuarioDomain)

        // Se for funcionário e informações obrigatórias forem passadas, criar o funcionário
        if (usuarioCommand.tipoUsuario == Role.ROLE_FUNCIONARIO && usuarioCommand.empregador != null) {
            val now = Clock.System.now()

            val funcionario = Funcionario(
                id = UUID.randomUUID(),
                empregador = usuarioCommand.empregador,
                contadorId = usuarioCommand.contadorId ?: throw IllegalArgumentException("ContadorId é obrigatório para funcionário"),
                usuarioId = usuarioDomain.id,
                cargo = usuarioCommand.cargo ?: throw IllegalArgumentException("Cargo é obrigatório para funcionário"),
                setor = usuarioCommand.setor ?: throw IllegalArgumentException("Setor é obrigatório para funcionário"),
                salario = usuarioCommand.salario?.toDouble() ?: throw IllegalArgumentException("Salário é obrigatório para funcionário"),
                dataAdmissao = usuarioCommand.dataAdmissao ?: throw IllegalArgumentException("Data de admissão é obrigatória para funcionário"),
                criadoEm = now,
                usuarioCriacao = usuarioDomain.nome, // Ou id, depende de como você quer rastrear
                atualizadoEm = now,
                usuarioAtualizacao = usuarioDomain.nome
            )

            val funcionarioCommand: FuncionarioCommand
            funcionarioCommand.toFuncionario(funcionario.id)
        }

        return findById(usuarioDomain.id)
    }


    return findById(savedUsuario.id)
    }


    fun update(usuario: UsuarioCommand, usuarioId: UUID): Usuario {
        usuarioRepository.findById(usuarioId = usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId)
        usuarioRepository.update(usuario.toUsuario(usuarioId))
        return findById(usuarioId = usuarioId)
    }

    fun delete(usuarioId: UUID){
        usuarioRepository.findById(usuarioId = usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId)
        usuarioRepository.delete(usuarioId)
    }
}