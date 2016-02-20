package com.xidian.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.forms.User;
import com.xidian.service.api.UserService;
import com.xidian.util.SecurityUtil;

@Controller
public class UserController {
	@Resource(name="userServiceImpl")
	UserService userService;
	
	@RequestMapping(value="bbk.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView testReturnUser(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("p/login");
	}
	@RequestMapping(value="bbf.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView registerPage(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("p/register");
	}
	
	@RequestMapping(value="mainPage.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView mainPage(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView temp = new ModelAndView("p/mainPage");
		HttpSession session = request.getSession();
		temp.addObject("user", session.getAttribute("user"));
		return temp;
	}
	//ÕýÊ½´úÂë
	@RequestMapping(value="Login",method={RequestMethod.POST,RequestMethod.GET})
	public void login(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    JSONObject ret=new JSONObject();
	    //ÓÃ»§Ãû,ÔÝÓÃÓÊÏä
		String email= request.getParameter("userName");
		//ÃÜÂë
		String password=request.getParameter("password");
		User existUser=userService.getUserByEmail(email);
		if (existUser!=null) {
			String pwd = existUser.getPassword();
			if(SecurityUtil.passwordsMatch(password, pwd)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", existUser);
				
				ret.put("Response", "µÇÂ¼³É¹¦");
				ret.put("Status", "success");
				ret.put("url", "mainPage.htm");
			} else {
				ret.put("Response", "ÃÜÂë´íÎó");
				ret.put("Status", "fail");
			}
		} else {
			ret.put("Response", "ÕËºÅ´íÎó");
			ret.put("Status", "fail");
		}
		response.getWriter().write(ret.toString());
	}
	
	
	
	@RequestMapping(value="Register",method={RequestMethod.POST,RequestMethod.GET})
	public void register(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException 
	{
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8"); 
		JSONObject ret=new JSONObject();
		try {
		    String name = request.getParameter("nickName");
		    String password = request.getParameter("password");
			String email = request.getParameter("email");
			String sex = request.getParameter("sex");
			String address = request.getParameter("address");
			String school = request.getParameter("schoolAddress");
			String tempIsTeacher = request.getParameter("isTeacher");
			String key = request.getParameter("key");
			
			User user = new User();
			user.setEmail(email);
			user.setPassword(SecurityUtil.encryptPassword(password));
			user.setSex(sex);
			user.setName(name);
			user.setAddress(address);
			user.setSchool(school);
			user.setRegister_date(new Date());
			
			if(userService.getUserByEmail(email) == null && key.equals("a12adsfa132zsds43qa")){
				userService.addUser(user);
				User newUser = userService.getUserByEmail(email);
				ret.put("Status", "success");
				ret.put("UserID", newUser.getUid());
				ret.put("info", "×¢²á³É¹¦");
				ret.put("url", "bbk.htm");
			} else {
				ret.put("Status", "fail");
				ret.put("info", "×¢²áÊ§°Ü");
				ret.put("UserID", 0);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("Status", "fail");
			ret.put("info", "×¢²áÊ§°Ü");
			ret.put("UserID", 0);
		}
		response.getWriter().write(ret.toString());
	}
	
	@RequestMapping(value="Logout",method={RequestMethod.POST,RequestMethod.GET})
	public void Logout(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    try {
	    	HttpSession session = request.getSession(true);
			session.removeAttribute("user");
			response.sendRedirect("bbk.htm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
