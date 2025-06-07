package com.example.data_cheque.adapters.http.security

import com.example.data_cheque.adapters.http.security.exceptions.CustomEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfiguration (
    private val jwtUtil: JWTUtil
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        return http.authorizeHttpRequests {
            it.requestMatchers("/login").permitAll()
            it.requestMatchers("/users").permitAll()
            it.requestMatchers("/funcionario/cadastro").permitAll()
            it.requestMatchers("/contador/cadastro").permitAll()
            it.anyRequest().authenticated()
        }.csrf {
            it.disable()
        }.addFilterBefore(JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.exceptionHandling{
                it.authenticationEntryPoint(CustomEntryPoint())
            }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:5173")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("Authorization", "Content-Type")
            allowCredentials = true
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}