package com.br.ApiReme.` Port`.saida

interface TokenPortS {
    fun generateToken(
        subject: String,
        roles: List<String>
    ): String
}