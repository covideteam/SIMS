package com.covideinfo.controllers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.Projects;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.StudyDesignService;
import com.covideinfo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/testController")
public class TestController {
	@Autowired
	StudyDesignService studyDesignService;
	@Autowired
	UserService userService;
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveStudyInfo/{testNo}", method = RequestMethod.GET)
	public String saveStudyInfo(@PathVariable("testNo") int testNo, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) {
		Enumeration<String> attributes = request.getSession().getAttributeNames();
		while (attributes.hasMoreElements()) {
			String attribute = (String) attributes.nextElement();
			System.out.println(attribute + " : " + request.getSession().getAttribute(attribute));
		}

		System.out.println(request.getSession().getAttribute("userId"));
		System.out.println(request.getSession().getAttribute("userName"));
		System.out.println(request.getSession().getAttribute("uid"));
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		UserMaster user = userService.findById(userId);
		Projects project = studyDesignService.testSaveStudyInfo(testNo, user);
		return "redirect:/administration/";
	}

	@RequestMapping(value = "/testDose/{testNo}", method = RequestMethod.GET)
	public String testDose(@PathVariable("testNo") int testNo, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) {
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		UserMaster user = userService.findById(userId);
		try {

			StringBuffer sb = new StringBuffer();
			sb.append("<div id='div_03000002100100000000100'>");
			sb.append("<table border='1'>");
			sb.append("<tr><th>").append("Subject").append("</th>").append("<td>").append("01").append("</td></tr>");
			sb.append("<tr><th>").append("Dose Time").append("</th>").append("<td>").append("15:16")
					.append("</td></tr>");
			sb.append("<tr><th>").append("Dose Date").append("</th>").append("<td>").append("10-03-2022")
					.append("</td></tr>");
			sb.append("<tr><th>").append("Done By").append("</th>").append("<td>").append("10-03-2022")
					.append("</td></tr>");
			StringBuffer sb1 = new StringBuffer();
			for (int i = 1; i <= testNo; i++) {
				sb1.append("<tr><th>").append("lablelablelablelablelable" + i).append("</th>").append("<td>")
						.append("i").append("</td></tr>");
			}
			sb.append(sb1.toString());
			sb.append(
					"<tr><td colspan='2'><input type='button' value='Save' onclick=\"saveDoseSupervise(\'03000002100100000000100\')\"/>")
					.append("</td>");
			sb.append("</table><div>");

			Map<String, String> s = new HashedMap();
			s.put("id", testNo + "");
			s.put("data", sb.toString());
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(s);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, SseEmitter> map = TestWebSocket.dosingSuperwise;
			for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
				SseEmitter emitter = emitters.getValue();
				try {
					emitter.send(SseEmitter.event().name("dosingSuperwise").data(json));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					TestWebSocket.dosingSuperwise.remove(user.getUsername());
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/administration/";
	}

	@RequestMapping(value = "/saveDoseSuperviseInfoTest/{id}", method = RequestMethod.GET)
	public String testDose(@PathVariable("id") String id, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) {
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		UserMaster user = userService.findById(userId);
		try {
			Map<String, String> s = new HashedMap();
			s.put("id", id);
			s.put("data", "15:16");
			s.put("color", "blue");
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(s);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, SseEmitter> map = TestWebSocket.dosingMap;
			for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
				SseEmitter emitter = emitters.getValue();
				try {
					emitter.send(SseEmitter.event().name("dosingMap").data(json));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					TestWebSocket.dosingMap.remove(user.getUsername());
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/administration/";
	}
	
}
