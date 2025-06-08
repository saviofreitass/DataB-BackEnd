package com.example.data_cheque.adapters.jdbc.funcionario

import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlDeleteFuncionarioById
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlInsertFuncionario
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlUpdateFuncionario
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlSelectAll
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlSelectById
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlSelectByUserId
import com.example.data_cheque.application.contador.ContadorService
import com.example.data_cheque.domain.contador.Contador
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import kotlinx.serialization.Contextual
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import java.sql.Timestamp
import java.util.*


@Repository
class FuncionarioJDBCRepository(
    private val db: NamedParameterJdbcOperations,
    private val contadorService: ContadorService
): FuncionarioRepository {
    private companion object{
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(): List<Funcionario> {
        val funcionario = try {
            db.query(sqlSelectAll(), rowMapper())
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar os funcionarios: ${ex.message}" }
            throw ex
        }
        return funcionario
    }

    override fun findById(funcionarioId: UUID): Funcionario? {
        val funcionario = try {
            val params = MapSqlParameterSource("id", funcionarioId)
            db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar o funcionario: ${ex.message}" }
            throw ex
        }
        return funcionario
    }

    override fun findByIdUser(usuarioId: UUID): Funcionario? {
        val funcionario = try {
            val params = MapSqlParameterSource("usuario_id", usuarioId)
            db.query(sqlSelectByUserId(), params, rowMapper()).firstOrNull()
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar o funcionário" }
            throw ex;
        }
        return funcionario
    }


    override fun insert(funcionario: Funcionario): Boolean {
        try {
            val params = parametros(funcionario)
            val linhasAfetadas = db.update(sqlInsertFuncionario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao inserir a funcionario: ${ex.message}" }
            throw ex
        }
    }

    override fun update(funcionario: Funcionario): Boolean {
        try {
            val params = parametros(funcionario)
            val linhasAfetadas = db.update(sqlUpdateFuncionario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao atualizar o funcionário: ${ex.message}" }
            throw ex
        }
    }

    override fun delete(funcionarioId: UUID): Boolean {
        try {
            val params = MapSqlParameterSource("id", funcionarioId)
            val linhasExcluidas = db.update(sqlDeleteFuncionarioById(), params)
            return linhasExcluidas == 1
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao excluir o funcionário: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() = RowMapper<Funcionario> { rs, _ ->
        val funcionarioId = UUID.fromString(rs.getString("id"))

        val usuario = Usuario(
            id = UUID.fromString(rs.getString("usuario_id")),
            email = rs.getString("email"),
            senha = "Sem senha para voce!",
            tipoUsuario = Role.valueOf(rs.getString("tipo_usuario")),
            usuarioCriacao = rs.getString("usuario_criacao"),
            criadoEm = rs.getTimestamp("criado_em").toInstant().toKotlinInstant(),
            atualizadoEm = rs.getTimestamp("atualizado_em")?.toInstant()?.toKotlinInstant(),
            usuarioAtualizacao = rs.getString("usuario_atualizacao")
        )

        val pessoa = Pessoa(
            id = UUID.fromString(rs.getString("pessoa_id")),
            nome = rs.getString("nome"),
            cpfcnpj = rs.getString("cpfcnpj"),
            telefone = rs.getString("telefone"),
            ativo = rs.getBoolean("ativo")
        )

        Funcionario(
            id = funcionarioId,
            contador = UUID.fromString(rs.getString("contador_id")),
            usuario = usuario,
            pessoa = pessoa,
            cargo = rs.getString("cargo"),
            setor = rs.getString("setor"),
            salario = rs.getDouble("salario"),
            dataAdmissao = rs.getTimestamp("data_admissao").toInstant().toKotlinInstant()
        )
    }

    private fun parametros(funcionario: Funcionario): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", funcionario.id)
        params.addValue("contador_id", funcionario.contador)
        params.addValue("usuario_id", funcionario.usuario.id)
        params.addValue("pessoa_id", funcionario.pessoa.id)
        params.addValue("cargo", funcionario.cargo)
        params.addValue("setor", funcionario.setor)
        params.addValue("salario", funcionario.salario)
        params.addValue("data_admissao", Timestamp.from(funcionario.dataAdmissao.toJavaInstant()))

        return params
    }


}