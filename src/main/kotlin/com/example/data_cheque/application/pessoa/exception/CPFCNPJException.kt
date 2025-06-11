package com.example.data_cheque.application.pessoa.exception

sealed class CPFCNPJException(mensagem: String): Exception(mensagem){
    abstract val cpfcnpj: String?
}

data class CPFCNPFExistenteException(
    override val cpfcnpj: String?
): CPFCNPJException("CPF ou CNPJ: $cpfcnpj já existente")

data class CPFCNPFInvalidoException(
    override val cpfcnpj: String?
): CPFCNPJException("CPF ou CNPJ inválido")