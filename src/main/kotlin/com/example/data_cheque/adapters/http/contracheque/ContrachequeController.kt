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
import org.springframework.web.bind.annotation.RestController

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@CrossOrigin(origins = ["*"])
@RestController
class ContrachequeController(
    private val contrachequeHandler: ContrachequeHandler
) {
    @GetMapping("/contracheques/{funcionarioId:$UUID_REGEX}")
    fun findAll(@PathVariable funcionarioId: String ): ResponseEntity<List<Contracheque>>{
        return contrachequeHandler.findAll(funcionarioId)
    }

    @GetMapping("/contracheques/{funcionarioId:$UUID_REGEX}/{contrachequeId:$UUID_REGEX}")
    fun findById(
        @PathVariable funcionarioId: String,
        @PathVariable contrachequeId: String
    ): ResponseEntity<Contracheque>{
        return contrachequeHandler.findById(contrachequeId, funcionarioId )
    }

    @PostMapping("/contracheques/{funcionarioId:$UUID_REGEX}")
    fun inserir(
        @RequestBody contracheque: ContrachequeCommand,
        @PathVariable funcionarioId: String)
    : ResponseEntity<Contracheque>{
        return contrachequeHandler.inserir(contracheque, funcionarioId)
    }

    @PutMapping("/contracheques/{funcionarioId:$UUID_REGEX}/{contrachequeId:$UUID_REGEX}")
    fun atualizar(
        @RequestBody contracheque: ContrachequeCommand,
        @PathVariable contrachequeId: String,
        @PathVariable funcionarioId: String
    ): ResponseEntity<Contracheque> {
        return contrachequeHandler.atualizar(contracheque, contrachequeId, funcionarioId)
    }

    @DeleteMapping("/contracheques/{funcionarioId:$UUID_REGEX}/{contrachequeId:$UUID_REGEX}")
    fun excluir(
        @PathVariable contrachequeId: String,
        @PathVariable funcionarioId: String
    ): ResponseEntity<String>{
        return contrachequeHandler.excluir(contrachequeId, funcionarioId)
    }

}