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
                // Allow login page and resources to be accessible without login
                .antMatchers("/login", "/login/**", "/resources/**", "/static/**", "/images/**", "/css/**", "/js/**").permitAll()
                // Allow the index page for all authenticated users
                .antMatchers("/").authenticated()

                // License-related paths - only accessible by specific roles
                .antMatchers("/licenses/license/**").access("hasRole('ROLE_LICENSE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/license/**").access("hasRole('ROLE_LICENSE_APPROVAL') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/license/**").access("hasRole('ROLE_LICENSE_ENTRY') or hasRole('ROLE_ADMIN')")
                // Finance-related paths - only accessible by specific roles
                .antMatchers("/licenses/finance/license_finance/administration_fees/**").access("hasRole('ROLE_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/application_fees/**").access("hasRole('ROLE_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/database_maintainance_fees/**").access("hasRole('ROLE_FINANCE') or hasRole('ROLE_ADMIN')")
                .antMatchers("/licenses/finance/license_finance/guarantee_fees/**").access("hasRole('ROLE_FINANCE') or hasRole('ROLE_ADMIN')")
                // Ministry-related paths - only accessible by specific roles
                .antMatchers("/licenses/finance/license_finance/**").access("hasRole('ROLE_MINISTRY') or hasRole('ROLE_ADMIN')")

                // Admin-only paths - only accessible by the admin role
                .antMatchers("/licenses/admin/**").access("hasRole('ROLE_ADMIN')")
                // Require authentication for all other pages
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login") // Custom login page
                .permitAll()  // Allow everyone to access the login page
                .successHandler(customAuthenticationSuccessHandler)  // Redirect after successful login
                .failureUrl("/login?error")  // Redirect to login page if authentication fails
                .usernameParameter("email")  // Custom username parameter (email)
                .passwordParameter("password")  // Custom password parameter
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // Handle logout request
                .logoutSuccessUrl("/login")  // Redirect after logout
                .invalidateHttpSession(true)  // Invalidate session on logout
                .deleteCookies("JSESSIONID")  // Delete session cookies on logout
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied")  // Redirects to /access-denied
                .and()
                .csrf().disable();  // Disable CSRF for simplicity (use with caution)
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
                        "ROLE_ADMIN > ROLE_LICENSE_ENTRY \n" +
                        "ROLE_ADMIN > ROLE_LICENSE_APPROVAL"
        );
        return roleHierarchy;
    }

}
