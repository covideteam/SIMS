package com.covideinfo.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.covideinfo.controllers.TestWebSocket;
import com.covideinfo.dto.CpuOnlineData;

public class CorsConfigurationProcess {

	/**
	 * To Send Data to all Users of registered service
	 * @param data
	 */
	public void sendDataToClinets(CpuOnlineData data) {
		try {
			Map<String, SseEmitter> map = userSoctets(data.getServiceName());// TestWebSocket.sampleCollection;
			for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
				SseEmitter emitter = emitters.getValue();
				try {
					emitter.send(SseEmitter.event().name(data.getServiceName()).data(data.getJsonData()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To all all Users of registered service of Paricular name
	 */
	
	private Map<String, SseEmitter> userSoctets(String serviceName) {
		switch (serviceName) {
		case "rtcvitalCollectionData":
			return TestWebSocket.vitalCollectionData;
		case "rtcSampleCollectionData":
			return TestWebSocket.sampleCollectionData;
		case "rtcDosingCollectionData":
			return TestWebSocket.dosingCollectionData;
		case "rtcMealsCollectionData":
			return TestWebSocket.mealsCollectionData;
		default:
			return null;
		}

	}
}
