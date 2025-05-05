package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.pessoa.PessoaRepository
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import kotlinx.datetime.Clock
import org.springframework.stereotype.Service
import java.util.*

@Service
class FuncionarioService (
    private val funcionarioRepository: FuncionarioRepository,
    private val usuarioRepository: UsuarioRepository,
    private val pessoaRepository: PessoaRepository
) {
    fun findAll(): List<FuncionarioCommandResponse> {
        return funcionarioRepository.findAll()
    }
    fun findById(funcionarioId: UUID): Funcionario {
        return funcionarioRepository.findById(funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
    }

    fun insert(funcionario: FuncionarioCommand): Funcionario {

        val novoUsuario = Usuario(
            id = funcionario.usuarioId,
            email = funcionario.email,
            senha = funcionario.senha,
            tipoUsuario = Role.ROLE_FUNCIONARIO,
            criadoEm = funcionario.criadoEm,
            usuarioCriacao = funcionario.usuarioCriacao,
            atualizadoEm = funcionario.atualizadoEm,
            usuarioAtualizacao = funcionario.usuarioAtualizacao
        )
        usuarioRepository.insert(novoUsuario)

        val novaPessoa = Pessoa(
            id = funcionario.pessoaId,
            usuarioId = novoUsuario.id,
            nome = funcionario.nome,
            cpfcnpj = funcionario.cpfcnpj,
            telefone = funcionario.telefone,
            ativo = true
        )
        pessoaRepository.insert(novaPessoa)

        val novoFuncionario = Funcionario(
            id = UUID.randomUUID(),
            usuarioId = novoUsuario.id,
            pessoaId = novaPessoa.id,
//            empregadorId = request.empregadorId,
//            contadorId = request.contadorId,
            cargo = funcionario.cargo,
            setor = funcionario.setor,
            salario = funcionario.salario,
            dataAdmissao = funcionario.dataAdmissao
        )
        funcionarioRepository.insert(novoFuncionario)
        return novoFuncionario
    }

    fun update(funcionario: FuncionarioCommand, funcionarioId: UUID): Funcionario {
        val usuarioUpdate = Usuario(
            id = UUID.randomUUID(),
            email = funcionario.email,
            senha = funcionario.senha,
            tipoUsuario = Role.ROLE_FUNCIONARIO,
            criadoEm = Clock.System.now(),
            usuarioCriacao = "sistema",
            atualizadoEm = null,
            usuarioAtualizacao = null
        )
        usuarioRepository.update(usuarioUpdate)

        val pessoaUpdate = Pessoa(
            id = UUID.randomUUID(),
            usuarioId = usuarioUpdate.id,
            nome = funcionario.nome,
            cpfcnpj = funcionario.cpfcnpj,
            telefone = funcionario.telefone,
            ativo = true
        )
        pessoaRepository.update(pessoaUpdate)

        val novoFuncionario = Funcionario(
            id = UUID.randomUUID(),
            usuarioId = usuarioUpdate.id,
            pessoaId = pessoaUpdate.id,
//            empregadorId = request.empregadorId,
//            contadorId = request.contadorId,
            cargo = funcionario.cargo,
            setor = funcionario.setor,
            salario = funcionario.salario,
            dataAdmissao = funcionario.dataAdmissao
        )
        funcionarioRepository.update(novoFuncionario)
        return findById(funcionarioId = funcionarioId)
    }

    fun delete(funcionarioId: UUID){
        funcionarioRepository.findById(funcionarioId = funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
        funcionarioRepository.delete(funcionarioId)
    }
}