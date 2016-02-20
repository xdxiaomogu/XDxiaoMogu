package com.xidian.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.xidian.forms.Ranker;
import com.xidian.forms.SingleInfo;
import com.xidian.service.api.RankerService;
import com.xidian.service.api.SingleInfoService;
import com.xidian.util.CompressPic;
import java.math.*;
@Controller
public class SingleActivityController {
	public HttpRequests httpRequests = new HttpRequests("57e0562a7461033fb24872342e230fe8", "5SlgigACZyugOXR9DTnRPIOuemvhCzLH ", true, true);
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="singleInfoServiceImpl")
	private SingleInfoService singleInfoService;
	@Resource(name="rankerServiceImpl")
	private RankerService rankerService;
	
	@RequestMapping(value="error")
	public String errorTest(Model model){
		System.out.println(System.getProperty("XidianLife.root"));
		model.addAttribute("jumpUrl", "singleActivity");
		model.addAttribute("errorInfo", "错误信息");
		return "p/error";
	}
	
	@RequestMapping(value={"signSingleActivity","uploadPicture","viewSingleInfo"})
	public String activityFinish(Model model){
		model.addAttribute("errorInfo","该活动已经结束。正在跳转，请稍后");
		model.addAttribute("jumpUrl", "http://mp.weixin.qq.com/s?__biz=MzI1NjAxOTA5NQ==&mid=402077510&idx=1&sn=0429195dad0b20031d352814c2f683f1#rd");
		return "p/error";
	}
	@RequestMapping(value="singleActivity",method=RequestMethod.GET)
	public String singleActivity(){
		//System.out.println("success");
		return "p/singleActivity";
	}
	
	//@RequestMapping(value="signSingleActivity",method=RequestMethod.GET)
	public String signSingleActivity(Model model){
		model.addAttribute(new SingleInfo());
		//System.out.println("success");
		return "p/signSingleActivity";
	}
	//@RequestMapping(value="signSingleActivity",method=RequestMethod.POST)
	public String uploadPicture1(SingleInfo singleInfo,Model model) throws UnsupportedEncodingException{
		singleInfo.setTrain(0);
		System.out.println(singleInfo);
		singleInfoService.addSingleInfo(singleInfo);
		System.out.println(singleInfo.getUuid());
		model.addAttribute("Uuid", singleInfo.getUuid());
		return "p/uploadSingleActivity";
	}
	//@RequestMapping(value={"uploadPicture","viewSingleInfo"},method=RequestMethod.GET)
	public String clickRediret(Model model){
		model.addAttribute("errorInfo","正在跳转，请稍后");
		model.addAttribute("jumpUrl", "http://mp.weixin.qq.com/s?__biz=MzI1NjAxOTA5NQ==&mid=402077510&idx=1&sn=0429195dad0b20031d352814c2f683f1#rd");
		return "p/error";
	}
	
	//@RequestMapping(value="uploadPicture",method=RequestMethod.POST)
	public String uploadPicture2(@RequestParam(value="image") MultipartFile image, String Uuid, Model model){
		if(!validateImage(image)){
			model.addAttribute("errorInfo","请选择一个图片文件哦");
			model.addAttribute("jumpUrl", "singleActivity");
			return "p/error";
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(date);
		String imageXiangduiFileName = timestamp+"_"+Uuid+".jpg";
		String imageXiangduiDir = "/p/singlePicture/";
		String imageXiangdui = imageXiangduiDir + imageXiangduiFileName;
		String curRealPath = request.getSession().getServletContext().getRealPath("/");
		String imagePath = curRealPath+imageXiangdui;
		String imageCompressPath = curRealPath + imageXiangduiDir + "compress/" + imageXiangduiFileName;
		saveImage(imagePath, image);
		CompressPic compressPic = new CompressPic();
		compressPic.compressPic(curRealPath+imageXiangduiDir, curRealPath+imageXiangduiDir+"compress/", imageXiangduiFileName, imageXiangduiFileName);
		SingleInfo singleInfo = singleInfoService.getSingleInfoById(Integer.parseInt(Uuid));
		singleInfo.setPhotourl(imageXiangdui);
		singleInfo.setPhotocompress(imageXiangduiDir + "compress/" + imageXiangduiFileName);
		System.out.println(singleInfo);
		singleInfoService.updateSingleInfo(singleInfo);
		model.addAttribute("curPerson", singleInfo);
		//进行人脸匹配操作
		try {
			
			if(singleInfo.getTarget() == 0 || singleInfo.getTarget() == 1){
				String group_name="";
				if(singleInfo.getTarget() == 0)
					group_name = "girls";
				else
					group_name = "boys";
				
				JSONObject result = httpRequests.groupGetInfo(new PostParameters().setGroupName(group_name));
				System.out.println(result);
				int personLen = result.getJSONArray("person").length();
				System.out.println("person:"+ personLen);
				if(personLen < 1){
					return "p/singleActivityEmptyResult";
				}
				JSONObject matchResult = httpRequests.recognitionIdentify(new PostParameters().setGroupName(group_name).setImg(new File(imageCompressPath)));
				//System.out.println(matchResult);
				if(matchResult.getJSONArray("face").length()<1){
					model.addAttribute("errorInfo","要上传一样正脸、大头照哦");
					model.addAttribute("jumpUrl", "singleActivity");
					return "p/error";
				}
				JSONObject matchPerson = matchResult.getJSONArray("face").getJSONObject(0).getJSONArray("candidate").getJSONObject(0);
				int matchPersonUuid = matchPerson.getInt("person_name");
				Double matchPersonCon = matchPerson.getDouble("confidence");
				double matchDeal = Math.pow(matchPersonCon, 1.0/3.0)*30.0;
				System.out.println(matchDeal);
				int matchPersonConInt = Integer.parseInt(new java.text.DecimalFormat("0").format(matchDeal));
				System.out.println(matchPersonUuid+"   "+matchPersonCon+"   "+matchPersonConInt);
				if(matchPersonConInt < 50)
					return "p/lowResult";
				SingleInfo matchSingleInfo = singleInfoService.getSingleInfoById(matchPersonUuid);
				model.addAttribute("matchPerson", matchSingleInfo);
				model.addAttribute("singleId", matchSingleInfo.getUuid());
				model.addAttribute("confidence",matchPersonConInt);
				Ranker ranker = new Ranker();
				ranker.setAuserId(singleInfo.getUuid());
				ranker.setBuserId(matchSingleInfo.getUuid());
				ranker.setCountFollowers(0);
				ranker.setMatchs(matchPersonConInt);
				ranker.setCountFollowers(0);
				rankerService.addRanker(ranker);
				System.out.println(matchSingleInfo);
			}
			else{
				JSONObject result1 = httpRequests.groupGetInfo(new PostParameters().setGroupName("girls"));
				JSONObject result2 = httpRequests.groupGetInfo(new PostParameters().setGroupName("boys"));
				System.out.println("result1:"+result1+"result2"+result2);
				int personLen1 = result1.getJSONArray("person").length();
				int personLen2 = result2.getJSONArray("person").length();
				System.out.println("train person1:"+ personLen1 + "train person2:"+ personLen2);
				if(personLen1 < 1 || personLen2 < 1){
					return "p/singleActivityEmptyResult";
				}
				JSONObject matchResult1 = httpRequests.recognitionIdentify(new PostParameters().setGroupName("girls").setImg(new File(imageCompressPath)));
				JSONObject matchResult2 = httpRequests.recognitionIdentify(new PostParameters().setGroupName("boys").setImg(new File(imageCompressPath)));
				if(matchResult1.getJSONArray("face").length()<1 || matchResult2.getJSONArray("face").length()<1){
					model.addAttribute("errorInfo","要上传一样正脸、大头照哦");
					model.addAttribute("jumpUrl", "singleActivity");
					return "p/error";
				}
				JSONObject matchPerson1 = matchResult1.getJSONArray("face").getJSONObject(0).getJSONArray("candidate").getJSONObject(0);
				JSONObject matchPerson2 = matchResult2.getJSONArray("face").getJSONObject(0).getJSONArray("candidate").getJSONObject(0);
				int matchPersonUuid = 0;
				Double matchPersonCon = 0.0;
				Double matchPersonCon1 = matchPerson1.getDouble("confidence");
				Double matchPersonCon2 = matchPerson2.getDouble("confidence");
				System.out.println("con 1:"+matchPersonCon1+"2:"+matchPersonCon2);
				if(matchPersonCon1 > matchPersonCon2){
					matchPersonUuid = matchPerson1.getInt("person_name");
					matchPersonCon = matchPersonCon1;
				}
				else{
					matchPersonUuid = matchPerson2.getInt("person_name");
					matchPersonCon = matchPersonCon2;
				}
				double matchDeal = Math.pow(matchPersonCon, 1/3)*30;
				int matchPersonConInt = Integer.parseInt(new java.text.DecimalFormat("0").format(matchDeal));
				System.out.println(matchPersonUuid+"   "+matchPersonCon+"   "+matchPersonConInt);
				if(matchPersonConInt < 50)
					return "p/lowResult";
				SingleInfo matchSingleInfo = singleInfoService.getSingleInfoById(matchPersonUuid);
				model.addAttribute("matchPerson", matchSingleInfo);
				model.addAttribute("singleId", matchSingleInfo.getUuid());
				model.addAttribute("confidence",matchPersonConInt);
				Ranker ranker = new Ranker();
				ranker.setAuserId(singleInfo.getUuid());
				ranker.setBuserId(matchSingleInfo.getUuid());
				ranker.setCountFollowers(0);
				ranker.setMatchs(matchPersonConInt);
				ranker.setCountFollowers(0);
				rankerService.addRanker(ranker);
				System.out.println(matchSingleInfo);
			}
		} catch (FaceppParseException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "p/singleActivityResult";
	}
	//@RequestMapping(value="viewSingleInfo",method=RequestMethod.POST)
	public String viewSingleInfo(String singleId,String matchPic,String thisPic,String con, Model model){
		SingleInfo matchSingleInfo = singleInfoService.getSingleInfoById(Integer.parseInt(singleId));
		model.addAttribute("matchPerson", matchSingleInfo);
		model.addAttribute("matchPic", matchPic);
		model.addAttribute("thisPic", thisPic);
		model.addAttribute("con", con);
		return "p/viewSingleInfo";
	}
	private boolean validateImage(MultipartFile image){
		System.out.println(image.getContentType());
		if(!image.getContentType().equals("image/jpeg") && !image.getContentType().equals("image/png") && !image.getContentType().equals("image/gif") && !image.getContentType().equals("image/bmp")){//问题
			return false;
		}
		return true;
	}
	private boolean saveImage(String filename, MultipartFile image){
		try{
			File file = new File(filename);
			FileUtils.writeByteArrayToFile(file, image.getBytes());
		}
		catch(IOException e){
			return false;
		}
		return true;
	}
}
