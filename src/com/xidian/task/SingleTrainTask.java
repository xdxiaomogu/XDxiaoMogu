package com.xidian.task;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.xidian.forms.SingleInfo;
import com.xidian.service.api.SingleInfoService;

@Component
public class SingleTrainTask {
	
	@Resource(name="singleInfoServiceImpl")
	private SingleInfoService singleInfoService;
	
	//@Scheduled(cron="0 0 3 * * ? ")//@Scheduled(cron="0/5 * *  * * ? ")
	public void uploadAndTrain(){
		System.out.println("begin to train!");
		List<SingleInfo> singleInfoList = singleInfoService.getSingleInfo();
		HttpRequests httpRequests = new HttpRequests("57e0562a7461033fb24872342e230fe8", "5SlgigACZyugOXR9DTnRPIOuemvhCzLH ", true, true);
		JSONObject result = null;
		for(SingleInfo singleInfo:singleInfoList){
			if(singleInfo.getTrain() == 0){
				String group_name = "";
				if(singleInfo.getGender().equals("boy")){
					group_name = "boys";
				}
				else{
					group_name = "girls";
				}
				if(singleInfo.getPhotocompress() == null || singleInfo.getPhotocompress() == ""){
					continue;
				}
				String imagePath = System.getProperty("XidianLife.root")+singleInfo.getPhotocompress();
				try {
					result = httpRequests.detectionDetect(new PostParameters().setImg(new File(imagePath)));
					JSONObject info = httpRequests.groupGetInfo(new PostParameters().setGroupName(group_name));
					System.out.println("person:"+ info.getJSONArray("person").length());
					System.out.println(httpRequests.personCreate(new PostParameters().setGroupName(group_name).setPersonName(Integer.toString(singleInfo.getUuid()))));
					System.out.println(httpRequests.personAddFace(new PostParameters().setGroupName(group_name).setPersonName(Integer.toString(singleInfo.getUuid())).setFaceId(
							result.getJSONArray("face").getJSONObject(0).getString("face_id"))));
					System.out.println("\ntrain/Identify");
					JSONObject syncRet = null; 
					syncRet = httpRequests.trainIdentify(new PostParameters().setGroupName(group_name));
					System.out.println(syncRet);
					System.out.println(httpRequests.getSessionSync(syncRet.getString("session_id")));
					singleInfo.setTrain(1);
					singleInfoService.updateSingleInfo(singleInfo);
					info = httpRequests.groupGetInfo(new PostParameters().setGroupName(group_name));
					System.out.println("person:"+ info.getJSONArray("person").length());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
