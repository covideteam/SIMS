package com.covideinfo.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.covideinfo.interceptor.MainInterceptor;
import com.covideinfo.interceptor.UrlLocaleInterceptor;

import org.springframework.web.servlet.LocaleResolver;

@Configuration
@EnableWebMvc
@ComponentScan({"com.covideinfo","com.covide"})
@EnableAspectJAutoProxy
/* @MultipartConfig(maxFileSize = 5242880) */
public class AppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	/**
	 * Configure TilesConfigurer.
	 */
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/views/**/tiles.xml" });
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}

	/**
	 * Configure ViewResolvers to deliver preferred views.
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		TilesViewResolver viewResolver = new TilesViewResolver();
		registry.viewResolver(viewResolver);
	}

	/**
	 * Configure ResourceHandlers to serve static resources like CSS/ Javascript
	 * etc...
	 */

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	/**
	 * Configure Converter to be used. In our example, we need a converter to
	 * convert string values[Roles] to UserProfiles in newUser.jsp
	 */
/*	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(roleToUserProfileConverter);
	}*/

	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}

	@Bean(name = "filterMultipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		return multipartResolver;
	}

	 /* messageSource bean is spring built-in bean name which will manipulate internationalization messages.
     * All message files is saved in src/main/resources/config/ folder, if the config folder do not exist, you need to create it first by hand.
     * Each message file is a properties file, the file base name is messages and suffix with the language or country ISO code, such as messages_en, messages_zh_cn etc.
     * */
	@Bean(name = "messageSource")
	   public MessageSource getMessageResource()  {
	       ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
	       
	       // Read i18n/messages_xxx.properties file.
	       // For example: i18n/messages_en.properties

	       messageResource.setBasename("classpath:languages/messages");
	       messageResource.setDefaultEncoding("UTF-8");
	       return messageResource;
	   }
    
    
    
    /* The LocaleChangeInterceptor is a interceptor that will intercept user locale change by a parameter value. 
     * For example, if we set the locale change parameter name is locale, then request url http://localhost:8088/index.jsp?locale=en will change 
     * user locale to en and display messages in src/main/resources/config/messages_en.properties.
     *  */
    @Bean(name="localeInterceptor")
    public LocaleChangeInterceptor getLocaleInterceptor(){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("language");
        return interceptor;
    }
    
 // To solver URL like:
    // /SpringMVCInternationalization/en/login2
    // /SpringMVCInternationalization/vi/login2
    // /SpringMVCInternationalization/fr/login2
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver()  {
        LocaleResolver resolver= new UrlLocaleResolver();
        return resolver;
    }
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        /*localeInterceptor.setParamName("lang");
//        registry.addInterceptor(localeInterceptor).addPathPatterns("/*");
        */
    	UrlLocaleInterceptor urllocaleInterceptor = new UrlLocaleInterceptor();
        registry.addInterceptor(urllocaleInterceptor).addPathPatterns("/*");
//        registry.addInterceptor(urllocaleInterceptor).addPathPatterns("/en/*","/es/*");
       
		registry.addInterceptor(new MainInterceptor());
	}
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    

}
