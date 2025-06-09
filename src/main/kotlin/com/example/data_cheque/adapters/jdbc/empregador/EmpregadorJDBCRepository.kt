package com.example.data_cheque.adapters.jdbc.empregador

import com.example.data_cheque.domain.empregador.Empregador
import com.example.data_cheque.domain.empregador.EmpregadorRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import com.example.data_cheque.adapters.jdbc.empregador.EmpregadorSqlExpressions.sqlSelectAllContador
import com.example.data_cheque.adapters.jdbc.empregador.EmpregadorSqlExpressions.sqlSelectByIdContador
import com.example.data_cheque.adapters.jdbc.empregador.EmpregadorSqlExpressions.sqlInsertEmpregador
import com.example.data_cheque.adapters.jdbc.empregador.EmpregadorSqlExpressions.sqlUpdateEmpregador
import com.example.data_cheque.adapters.jdbc.empregador.EmpregadorSqlExpressions.sqlDeleteById
import kotlinx.datetime.toKotlinInstant
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Types
import java.util.UUID

@Repository
class EmpregadorJDBCRepository(
    private val db: NamedParameterJdbcOperations
): EmpregadorRepository {
    private companion object {
        val LOGGER = KotlinLogging.logger {}
    }

    override fun findAllByContador(contadorId: UUID): List<Empregador> {
        val empregadores =
            try{
                val params = MapSqlParameterSource("contador_id", contadorId)
                db.query(sqlSelectAllContador(), params, rowMapper())
            }catch(ex: Exception){
                LOGGER.error { "Houve um erro ao consultar os empregadores: {${ex.message}}" }
                throw ex
            }
        return empregadores
    }

    override fun findByIdContador(contadorId: UUID, empregadorId: UUID): Empregador? {
        val empregador =
            try{
                val params = MapSqlParameterSource(mapOf("id" to empregadorId, "contador_id" to contadorId))
                db.query(sqlSelectByIdContador(), params, rowMapper()).firstOrNull()
            }catch (ex:Exception){
                LOGGER.error { "Houve um erro ao consultar empregadores: {${ex.message}}" }
                throw ex
            }
        return empregador
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun inserir(empregador: Empregador, contadorId: UUID): Boolean {
        try{
            val params = parametros(empregador, contadorId )
            println(params)

            val linhasAfetadas = db.update(sqlInsertEmpregador(), params)
            return linhasAfetadas > 0
        }catch (ex:Exception){
            LOGGER.error { "Houve um erro ao cadastrar empregador: {${ex.message}}" }
            throw ex
        }
    }

    override fun atualizar(empregador: Empregador, contadorId: UUID): Boolean {
        try{
            val params = parametros(empregador, contadorId)

            val linhasAfetadas = db.update(sqlUpdateEmpregador(), params)
            return linhasAfetadas > 0
        }catch (ex:Exception){
            LOGGER.error { "Houve um erro ao atualizar empregador: {${ex.message}}" }
            throw ex
        }
    }

    override fun excluir(empregadorId: UUID): Boolean {
        try{
            val params = MapSqlParameterSource("id", empregadorId)

            val linhasExcluidas = db.update(sqlDeleteById(), params)
            return linhasExcluidas == 0
        }catch (ex:Exception){
            LOGGER.error { "Houve um erro ao excluir empregador: {${ex.message}}" }
            throw ex
        }
    }

    private fun rowMapper() =
        RowMapper<Empregador> {rs, _ ->
            val empregadorId = UUID.fromString(rs.getString("id"))

            Empregador(
                id = empregadorId,
                contadorId = UUID.fromString(rs.getString("contador_id")),
                cnpj = rs.getString("cnpj"),
                razaoSocial = rs.getString("razao_social"),
                nomeFantasia = rs.getString("nome_fantasia"),
                endereco = rs.getString("endereco"),
                criadoEm = rs.getTimestamp("criado_em").toInstant().toKotlinInstant(),
                usuarioCriacao = rs.getString("usuario_criacao"),
                atualizadoEm =
                    if(rs.getTimestamp("atualizado_em") ==  null){
                        null
                    }else{
                        rs.getTimestamp("atualizado_em").toInstant().toKotlinInstant()
                    },
                usuarioAtualizacao = rs.getString("usuario_atualizacao"),
            )
        }

    private fun parametros(empregador: Empregador, contadorId: UUID): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", empregador.id)
        params.addValue("contador_id", contadorId)
        params.addValue("cnpj", empregador.cnpj)
        params.addValue("razao_social", empregador.razaoSocial)
        params.addValue("nome_fantasia", empregador.nomeFantasia)
        params.addValue("endereco", empregador.endereco)
        params.addValue("criado_em", empregador.criadoEm)
        params.addValue("usuario_criacao", empregador.usuarioCriacao)
        if(empregador.atualizadoEm != null){
            params.addValue("atualizado_em", empregador.atualizadoEm)
        }else{
            params.addValue("atualizado_em", null, Types.TIMESTAMP)
        }
        params.addValue("usuario_atualizacao", empregador.usuarioAtualizacao)

        return params
    }
}