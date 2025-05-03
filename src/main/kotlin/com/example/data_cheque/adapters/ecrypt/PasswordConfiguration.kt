package com.example.data_cheque.adapters.ecrypt

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class PasswordConfiguration {

    @Bean
    fun encoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}