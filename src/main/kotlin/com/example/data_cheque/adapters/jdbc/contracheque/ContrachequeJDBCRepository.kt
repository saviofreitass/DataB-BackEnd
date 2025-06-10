package com.example.data_cheque.adapters.jdbc.contracheque


import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlDeleteById
import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlInsertContracheque
import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlSelectAllContador
import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlSelectAllEmpregador
import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlSelectAllFuncionario
import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlSelectByIdContador
import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlSelectByIdFuncionario
import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlUpdateContracheque
import com.example.data_cheque.domain.contracheque.ContraChequeRepository
import com.example.data_cheque.domain.contracheque.Contracheque
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.Timestamp
import java.sql.Types
import java.util.UUID

@Repository
class ContrachequeJDBCRepository(
    private val db: NamedParameterJdbcOperations
) : ContraChequeRepository {
    private companion object {
        val LOGGER = KotlinLogging.logger {}
    }

    override fun findAllByFuncionario(funcionarioId: UUID): List<Contracheque> {
        val contracheques =
            try{
                val params = MapSqlParameterSource("funcionario_id", funcionarioId)
                db.query(sqlSelectAllFuncionario(), params, rowMapper())
            } catch (ex: Exception) {
                LOGGER.error{"Houve um erro ao consultar os contracheques: ${ex.message}"}
                throw ex
            }
        return contracheques
    }

    override fun findAllByContador(contadorId: UUID): List<Contracheque> {
        val contracheques =
            try{
                val params = MapSqlParameterSource("contador_id", contadorId)
                db.query(sqlSelectAllContador(), params, rowMapper())
            }catch(ex:Exception){
                LOGGER.error{"Houve um erro ao consultar os contracheques: ${ex.message}"}
                throw ex
            }
        return contracheques
    }

    override fun findAllByEmpregador(empregadorId: UUID): List<Contracheque>{
        val contracheques =
            try{
                val params = MapSqlParameterSource("empregador_id", empregadorId)
                db.query(sqlSelectAllEmpregador(), params, rowMapper())
            }catch(ex:Exception){
                LOGGER.error { "Houve um erro ao consultar os contracheques: ${ex.message}" }
                throw ex
            }
        return contracheques
    }

    override fun findByIdFuncionario(contrachequeId: UUID , funcionarioId: UUID): Contracheque? {
        val contracheque =
            try{
                val params = MapSqlParameterSource(mapOf("id" to contrachequeId , "funcionario_id" to funcionarioId))
                db.query(sqlSelectByIdFuncionario(), params, rowMapper()).firstOrNull()
            } catch (ex: Exception) {
                LOGGER.error { "Houve um erro ao consultar o contracheque : ${ex.message}" }
                throw ex
            }
        return contracheque
    }

    override fun findByIdContador(contrachequeId: UUID, contadorId: UUID): Contracheque? {
        val contracheque =
            try{
                val params = MapSqlParameterSource(mapOf("id" to contrachequeId , "contador_id" to contadorId))
                db.query(sqlSelectByIdContador(), params, rowMapper()).firstOrNull()
            }catch(ex:Exception){
                LOGGER.error { "Houve um erro ao consultar o contracheque : ${ex.message}" }
                throw ex
            }
        return contracheque
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun inserir(contracheque: Contracheque): Boolean {
        try{
            val params = parametros(contracheque)

            val linhasAfetadas = db.update(sqlInsertContracheque(), params)
            return linhasAfetadas > 0
        } catch (ex: Exception) {
            LOGGER.error{"Houve um erro ao cadastrar o contracheque: ${ex.message}"}
            throw ex
        }
    }

    override fun atualizar(contracheque: Contracheque): Boolean {
        try{
            val params = parametros(contracheque)

            val linhasAfetadas = db.update(sqlUpdateContracheque(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao atualizar o contracheque: ${ex.message}" }
            throw ex
        }
    }

    override fun excluir(contrachequeId: UUID): Boolean {
        try{
            val params = MapSqlParameterSource("id", contrachequeId)

            val linhasExcluidas = db.update(sqlDeleteById(), params)
            return linhasExcluidas == 1
        }catch (ex:Exception){
            LOGGER.error { "Houve um erro ao excluid contracheque: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() =
        RowMapper<Contracheque> { rs, _ ->
            val contrachequeId = UUID.fromString(rs.getString("id"))

            Contracheque(
                id = contrachequeId,
                funcId = UUID.fromString(rs.getString("funcionario_id")),
                contadorId = UUID.fromString(rs.getString("contador_id")),
                empregadorId = UUID.fromString(rs.getString("empregador_id")),
                dataPagamento = rs.getDate("data_pagamento").toLocalDate(),
                dataRefInicio = rs.getDate("data_ref_inicio").toLocalDate(),
                dataRefFim = rs.getDate("data_ref_fim").toLocalDate(),
                salarioBase = rs.getDouble("salario_base"),
                horaExtra = rs.getNullableDouble("hora_extra"),
                adicionalNoturno = rs.getNullableDouble("adicional_noturno"),
                comissoes = rs.getNullableDouble("comissoes"),
                beneficios = rs.getNullableDouble("beneficios"),
                inss = rs.getDouble("inss"),
                irrf = rs.getNullableDouble("irrf"),
                fgts = rs.getDouble("fgts"),
                outrosDescontos = rs.getNullableDouble("outros_descontos"),
                criadoEm = rs.getTimestamp("criado_em").toInstant().toKotlinInstant(),
                usuarioCriacao = rs.getString("usuario_criacao"),
                atualizadoEm =
                     if(rs.getTimestamp("atualizado_em") == null){
                         null
                     } else {
                         rs.getTimestamp("atualizado_em").toInstant().toKotlinInstant()
                     },
                usuarioAtualizacao = rs.getString("usuario_atualizacao"),
            )
        }

        private fun ResultSet.getNullableDouble(columnLabel: String): Double? {
            val value = this.getDouble(columnLabel)
            return if (this.wasNull()) null else value
        }

    private fun parametros(contracheque: Contracheque): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", contracheque.id)
        params.addValue("funcionario_id", contracheque.funcId)
        params.addValue("contador_id", contracheque.contadorId)
        params.addValue("empregador_id", contracheque.empregadorId)
        params.addValue("data_pagamento", contracheque.dataPagamento)
        params.addValue("data_ref_inicio", contracheque.dataRefInicio)
        params.addValue("data_ref_fim", contracheque.dataRefFim)
        params.addValue("salario_base", contracheque.salarioBase)
        params.addValue("hora_extra", contracheque.horaExtra)
        params.addValue("adicional_noturno", contracheque.adicionalNoturno)
        params.addValue("comissoes", contracheque.comissoes)
        params.addValue("beneficios", contracheque.beneficios)
        params.addValue("inss", contracheque.inss)
        params.addValue("irrf", contracheque.irrf)
        params.addValue("fgts", contracheque.fgts)
        params.addValue("outros_descontos", contracheque.outrosDescontos)
        params.addValue("criado_em", Timestamp.from(contracheque.criadoEm.toJavaInstant()), Types.TIMESTAMP)
        params.addValue("usuario_criacao", contracheque.usuarioCriacao)
        if(contracheque.atualizadoEm != null) {
            params.addValue("atualizado_em", Timestamp.from(contracheque.atualizadoEm.toJavaInstant()), Types.TIMESTAMP)
        }else{
            params.addValue("atualizado_em", null, Types.TIMESTAMP)
        }
        params.addValue("usuario_atualizacao", contracheque.usuarioAtualizacao)

        return params
    }

}