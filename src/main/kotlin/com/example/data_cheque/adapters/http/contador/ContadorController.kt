package com.example.data_cheque.adapters.http.contador

import com.example.data_cheque.application.contador.ContadorCreateCommand
import com.example.data_cheque.application.contador.ContadorUpdateCommand
import com.example.data_cheque.domain.contador.Contador
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
@CrossOrigin(origins = arrayOf("*"))
class ContadorController(
    private val contadorHandler: ContadorHandler
) {
    @GetMapping("/contador")
    fun findAll(): ResponseEntity<List<Contador>> {
        return contadorHandler.findAll()
    }

    @GetMapping("/contador/{contadorId:$UUID_REGEX}")
    fun findById(@PathVariable contadorId: String): ResponseEntity<Contador>{
        return contadorHandler.findById(contadorId)
    }

    @PostMapping("/contador/cadastro")
    fun insert(@RequestBody contadorCreateCommand: ContadorCreateCommand): ResponseEntity<Contador>{
        return contadorHandler.insert(contadorCreateCommand)
    }

    @PutMapping("/contador/{contadorId:$UUID_REGEX}")
    fun update(@RequestBody contadorUpdateCommand: ContadorUpdateCommand,
               @PathVariable contadorId: String): ResponseEntity<Contador> {
        return contadorHandler.update(contadorUpdateCommand, contadorId)
    }

    @DeleteMapping("/contador/{contadorId:$UUID_REGEX}")
    fun delete(@PathVariable contadorId: String): ResponseEntity<String> {
        return contadorHandler.delete(contadorId)
    }
}