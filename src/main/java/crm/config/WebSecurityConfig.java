package crm.config;

import crm.security.CrmPermissionEvaluator;
import crm.security.UsuarioAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuracion para el sistema de seguridad (spring security) de la aplicacion. Se agrega un servicio
 * para realizar el login de un usuario dentro del sistema.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Servicio para autenticar usuarios del sistema segun el mecanismo de spring security.
     * @see org.springframework.security.core.userdetails.UserDetailsService
     */
    @Autowired
    private UsuarioAuthenticationProvider usuarioAuthenticationProvider;

    /**
     * Configuracion de seguridad para las urls del sitio.
     *
     * @param http objeto de configuracion para peticiones http especificas.
     * @see org.springframework.security.config.annotation.web.builders.HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/font-awesome/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .permitAll();
        ;
    }

    /**
     * Configuracion para autenticacion JDBC y creacion de
     * {@link org.springframework.security.authentication.AuthenticationManager}
     *
     * @param authManagerBuilder {@link org.springframework.security.config.annotation.SecurityBuilder} utilizado para
     *                           crear un {@link org.springframework.security.authentication.AuthenticationManager}.
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder)
            throws Exception {
        authManagerBuilder.userDetailsService(usuarioAuthenticationProvider).passwordEncoder(new Md5PasswordEncoder());
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    static class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Autowired
        private CrmPermissionEvaluator permissionEvaluator;

        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
            handler.setPermissionEvaluator(permissionEvaluator);
            return handler;
        }
    }

}