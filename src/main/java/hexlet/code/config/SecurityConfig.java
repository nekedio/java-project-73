package hexlet.code.config;

import hexlet.code.filter.JWTAuthenticationFilter;
import hexlet.code.filter.JWTAuthorizationFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

//import static hexlet.code.controller.UserController.USER_CONTROLLER_PATH;
import hexlet.code.helper.JWTHelper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN = "/login";

    public static final List<GrantedAuthority> DEFAULT_AUTHORITIES = List.of(new SimpleGrantedAuthority("USER"));

    //Note: Сейчас разрешены:
    // - GET('/api/users')
    // - POST('/api/users')
    // - POST('/api/login')
    // - все запросы НЕ начинающиеся на '/api'
    private final RequestMatcher publicUrls;
    private final RequestMatcher loginRequest;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;

    public SecurityConfig(
            @Value("${base-url}") final String baseUrl,
            final UserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder,
            final JWTHelper jwtHelper) {
        this.loginRequest = new AntPathRequestMatcher(baseUrl + LOGIN, POST.toString());
        this.publicUrls = new OrRequestMatcher(
                loginRequest,
                new AntPathRequestMatcher(baseUrl + "/users", POST.toString()),
                new AntPathRequestMatcher(baseUrl + "/users", GET.toString()),
                //--------------
//                new AntPathRequestMatcher(baseUrl + "/users/**", GET.toString()),
                new AntPathRequestMatcher(baseUrl + "/statuses", POST.toString()),
                new AntPathRequestMatcher(baseUrl + "/statuses", GET.toString()),
                new AntPathRequestMatcher(baseUrl + "/statuses/**", GET.toString()),
                new AntPathRequestMatcher(baseUrl + "/statuses/**", PUT.toString()),
                new AntPathRequestMatcher(baseUrl + "/statuses/**", DELETE.toString()),
//                new AntPathRequestMatcher(baseUrl + "/tasks/**", GET.toString()),
//                new AntPathRequestMatcher(baseUrl + "/tasks/**", POST.toString()),
                //--------------
                new NegatedRequestMatcher(new AntPathRequestMatcher(baseUrl + "/**"))
        );
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        final var authenticationFilter = new JWTAuthenticationFilter(
                authenticationManagerBean(),
                loginRequest,
                jwtHelper
        );

        final var authorizationFilter = new JWTAuthorizationFilter(
                publicUrls,
                jwtHelper
        );

        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(publicUrls).permitAll()
                // .and().authorizeRequests().antMatchers("/h2console/**").permitAll() //for h2console
                .anyRequest().authenticated()
                .and()
//                .exceptionHandling().authenticationEntryPoint(this::handleError)
//                .and()
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();

        http.headers().frameOptions().disable(); // for h2console
    }

//    private void handleError(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
//    }


}
