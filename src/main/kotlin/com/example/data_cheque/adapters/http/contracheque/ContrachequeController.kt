package com.example.data_cheque.adapters.http.contracheque

import com.example.data_cheque.domain.contracheque.Contracheque
import com.example.data_cheque.application.contracheque.ContrachequeCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import com.example.data_cheque.adapters.http.security.JWTUtil
import com.example.data_cheque.adapters.http.security.JwtPayload

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@CrossOrigin(origins = ["*"])
@RestController
class ContrachequeController(
    private val contrachequeHandler: ContrachequeHandler,
    private val jwtUtil: JWTUtil
) {
    @GetMapping("/contracheques")
    fun findAll(@RequestHeader("Authorization") authHeader: String): ResponseEntity<List<Contracheque>>{
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        if(claims.tipo == "ROLE_FUNCIONARIO"){
            return contrachequeHandler.findAllByFuncionario(claims.id.toString())
        }else if(claims.tipo == "ROLE_CONTADOR"){
            return contrachequeHandler.findAllByContador(claims.id.toString())
        }
        throw Exception("Erro ao reconhecer ROLE")
    }

    @GetMapping("/empregador/{empregadorId:$UUID_REGEX}/contracheques")
    fun findAllEmpregador(@PathVariable empregadorId: String): ResponseEntity<List<Contracheque>>{
        return contrachequeHandler.findAllByEmpregador(empregadorId)
    }

    @GetMapping("/contracheques/{contrachequeId:$UUID_REGEX}")
    fun findById(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable contrachequeId: String
    ): ResponseEntity<Contracheque>{
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        if(claims.tipo == "ROLE_FUNCIONARIO"){
            return contrachequeHandler.findByIdFuncionario( contrachequeId,claims.id.toString())
        }else if(claims.tipo == "ROLE_CONTADOR"){
            return contrachequeHandler.findByIdContador(contrachequeId, claims.id.toString())
        }
        throw Exception("Erro ao reconhecer ROLE")
    }

    @PostMapping("/contracheques")
    fun inserir(@RequestHeader("Authorization") authHeader: String,
                @RequestBody contracheque: ContrachequeCommand
    ): ResponseEntity<Contracheque>{
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        return contrachequeHandler.inserir(contracheque, claims.id.toString())
    }

    @PutMapping("/contracheques/{contrachequeId:$UUID_REGEX}")
    fun atualizar(
        @RequestBody contracheque: ContrachequeCommand,
        @PathVariable contrachequeId: String,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Contracheque> {
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        return contrachequeHandler.atualizar(contracheque, contrachequeId, claims.id.toString())
    }

    @DeleteMapping("/contracheques/{contrachequeId:$UUID_REGEX}")
    fun excluir(
        @PathVariable contrachequeId: String,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<String>{
        val token = authHeader.removePrefix("Bearer ").trim()
        val payloadJson = jwtUtil.decodificarJwtPayload(token)

        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        val claims = json.decodeFromString<JwtPayload>(payloadJson)

        return contrachequeHandler.excluir(contrachequeId, claims.id.toString())
    }

}