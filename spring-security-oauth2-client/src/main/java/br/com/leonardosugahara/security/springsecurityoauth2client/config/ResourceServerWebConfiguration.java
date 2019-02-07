package br.com.leonardosugahara.security.springsecurityoauth2client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class ResourceServerWebConfiguration implements WebMvcConfigurer {
}
