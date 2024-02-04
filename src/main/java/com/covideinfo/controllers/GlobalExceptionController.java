package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleError405(HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView("methodnotfound");
        Object userName = request.getSession().getAttribute("longinUserName");
        if (userName == null ) {
        	mav = new ModelAndView("redirect:login");
			
		} else {
			mav = new ModelAndView("methodnotfound");
		}
        mav.addObject("exception", e);
        return mav;
    }
}
