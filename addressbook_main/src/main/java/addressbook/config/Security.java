package addressbook.config;

import addressbook.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class Security extends WebSecurityConfigurerAdapter {

    private final AuthService authService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilterAfter(requestHeaderAuthenticationFilter(), LogoutFilter.class)
                .authenticationProvider(preAuthenticatedAuthenticationProvider());
    }

    public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() throws Exception {
        RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
        filter.setPrincipalRequestHeader("login");
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setExceptionIfHeaderMissing(false);
        filter.setCheckForPrincipalChanges(true);
        return filter;
    }

    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(userDetailsByNameServiceWrapper());
        return provider;
    }

    public UserDetailsByNameServiceWrapper userDetailsByNameServiceWrapper() {
        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> serviceWrapper = new UserDetailsByNameServiceWrapper<>();
        serviceWrapper.setUserDetailsService(customUserDetailsService());
        return serviceWrapper;
    }

    public UserDetailsService customUserDetailsService() {
        return username -> {
            if (authService.isUserAuthByUserName(username)) {

                return User.withUsername(username)
                        .password("")
                        .roles("USER")
                        .build();
            } else return User.withUsername(username)
                    .password("")
                    .roles("ANONYMOUS")
                    .build();

        };
    }

    private boolean getUserByUserName(String username) {
        log.info("Auth user: {}", username);
        return false;
    }
}
