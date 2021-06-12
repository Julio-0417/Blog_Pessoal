package org.generation.blogPessoal.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity		//habilita a configuração de websecurity e em parte desabilita a configuração padrão
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	 * Método que sobrescreve configuração de segurança
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("julio123").password(passWordEncoder().encode("julio123")).authorities("ROLE_ADMIN");
		auth.userDetailsService(userDetailsService);
	}
	
	/*
	 * Criptografa a senha no intuito de deixá-la mais segura
	 */
	
	@Bean		//sinaliza que o objeto poderá ser usado por outras classes como dependencia
	public PasswordEncoder passWordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * Sobreescrevendo as diretrizes para segurança
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{		//throws funciona como try catch
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll()		//.antMatchers(HttpMethod.POST, "/usuarios/logar").permitALL()
		.antMatchers("/usuarios/cadastrar").permitAll()		//rota permitida sem acesso
		.anyRequest().authenticated()
		.and().httpBasic()		//será utilizado o padrão Basic para criar a chave
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)		//gerente de sessão, insere política e regra de não guardar sessão
		.and().cors()		//permite acessar recursos de dominios diferentes
		.and().csrf().disable();		//desabilita o csrf para usar todas as configurações padrões
	}

}
