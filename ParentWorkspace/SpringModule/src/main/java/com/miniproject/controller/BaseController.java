package com.miniproject.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.miniproject.util.LogUtil;

/**
 * @author muhilkennedy
 *
 */
@RestController
public class BaseController {

	/**
	 * @param request
	 * @return home page of the application
	 */
	@RequestMapping("/")
    public RedirectView index(HttpServletRequest request) {
		RedirectView redirectURL = new RedirectView();
		LogUtil.getLogger().debug("Request URL : "+request.getRequestURL().toString());
		redirectURL.setUrl(request.getRequestURL().toString()+"index.html?status=0");
		return redirectURL;
	}

}
