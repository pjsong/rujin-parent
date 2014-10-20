package ruking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public abstract class BaseController implements Controller
{
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	process(request, response);
    	return null;
    }
    
    public abstract void process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}