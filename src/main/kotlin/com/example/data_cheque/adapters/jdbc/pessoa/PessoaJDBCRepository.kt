package com.example.data_cheque.adapters.jdbc.pessoa


import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.pessoa.PessoaRepository
import com.example.data_cheque.adapters.jdbc.pessoa.PessoaSQLExpressions.sqlSelectAll
import com.example.data_cheque.adapters.jdbc.pessoa.PessoaSQLExpressions.sqlSelectById
import com.example.data_cheque.adapters.jdbc.pessoa.PessoaSQLExpressions.sqlDeletePessoaById
import com.example.data_cheque.adapters.jdbc.pessoa.PessoaSQLExpressions.sqlInsertPessoa
import com.example.data_cheque.adapters.jdbc.pessoa.PessoaSQLExpressions.sqlUpdatePessoa
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import mu.KotlinLogging
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import java.util.*

@Repository
class PessoaJDBCRepository (private val db: NamedParameterJdbcOperations) : PessoaRepository {
    private companion object{
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(): List<Pessoa> {
        val pessoa = try {
            db.query(sqlSelectAll(), rowMapper())
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar as pessoas: ${ex.message}" }
            throw ex
        }
        return pessoa
        }


    override fun findById(id: UUID): Pessoa? {
        val pessoa = try {
            val params = MapSqlParameterSource("id", id)
            db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar a pessoa: ${ex.message}" }
            throw ex
        }
        return pessoa
    }

    override fun insert(pessoa: Pessoa): Boolean {
        try {
            val params = parametros(pessoa)
            val linhasAfetadas = db.update(sqlInsertPessoa(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao inserir a pessoa: ${ex.message}" }
            throw ex
        }
    }

    override fun update(pessoa: Pessoa): Boolean {
        try {
            val params = parametros(pessoa)
            val linhasAfetadas = db.update(sqlUpdatePessoa(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao atualizar a pessoa: ${ex.message}" }
            throw ex
        }
    }

    override fun delete(id: UUID): Boolean {
        try {
            val params = MapSqlParameterSource("id", id)
            val linhasExcluidas = db.update(sqlDeletePessoaById(), params)
            return linhasExcluidas == 1
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao excluir a pessoa: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() = org.springframework.jdbc.core.RowMapper<Pessoa> { rs, _ ->
        val pessoaId = UUID.fromString(rs.getString("id"))
        Pessoa(
            id = pessoaId,
            nome = rs.getString("nome"),
            cpfcnpj = rs.getString("cpfcnpj"),
            telefone = rs.getString("telefone"),
            ativo = rs.getString("ativo").toBoolean(),
            usuarioId = UUID.fromString(rs.getString("usuarioId"))
        )

    }

    private fun parametros(pessoa: Pessoa): MapSqlParameterSource{
        val params = MapSqlParameterSource()
        params.addValue("id", pessoa.id)
        params.addValue("nome", pessoa.nome)
        params.addValue("cpfcnpj", pessoa.cpfcnpj)
        params.addValue("telefone", pessoa.telefone)
        params.addValue("ativo", pessoa.ativo)
        params.addValue("usuario_id", pessoa.usuarioId)

        return params
    }
}