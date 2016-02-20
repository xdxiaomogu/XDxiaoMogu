package com.xidian.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xidian.weichat.SHA1;

@Controller
public class WeiChatController {
	@RequestMapping(value = "WeiChat", method = { RequestMethod.POST,RequestMethod.GET })
	public void testWeiChat(HttpServletRequest request,HttpServletResponse response) throws IOException {
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if (isGet) {
			// ΢�ż���ǩ��
			String signature = request.getParameter("signature");
			// ����ַ���
			String echostr = request.getParameter("echostr");
			// ʱ���
			String timestamp = request.getParameter("timestamp");
			// �����
			String nonce = request.getParameter("nonce");

			String TOKEN = new String("xd_mushroom");

			String[] str = { TOKEN, timestamp, nonce };
			Arrays.sort(str); // �ֵ�������
			String bigStr = str[0] + str[1] + str[2];
			// SHA1����
			String digest = new SHA1().getDigestOfString(bigStr.getBytes())
					.toLowerCase();

			// ȷ����������΢��
			if (digest.equals(signature)) {
				response.getWriter().print(echostr);
			}
		} else {
			// ����POST���촦��  
			try {  
                // ������Ϣ��������Ϣ  
                acceptMessage(request, response);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
		}
	}
	
	private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
		response.setCharacterEncoding("UTF-8");
		String str = new String("��ã���ӭ��������СĢ����");
		response.getWriter().write(str);  
    }  
}
