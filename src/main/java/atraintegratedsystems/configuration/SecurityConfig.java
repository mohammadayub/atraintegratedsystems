package atraintegratedsystems.configuration;

import atraintegratedsystems.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Allow login page and static resources without authentication
                .antMatchers("/login", "/login/**", "/resources/**", "/static/**", "/images/**", "/css/**", "/js/**").permitAll()

                // ✅ ALLOW INTEGRATION API PUBLICLY (no auth needed)
                .antMatchers("/api/integration/**").permitAll()

                // ✅ ADD THIS LINE TO ALLOW UNPAID API PUBLICLY
                .antMatchers("/api/licenses/unpaid-mcit").permitAll()

                // Index page
                .antMatchers("/").authenticated()

                // License-related paths
                .antMatchers("/licenses/license/**").access("hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL') or hasRole('ROLE_LICENSE_PROFILE_ENTRY') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
                .antMatchers("/licenses/finance/**").access("hasRole('ROLE_FINANCE') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MINISTRY')")
                .antMatchers("/licenses/finance/license_finance/**").access("hasRole('ROLE_MINISTRY') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_ADMIN')")

                // Admin-only paths
                .antMatchers("/licenses/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN')")
                .antMatchers("/codes/**").access("hasRole('ROLE_CODES_ADMIN')")
                .antMatchers("/typeofapprovals/**").access("hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_ADMIN')")

                // All other requests need authentication
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/login?error")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
                // Disable CSRF only for integration APIs
                .csrf()
                .ignoringAntMatchers("/api/integration/**", "/api/licenses/unpaid-mcit"); // ✅ Include here to disable CSRF for Postman POST if needed
    }




    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**");
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                "ROLE_ADMIN > ROLE_LICENSE \n" +
                        "ROLE_ADMIN > ROLE_FINANCE \n" +
                        "ROLE_ADMIN > ROLE_MINISTRY \n" +
                        "ROLE_ADMIN > ROLE_LICENSE_PROFILE_ENTRY \n" +
                        "ROLE_ADMIN > ROLE_LICENSE_COMPLETION_PROFILE \n" +
                        "ROLE_ADMIN > ROLE_LICENSE_APPROVAL \n"+
                        "ROLE_ADMIN > ROLE_LICENSE_ADMIN \n"+
                        "ROLE_ADMIN > ROLE_CODES_ADMIN \n"+
                        "ROLE_ADMIN > ROLE_typeofapprovals_ADMIN"

        );
        return roleHierarchy;
    }

}
