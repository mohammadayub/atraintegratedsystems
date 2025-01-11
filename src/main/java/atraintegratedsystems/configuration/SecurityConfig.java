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
                .antMatchers("/licenses/license/**").access("hasRole('ROLE_LICENSE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/administration_fees/**").access("hasRole('ROLE_ATRA_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/application_fees/**").access("hasRole('ROLE_ATRA_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/database_maintainance_fees/**").access("hasRole('ROLE_ATRA_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/guarantee_fees/**").access("hasRole('ROLE_ATRA_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/mcit/**").access("hasRole('ROLE_MCIT_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/**").hasRole("ADMIN")
                .antMatchers("/**").hasRole("LICENSE")
                .antMatchers("/**").hasRole("ATRA_FINANCE")
                .antMatchers("/**").hasRole("MCIT_FINANCE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll()
                .failureUrl("/login") // Include language in failure URL
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login") // Include language in logout success URL
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .and()
                .csrf().disable();
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
                        "ROLE_ADMIN > ROLE_ATRA_FINANCE \n" +
                        "ROLE_ADMIN > ROLE_MCIT_FINANCE \n" +
                        "ROLE_ADMIN > ROLE_ISSUE_LICENSE"
        );
        return roleHierarchy;
    }
}
