package com.xidian.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NoHttpResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.dao.api.GradeStatisticsDao;
import com.xidian.forms.GradeTemp;
import com.xidian.grade.Main;
import com.xidian.grade.UserInfo;
import com.xidian.mastergrade.MasterMain;
import com.xidian.mastergrade.MasterUserInfo;
import com.xidian.service.api.GradeTempService;
import com.xidian.util.QuerySecret;
import com.xidian.service.api.GradeStatisticsService;

@Controller
public class GradeController {
	@Resource(name = "gradeTempServiceImpl")
	GradeTempService gradeTempService;
	@Resource(name = "gradeStatisticsServiceImpl")
	GradeStatisticsService gradeStatisticsService;

	@RequestMapping(value = "queryGrade.htm", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView queryGrade(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "refresh", required = false) boolean refresh)
			throws IOException, ClassNotFoundException {
		ModelAndView tempView = new ModelAndView("p/queryGrade");
		try {
			Cookie[] cookies = request.getCookies();
			String username = null;
			String password = null;
			String gradeTest = null;
			String level = null;
			// count�����ж��û����������Ƿ���ڣ��Լ��ϴβ�ѯʱ���Ƿ��ѹ���
			int count = 0;
			// ����ѭ��ʱ�䣬��ʵ��ôд���Ǻܺã����������
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("usernameGrade")) {
						username = QuerySecret.deleteSecret(URLDecoder.decode(
								cookie.getValue(), "utf-8"));
						System.out.println(cookie.getValue());
						count++;
					}
					if (cookie.getName().equals("passwordGrade")) {
						password = QuerySecret.deleteSecret(URLDecoder.decode(
								cookie.getValue(), "utf-8"));
						System.out.println(cookie.getValue());
						count++;
					}
					if (cookie.getName().equals("gradeTest")) {
						gradeTest = cookie.getValue();
						count++;
					}
					if (cookie.getName().equals("level")) {
						level = cookie.getValue();
						count++;
					}
					if (count == 4) {
						break;
					}
				}
			}
			MasterUserInfo masterUserInfo = null;
			UserInfo userInfo = null;
			// �û����������,���ѹ���1Сʱ
			if (username != null && password != null && level != null
					&& (gradeTest == null || refresh)) {
				// System.out.println("������");
				if (level.equals("master")) {
					masterUserInfo = MasterMain.test(username, password);
					if (masterUserInfo == null) {
						gradeStatisticsService.addMasterNum();
						tempView.setViewName("p/queryGrade");
						tempView.addObject("status", "fail");
						tempView.addObject("info", "�û������������");
						
					} else {
						gradeStatisticsService.addMasterOkNum();
						tempView.setViewName("p/queryMasterGradePage");
						tempView.addObject("grade",
								masterUserInfo.getGradeInfo());
						tempView.addObject("userinfo",
								masterUserInfo.getUserDesc());
						// ���л�
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(
								byteArrayOutputStream);
						objectOutputStream.writeObject(masterUserInfo);
						String serStr = byteArrayOutputStream
								.toString("ISO-8859-1");
						serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
						objectOutputStream.close();
						byteArrayOutputStream.close();
						// д�����ݿ�
						GradeTemp gradeTemp = new GradeTemp();
						gradeTemp.setContent(serStr);
						gradeTemp.setUsername(username);
						gradeTempService.addGradeTemp(gradeTemp);
						// ����1Сʱ��Ч��������ʾˢ��ʱ��
						Date now = new Date();
						Cookie tempCookie = new Cookie("gradeTest",
								String.valueOf(now.getTime()));
						tempCookie.setMaxAge(3600);
						response.addCookie(tempCookie);
						tempView.addObject("hours", 0);
						tempView.addObject("minutes", 1);

						return tempView;
					}
				} else if (level.equals("bachelor")) {
					userInfo = Main.test(username, password);
					if (userInfo == null) {
						gradeStatisticsService.addBacherlorNum();
						tempView.setViewName("p/queryGrade");
						tempView.addObject("status", "fail");
						tempView.addObject("info", "�û������������");
						Cookie cookie = new Cookie("usernameGrade", null);
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						cookie = new Cookie("passwordGrade", null);
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						cookie = new Cookie("gradeTest", null);
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						cookie = new Cookie("level", null);
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					} else {
						gradeStatisticsService.addBacherlorOkNum();
						tempView.setViewName("p/queryGradePage");
						tempView.addObject("gradeOne",
								userInfo.getCurrentGradeInfos());
						tempView.addObject("gradeTwo",
								userInfo.getSemesterGrades());
						tempView.addObject("gradeThree",
								userInfo.getFailGradeInfo());
						tempView.addObject("userinfo", userInfo.getUserDesc());
						// ���л�
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(
								byteArrayOutputStream);
						objectOutputStream.writeObject(userInfo);
						String serStr = byteArrayOutputStream
								.toString("ISO-8859-1");
						serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
						objectOutputStream.close();
						byteArrayOutputStream.close();
						// д�����ݿ�
						GradeTemp gradeTemp = new GradeTemp();
						gradeTemp.setContent(serStr);
						gradeTemp.setUsername(username);
						gradeTempService.addGradeTemp(gradeTemp);
						// ����1Сʱ��Ч��������ʾˢ��ʱ��
						Date now = new Date();
						Cookie tempCookie = new Cookie("gradeTest",
								String.valueOf(now.getTime()));
						tempCookie.setMaxAge(3600);
						response.addCookie(tempCookie);
						tempView.addObject("hours", 0);
						tempView.addObject("minutes", 1);

						return tempView;
					}
				}

			} else if (username != null && password != null
					&& gradeTest != null && level != null) {
				System.out.println("success");
				// ����ˢ��ʱ��
				Date nowTime = new Date();
				long diff = nowTime.getTime() - Long.parseLong(gradeTest);
				System.out.println(diff);
				long hours = (diff) / (1000 * 60 * 60);
				long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
				tempView.addObject("hours", hours);
				tempView.addObject("minutes", minutes + 1);
				if (level.equals("master")) {
					// �����л�
					String redStr = java.net.URLDecoder
							.decode(gradeTempService.getGradeTempByUser(
									username).getContent(), "UTF-8");
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
							redStr.getBytes("ISO-8859-1"));
					ObjectInputStream objectInputStream = new ObjectInputStream(
							byteArrayInputStream);
					masterUserInfo = (MasterUserInfo) objectInputStream
							.readObject();
					objectInputStream.close();
					byteArrayInputStream.close();
					gradeStatisticsService.addMasterOkNum();
					tempView.setViewName("p/queryMasterGradePage");
					tempView.addObject("grade", masterUserInfo.getGradeInfo());
					tempView.addObject("userinfo", masterUserInfo.getUserDesc());
				} else if (level.equals("bachelor")) {
					// �����л�
					System.out.println(username);
					String redStr = java.net.URLDecoder
							.decode(gradeTempService.getGradeTempByUser(
									username).getContent(), "UTF-8");
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
							redStr.getBytes("ISO-8859-1"));
					ObjectInputStream objectInputStream = new ObjectInputStream(
							byteArrayInputStream);
					userInfo = (UserInfo) objectInputStream.readObject();
					objectInputStream.close();
					byteArrayInputStream.close();
					gradeStatisticsService.addBacherlorOkNum();
					tempView.setViewName("p/queryGradePage");
					tempView.addObject("gradeOne",
							userInfo.getCurrentGradeInfos());
					tempView.addObject("gradeTwo", userInfo.getSemesterGrades());
					tempView.addObject("gradeThree",
							userInfo.getFailGradeInfo());
					tempView.addObject("userinfo", userInfo.getUserDesc());
				}
				return tempView;
			}
			return tempView;

		} catch (NoHttpResponseException nhe) {
			tempView.addObject("status", "fail");
			tempView.addObject("info", "ѧУ�ɼ���ѯϵͳ�޷����ӣ�");
			tempView.setViewName("p/queryGrade");
			Cookie cookie = new Cookie("usernameGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("passwordGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("gradeTest", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("level", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return tempView;
		} catch (Exception e) {
			tempView.setViewName("p/queryGrade");
			tempView.addObject("status", "fail");
			tempView.addObject("info", "��Ǹ�����Ժ����ԣ���ֱ��ͨ��΢����СĢ����ϵ��");
			Cookie cookie = new Cookie("usernameGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("passwordGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("gradeTest", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("level", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return tempView;
		}

	}

	@RequestMapping(value = "queryGrade", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView queryGradeAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// �״ε�½
		ModelAndView tempView = new ModelAndView("p/queryGradePage");

		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Cookie userCookie = new Cookie("usernameGrade", URLEncoder.encode(
					QuerySecret.addSecret(username), "utf-8"));
			Cookie passCookie = new Cookie("passwordGrade", URLEncoder.encode(
					QuerySecret.addSecret(password), "utf-8"));
			UserInfo userInfo = Main.test(username, password);
			if (userInfo == null) {
				gradeStatisticsService.addBacherlorNum();
				tempView.setViewName("p/queryGrade");
				tempView.addObject("status", "fail");
				tempView.addObject("info", "�û������������");
			} else {
				gradeStatisticsService.addBacherlorOkNum();
				// ��־ʱ��
				Date now = new Date();
				Cookie tempCookie = new Cookie("gradeTest", String.valueOf(now
						.getTime()));
				tempCookie.setMaxAge(3600);
				response.addCookie(tempCookie);
				tempView.addObject("hours", 0);
				tempView.addObject("minutes", 1);
				// �����о������ֱ�־
				tempCookie = new Cookie("level", "bachelor");
				tempCookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(tempCookie);

				// �û�������д��cookie
				userCookie.setMaxAge(Integer.MAX_VALUE);
				passCookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(userCookie);
				response.addCookie(passCookie);
				tempView.addObject("userinfo", userInfo.getUserDesc());
				tempView.addObject("gradeOne", userInfo.getCurrentGradeInfos());
				tempView.addObject("gradeTwo", userInfo.getSemesterGrades());
				tempView.addObject("gradeThree", userInfo.getFailGradeInfo());
				// ���л�
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						byteArrayOutputStream);
				objectOutputStream.writeObject(userInfo);
				String serStr = byteArrayOutputStream.toString("ISO-8859-1");
				serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
				objectOutputStream.close();
				byteArrayOutputStream.close();
				// д�����ݿ�
				GradeTemp gradeTemp = new GradeTemp();
				gradeTemp.setContent(serStr);
				gradeTemp.setUsername(username);
				gradeTempService.addGradeTemp(gradeTemp);
			}
			return tempView;

		} catch (NoHttpResponseException nhe) {
			tempView.addObject("status", "fail");
			tempView.addObject("info", "ѧУ�ɼ���ѯϵͳ�޷����ӣ�");
			tempView.setViewName("p/queryGrade");
			Cookie cookie = new Cookie("usernameGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("passwordGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("gradeTest", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("level", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return tempView;
		} catch (Exception e) {
			tempView.setViewName("p/queryGrade");
			tempView.addObject("status", "fail");
			tempView.addObject("info", "��Ǹ�����Ժ����ԣ���ֱ��ͨ��΢����СĢ����ϵ��");
			Cookie cookie = new Cookie("usernameGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("passwordGrade", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("gradeTest", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("level", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return tempView;
		}
	}

	@RequestMapping(value = "queryGradeOut", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView queryGradeOut(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView tempView = new ModelAndView("p/queryGrade");
		// ��cookie
		Cookie cookie = new Cookie("usernameGrade", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("passwordGrade", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("gradeTest", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		cookie = new Cookie("level", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return tempView;
	}
}
