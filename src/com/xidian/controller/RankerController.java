package com.xidian.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.activity.RankerJson;
import com.xidian.forms.Ranker;
import com.xidian.forms.SingleInfo;
import com.xidian.forms.Votes;
import com.xidian.service.api.RankerService;
import com.xidian.service.api.SingleInfoService;
import com.xidian.service.api.VotesService;
import com.xidian.util.CookieTool;

@Controller
public class RankerController {
	@Resource(name = "rankerServiceImpl")
	RankerService rankerService;
	@Resource(name = "votesServiceImpl")
	VotesService votesService;
	@Resource(name = "singleInfoServiceImpl")
	SingleInfoService singleInfoService;
	@Autowired
	HttpServletRequest request;
	// 送鲜花或投票功能
	//@RequestMapping(value = "Vote", method = { RequestMethod.POST, RequestMethod.GET })
	public void vote(String rankerId , HttpServletResponse response) throws JSONException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSONObject ret = new JSONObject();
		// 投票给somebody
		try {
			Cookie voted = CookieTool.getCookieByName(request, "voted");
			if (voted == null) {
				//更新鲜花数目
				Integer tmpRankerId = Integer.parseInt(rankerId);
				Ranker tmpRanker = rankerService.getRankerById(tmpRankerId);
				Integer latestNum = tmpRanker.getCountFollowers() + 1;
				tmpRanker.setCountFollowers(latestNum);
				rankerService.updateRanker(tmpRanker);
				ret.put("Response", "送花已成功！！快去寻找你的ta吧");
				ret.put("Status", "success");
				CookieTool.addCookie(response, "voted", "true", 7200);
			} else {
				ret.put("Response", "不要太心急哦！！每次投票要间隔两个小时啦");
				ret.put("Status", "fail");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(ret.toString());
	}
	public void login(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSONObject ret = new JSONObject();
		// 身份标识，根据此字段保证只能投票一次
		String identity = request.getParameter("identity");
		// 投票给somebody
		String rankerId = request.getParameter("rankerId");
		System.out.println("identity"+identity+" rankerId"+rankerId);
		try {
			Votes tmpItem = votesService.getVoteByIdentity(identity);
			if (tmpItem == null) {
				//新增一条投票记录
				Votes tmpVote=new Votes();
				tmpVote.setIdentity(identity);
				tmpVote.setRankerId(Integer.parseInt(rankerId));
				tmpVote.setCreateAt(new Date());//获取当前日期
				votesService.addVote(tmpVote);
				//更新鲜花数目
				Integer tmpRankerId = Integer.parseInt(rankerId);
				Ranker tmpRanker = rankerService.getRankerById(tmpRankerId);
				Integer latestNum = tmpRanker.getCountFollowers() + 1;
				tmpRanker.setCountFollowers(latestNum);
				rankerService.updateRanker(tmpRanker);
				ret.put("Response", "送花成功");
				ret.put("Status", "success");
			} else {
				ret.put("Response", "您已经送过鲜花了。。。");
				ret.put("Status", "fail");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(ret.toString());
	}
	@RequestMapping(value = "displayRanker")
	public ModelAndView stuSearchCards() {
		ModelAndView tempView = new ModelAndView("p/displayRanker");
		List<Ranker> tempList = new ArrayList<Ranker>();
		tempList = rankerService.getRankerList(85);
		List<Ranker> coupleList = new LinkedList<Ranker>();
		for(Ranker ranker:tempList){
			Integer a = ranker.getAuserId();
			Integer b = ranker.getBuserId();
			SingleInfo singlePersonA = singleInfoService.getSingleInfoById(a);
			if(singlePersonA == null)
				continue;
			SingleInfo singlePersonB = singleInfoService.getSingleInfoById(b);
			if(singlePersonB == null)
				continue;
			ranker.setaPhoto(singlePersonA.getPhotocompress());
			ranker.setaName(singlePersonA.getName());
			ranker.setbPhoto(singlePersonB.getPhotocompress());
			ranker.setbName(singlePersonB.getName());
			//System.out.println(ranker);
			coupleList.add(ranker);
		}
		tempView.addObject("coupleList", coupleList);
		return tempView;
	}
	//@RequestMapping(value="deleteCookie")
	public void deleteCookie(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Cookie voted = CookieTool.getCookieByName(request, "voted");
		if(voted != null){
			System.out.println("cookie 为"+voted.getValue());
			Cookie temp = new Cookie("voted",null);
			temp.setMaxAge(0);
			temp.setPath("/");
			response.addCookie(temp);
		}
		else{
			System.out.println("cookie 为空");
		}
		response.getWriter().write("成功");
	}
}
