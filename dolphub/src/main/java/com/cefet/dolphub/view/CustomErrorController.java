// package com.cefet.dolphub.view;

// import org.springframework.boot.web.servlet.error.ErrorAttributes;
// import org.springframework.boot.web.servlet.error.ErrorController;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.servlet.ModelAndView;

// import javax.servlet.http.HttpServletRequest;

// @Controller
// public class CustomErrorController implements ErrorController {

// private static final String PATH = "/error";

// @RequestMapping(PATH)
// public ModelAndView error(HttpServletRequest request) {
// ModelAndView mav = new ModelAndView("error");
// String errorMessage = (String)
// request.getAttribute("javax.servlet.error.message");
// mav.addObject("errorMessage", errorMessage);
// return mav;
// }

// @Override
// public String getErrorPath() {
// return PATH;
// }
// }
