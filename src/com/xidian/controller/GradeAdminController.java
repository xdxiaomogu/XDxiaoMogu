package com.xidian.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xidian.forms.GradeStatistics;
import com.xidian.service.api.GradeStatisticsService;

@Controller
public class GradeAdminController {
	@Resource(name="gradeStatisticsServiceImpl")
	GradeStatisticsService gradeStatisticsService;
	
	@RequestMapping("showGradeStatistic")
	public String showGradeStatistic(Model model){
		List<GradeStatistics> gradeList = gradeStatisticsService.getGradeStatistics();
		model.addAttribute("gradeList", gradeList);
		return "p/showGradeStatistic";
	}
}
