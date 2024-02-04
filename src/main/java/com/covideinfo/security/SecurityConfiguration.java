package com.covideinfo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;
 
    @Autowired(required=true)
    PersistentTokenRepository tokenRepository;
 
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().
    	antMatchers("/","/dashboard/**","/actvityReview/**","/activityLocked/**","/rules/**","/eformConfiguration/**","/administration/**",
    			    "/auditlog/**","/barcodelabels/**","/study/**","/condition/**","/customActParams/**","/defaultActivity/**","/delegation/**",
    			    "/department/**","/dosageCon/**","/dosingActivity/**","/dynamicParms/**","/globalAtivity/**","/groups/**","/parameters/**",
    			    "/values/**","/incExc/**","/instrument/**","/languages/**","/modulesAccess/**","/pdfReport/**","/projectCrf/**","/randomization/**",
    			    "/reporting/**","/userRole/**","/studydesignStaticData/**","/studyActivity/**","/studyActivityTimePoints/**","/study/clinical/**",
    			    "/clinicalReport/**","/studydesign/**","/studyExe/**","/subjectAllotment/**","/disocontinue/**","/testController/**","/testWebSocket/**",
    			    "/user/**","/userlogin/**","/userProjects/**","/studyControll/**","/activity/**","/triggerController/**", "/uad/**", "/inlag/**","/documentryRequ/**",
    			    "/drugReception/**","/pharmacyData/**","/studyDrugDispensing/**", "/crfData/**", "/dosingInfo/**", "/deviations/**", "/barcodes/**","/reviewData/**",
    			    "/drugDispance/**", "/stdMealDietConfig/**","/subMealCon/**", "/drugDispanse/**", "/drugDispansePdf/**", "/reCannula/**", "/activityUpdate/**", "/timeCtrl/**", 
    			    "/allowMeal/**", "/mealMenuReports/**", "/missedtpsReports/**")
    	 
    	.authenticated()
    	.and().logout().invalidateHttpSession(true).logoutUrl("/logout");
    	http.formLogin().loginPage("/login")
        .loginProcessingUrl("/login").successHandler(new LoginSuccessSecurityHandler())
        .failureUrl("/login?error").usernameParameter("username").passwordParameter("password").and().csrf()
    	.and().logout().permitAll()
        .and().sessionManagement().maximumSessions(1).expiredUrl("/login?expired");

    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
 
    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
        PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
                "remember-me", userDetailsService, tokenRepository);
        return tokenBasedservice;
    }
 
    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
    

}

