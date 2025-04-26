package com.example.data_cheque.adapters.jdbc.contracheque


import com.example.data_cheque.adapters.jdbc.contracheque.ContrachequeSqlExpressions.sqlSelectAll
import com.example.data_cheque.domain.ContraChequeRepository
import com.example.data_cheque.domain.Contracheque
import kotlinx.datetime.toKotlinInstant
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.UUID

@Repository
class ContrachequeJDBCRepository(
    private val db: NamedParameterJdbcOperations
) : ContraChequeRepository {
    private companion object {
        val LOGGER = KotlinLogging.logger {}
    }

    override fun findAll(): List<Contracheque> {
        val contracheques =
            try{
                db.query(sqlSelectAll(), rowMapper())
            } catch (ex: Exception) {
                LOGGER.error{"Houve um erro ao consultar os contracheques: ${ex.message}"}
                throw ex
            }
        return contracheques
    }

    override fun findById(contrachequeId: UUID): Contracheque? {
        TODO("Not yet implemented")
    }

    override fun inserir(contracheque: Contracheque): Boolean {
        TODO("Not yet implemented")
    }

    override fun atualizar(contracheque: Contracheque): Boolean {
        TODO("Not yet implemented")
    }

    override fun excluir(contrachequeId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    private fun rowMapper() =
        RowMapper<Contracheque> {rs, _ ->
            val contrachequeId = UUID.fromString(rs.getString("contracheque_id"))

            Contracheque(
                id = contrachequeId,
                funcId = UUID.fromString(rs.getString("func_id")),
                dataPagamento = rs.getDate("data_pagamento").toLocalDate(),
                dataRefInicio = rs.getDate("data_ref_inicio").toLocalDate(),
                dataRefFim = rs.getDate("data_ref_fim").toLocalDate(),
                salarioBase = rs.getDouble("salario_base"),
                horaExtra = rs.getNullableDouble("hora_extra"),
                adicionalNoturno = rs.getNullableDouble("adicional_noturno"),
                comissoes = rs.getNullableDouble("comissoes"),
                beneficios = rs.getNullableDouble("beneficios"),
                inss = rs.getDouble("ins"),
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

}