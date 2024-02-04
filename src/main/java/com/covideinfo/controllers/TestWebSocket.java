package com.covideinfo.controllers;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/testWebSocket")
public class TestWebSocket {
	
	
	public static Map<String, SseEmitter> doseTimes = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> dosingMap = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> dosingSuperwise = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> sampleCollection = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> sampleSaparation = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> mealCollectionStart = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> mealCollectionEnd = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> vitalCollectionStart = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> vitalCollectionEnd = new HashMap<String, SseEmitter>();	
	public static Map<String, SseEmitter> ecgCollectionDashStart = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> ecgCollectionDashEnd = new HashMap<String, SseEmitter>();
	
	public static Map<String, SseEmitter> vitalCollectionData = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> sampleCollectionData = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> dosingCollectionData = new HashMap<String, SseEmitter>();
	public static Map<String, SseEmitter> mealsCollectionData = new HashMap<String, SseEmitter>();
	
	
	public static Map<String, SseEmitter> emittersMap = new HashMap<String, SseEmitter>();
	
//	public static List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String testWebSocket0(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		return "testWebSocket.tiles";
	}
	@CrossOrigin
	@RequestMapping(value="/connect", method=RequestMethod.GET)
	public SseEmitter testWebSocket(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("INIT"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(emittersMap.containsKey(userName))
			emittersMap.remove(sseEmitter);
		emittersMap.put(userName, sseEmitter);
//		sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
//		emitters.add(sseEmitter);
//		request.getSession().setAttribute("sampleDashBoard", emitters);
		return sseEmitter;
	}
	
	@CrossOrigin
	@RequestMapping(value="/sampleCollection", method=RequestMethod.GET)
	public SseEmitter sampleCollection(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("sampleCollection"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sampleCollection.containsKey(userName))
			sampleCollection.remove(sseEmitter);
		sampleCollection.put(userName, sseEmitter);
//		sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
//		emitters.add(sseEmitter);
//		request.getSession().setAttribute("sampleDashBoard", emitters);
		return sseEmitter;
	}
	
	@CrossOrigin
	@RequestMapping(value="/dosingSuperwise", method=RequestMethod.GET)
	public SseEmitter dosingSuperwise(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("dosingSuperwise"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dosingSuperwise.containsKey(userName))
			dosingSuperwise.remove(sseEmitter);
		dosingSuperwise.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/dosingDashBorad", method=RequestMethod.GET)
	public SseEmitter dosingDashBorad(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("doseTimes"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(doseTimes.containsKey(userName))
			doseTimes.remove(sseEmitter);
		doseTimes.put(userName, sseEmitter);
		return sseEmitter;
	}
	

	@CrossOrigin
	@RequestMapping(value="/dosingAfterSave", method=RequestMethod.GET)
	public SseEmitter dosing(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("dosingMap"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dosingMap.containsKey(userName))
			dosingMap.remove(sseEmitter);
		dosingMap.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@RequestMapping(value="/sampleSaparation", method=RequestMethod.GET)
	public SseEmitter sampleSeparation(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("sampleSaparation"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sampleSaparation.containsKey(userName))
			sampleSaparation.remove(sseEmitter);
		sampleSaparation.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@RequestMapping(value="/mealCollectionStart", method=RequestMethod.GET)
	public SseEmitter mealCollection(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("mealCollectionStart"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(mealCollectionStart.containsKey(userName))
			mealCollectionStart.remove(sseEmitter);
		mealCollectionStart.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@RequestMapping(value="/mealCollectionEnd", method=RequestMethod.GET)
	public SseEmitter mealCollectionEnd(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("mealCollectionEnd"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(mealCollectionEnd.containsKey(userName))
			mealCollectionEnd.remove(sseEmitter);
		mealCollectionEnd.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@RequestMapping(value="/vitalCollectionStart", method=RequestMethod.GET)
	public SseEmitter vitalCollectionStart(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("vitalCollectionStart"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(vitalCollectionStart.containsKey(userName))
			vitalCollectionStart.remove(sseEmitter);
		vitalCollectionStart.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@RequestMapping(value="/vitalCollectionEnd", method=RequestMethod.GET)
	public SseEmitter vitalCollectionEnd(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("vitalCollectionEnd"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(vitalCollectionEnd.containsKey(userName))
			vitalCollectionEnd.remove(sseEmitter);
		vitalCollectionEnd.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@RequestMapping(value="/ecgCollectionDashStart", method=RequestMethod.GET)
	public SseEmitter ecgCollectionDashStart(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("ecgCollectionDashStart"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ecgCollectionDashStart.containsKey(userName))
			ecgCollectionDashStart.remove(sseEmitter);
		ecgCollectionDashStart.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@RequestMapping(value="/ecgCollectionDashEnd", method=RequestMethod.GET)
	public SseEmitter ecgCollectionDashEnd(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("ecgCollectionDashEnd"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ecgCollectionDashEnd.containsKey(userName))
			ecgCollectionDashEnd.remove(sseEmitter);
		ecgCollectionDashEnd.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	
	
	
//	@RequestMapping(value="/ss", method=RequestMethod.GET)
//	public void dispathEventToClients() {
//		Map<String, String> s = new HashedMap();
//		s.put("title", "1000");
//		s.put("text", "1002");
//		s.put("text1", "1003");
//		String json = "";
//		try {
//			json = new ObjectMapper().writeValueAsString(s);
//		} catch (JsonProcessingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		for(Map.Entry<String, SseEmitter> emitters : emittersMap.entrySet()){
//			SseEmitter emitter = emitters.getValue();
//			try {
//				emitter.send(SseEmitter.event().name("latestNews").data(json));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				emittersMap.remove(emitters.getKey());
//			}
//		}
////		return "testWebSocket.tiles";
//	}
	
	
	@CrossOrigin
	@RequestMapping(value="/vitalCollectionData", method=RequestMethod.GET)
	public SseEmitter vitalCollectionData(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.onCompletion(() -> vitalCollectionData.remove(userName));
			sseEmitter.send(SseEmitter.event().name("rtcvitalCollectionData"));
		} catch (IOException e) {
			e.printStackTrace();
			sseEmitter.complete();
			vitalCollectionData.remove(userName);
		}
		if(vitalCollectionData.containsKey(userName))
			vitalCollectionData.remove(userName);
		vitalCollectionData.put(userName, sseEmitter);
		return sseEmitter;
	}
	
	@CrossOrigin
	@RequestMapping(value="/sampleCollectionData", method=RequestMethod.GET)
	public SseEmitter sampleCollectionData(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.onCompletion(() -> sampleCollectionData.remove(userName));
			sseEmitter.send(SseEmitter.event().name("rtcSampleCollectionData"));
		} catch (IOException e) {
			e.printStackTrace();
			sseEmitter.complete();
			sampleCollectionData.remove(userName);
		}
		if(sampleCollectionData.containsKey(userName))
			sampleCollectionData.remove(userName);
		sampleCollectionData.put(userName, sseEmitter);
		return sseEmitter;
	}
	@CrossOrigin
	@RequestMapping(value="/dosingCollection", method=RequestMethod.GET)
	public SseEmitter dosingCollection(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.onCompletion(() -> dosingCollectionData.remove(userName));
			sseEmitter.send(SseEmitter.event().name("rtcDosingCollectionData"));
		} catch (IOException e) {
			e.printStackTrace();
			sseEmitter.complete();
			dosingCollectionData.remove(userName);
		}
		if(dosingCollectionData.containsKey(userName))
			dosingCollectionData.remove(userName);
		dosingCollectionData.put(userName, sseEmitter);
		return sseEmitter;
	}
	@CrossOrigin
	@RequestMapping(value="/mealsCollectionSocket", method=RequestMethod.GET)
	public SseEmitter mealsCollectionSocket(HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("userName");
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.onCompletion(() -> mealsCollectionData.remove(userName));
			sseEmitter.send(SseEmitter.event().name("rtcMealsCollectionData"));
		} catch (IOException e) {
			e.printStackTrace();
			sseEmitter.complete();
			mealsCollectionData.remove(userName);
		}
		if(mealsCollectionData.containsKey(userName))
			mealsCollectionData.remove(userName);
		mealsCollectionData.put(userName, sseEmitter);
		return sseEmitter;
	}
}
