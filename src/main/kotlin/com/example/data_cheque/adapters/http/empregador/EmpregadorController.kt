package com.example.data_cheque.adapters.http.empregador

import com.example.data_cheque.adapters.http.security.JwtPayload
import com.example.data_cheque.adapters.http.security.JWTUtil
import com.example.data_cheque.application.empregador.EmpregadorCommand
import com.example.data_cheque.domain.empregador.Empregador
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@CrossOrigin(origins = ["*"])
@RestController
class EmpregadorController(
    private val empregadorHandler: EmpregadorHandler,
    private val jwtUtil: JWTUtil,
) {
    @GetMapping("/empregador")
    fun findAll(@RequestHeader("Authorization") authHeader: String): ResponseEntity<List<Empregador>> {
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        return empregadorHandler.findAllByContador(claims.id.toString())
    }

    @GetMapping("/empregador/{empregadorId:$UUID_REGEX}")
    fun findById(@PathVariable empregadorId: String,
                 @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Empregador> {
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        return empregadorHandler.findByIdContador(claims.id.toString(), empregadorId)
    }

    @PostMapping("/empregador")
    fun inserir(@RequestHeader("Authorization") authHeader: String,
                @RequestBody empregador: EmpregadorCommand
    ): ResponseEntity<Empregador> {
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        return empregadorHandler.inserir(empregador, claims.id.toString())
    }

    @PutMapping("/empregador/{empregadorId:$UUID_REGEX}")
    fun atualizar(@RequestHeader("Authorization") authHeader: String,
                  @PathVariable("empregadorId") empregadorId: String,
                  @RequestBody empregador: EmpregadorCommand
    ): ResponseEntity<Empregador> {
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        return empregadorHandler.atualizar(empregador, empregadorId, claims.id.toString())
    }

    @DeleteMapping("/empregador/{empregadorId:$UUID_REGEX}")
    fun excluir(@RequestHeader("Authorization") authHeader: String,
                @PathVariable("empregadorId") empregadorId: String
    ): ResponseEntity<String> {
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)
        return empregadorHandler.excluir(claims.id.toString(), empregadorId)
    }
}