package com.covideinfo.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

	private static final Logger logger = Logger.getLogger(ExceptionHandlerController.class);

	/*
	 * Handing all type of exception's witch are not handled by Developer. Print
	 * error details in log file and Redirecting error page to end user.
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, Exception ex) {
		logger.error("Execution exception occurred:", ex);
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.addObject("pageError", "Internal Server Error.. ");
		// mav.setViewName("internalError");
		return mav;
	}
}