package br.com.leonardosugahara.security.oauth2ldapsecurityserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;

import java.util.Arrays;

@Configuration
public class ServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${ldap.config.url}")
    private String ldapUrl;

    @Value("${ldap.config.userdn}")
    private String ldapUserDn;

    @Value("${ldap.config.secret}")
    private String ldapSecret;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/oauth/token","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        DefaultSpringSecurityContextSource contextSource = contextSource();
        auth
        .ldapAuthentication()
                .userDnPatterns("cn={0},cn=USER,cn=users,ou=members,dc=leonardosugahara,dc=com,dc=br")
                .userSearchBase("cn=USER,cn=users,ou=members,dc=leonardosugahara,dc=com,dc=br")
                .groupSearchBase("ou=groups,dc=leonardosugahara,dc=com,dc=br")
                .contextSource(contextSource)
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword")
                .and().ldapAuthoritiesPopulator(ldapAuthoritiesPopulator(contextSource));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder(){
        return  new LdapShaPasswordEncoder();
    }

    @Bean
    public LdapAuthoritiesPopulator ldapAuthoritiesPopulator(DefaultSpringSecurityContextSource contextSource) {
        DefaultLdapAuthoritiesPopulator populator = new DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups,dc=leonardosugahara,dc=com,dc=br");
        populator.setSearchSubtree(true);
        populator.setIgnorePartialResultException(true);
        return populator;
    }

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(
                Arrays.asList(ldapUrl), ""){{
            setUserDn(ldapUserDn);
            setPassword(ldapSecret);
        }};
    }

    @Bean
    @Override
    public LdapUserDetailsService userDetailsServiceBean() throws Exception {
        LdapContextSource ldapContext = contextSource();
        String searchBase = "cn=USER,cn=users,ou=members,dc=leonardosugahara,dc=com,dc=br";
        String searchFilter = "cn={0}";
        FilterBasedLdapUserSearch search = new FilterBasedLdapUserSearch(searchBase, searchFilter, ldapContext);
        search.setSearchSubtree(false);
        LdapUserDetailsService service = new LdapUserDetailsService(search);
        service.setUserDetailsMapper(new LdapUserDetailsMapper());
        return service;
    }

}
