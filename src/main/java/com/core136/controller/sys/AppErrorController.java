package com.core136.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppErrorController implements ErrorController {
	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		try {
			Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
			if (statusCode == 401) {
				ModelAndView mv = new ModelAndView("/error-401.html");
				return mv;
			} else if (statusCode == 404) {
				ModelAndView mv = new ModelAndView("/error-404.html");
				return mv;
			} else if (statusCode == 403) {
				ModelAndView mv = new ModelAndView("/error-403.html");
				return mv;
			} else {
				ModelAndView mv = new ModelAndView("/error-500.html");
				return mv;
			}
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("titps.html");
			return mv;
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
