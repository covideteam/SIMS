package com.covideinfo.util;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.covideinfo.configuration.JdbcCollection;

public class SidemeusXmlFile {
	
	
	/*
	 * This method reading the existing xml file from real path and getting all menus and action url
	 * And Getting Login user Role contains side menu ids are also getting in this method
	 * Based on Current action url is getting id of total side menus map that id exists current Role sesssion sidmenu ids allowing access and retrun true other wise return false
	 * And if Xml does not contains Action url then return true
	 */

	public static boolean readSidemeusXmlFile(HttpServletRequest request, String path, Long sideMenuCount) {
			boolean flag = false;
			Map<String, List<Long>> smenusMap = new HashMap<>(); //Stores Action Name and List of Side menu Ids
			Map<Long, String> linkMap = new HashMap<>();
			Map<String, Long> nameIdsMap = new HashMap<>();
			List<Long> tempList = null;
			String realPath = request.getServletContext().getRealPath("/");
			String fileName = realPath+"\\xmlFile.xml";
			File file = new File(fileName);  
			try {
				if(file.exists()) {
					File xmlFile = new File(fileName);
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(xmlFile);
					doc.getDocumentElement();
					NamedNodeMap nodeMap = doc.getAttributes();
					NodeList nodeList =  doc.getChildNodes();
					for(int j=0; j<nodeList.getLength(); j++) {
						Node node = nodeList.item(j);
						String nodeName  = node.getNodeName();
						NodeList subNodeList =  node.getChildNodes();
					    for(int k=0; k<subNodeList.getLength(); k++) {
					    	Node node2 = subNodeList.item(k);
					    	String nodeName2  = node2.getNodeName();
					    	NodeList sList =  node2.getChildNodes();
					    	for(int l=0; l<sList.getLength(); l++) {
					    		if(nodeName2.equals("URLNAMES")) {
					    			Node urlNameNode = sList.item(l);
						    		String urlNodeName  = urlNameNode.getNodeName();
						    		NodeList urlNameList = urlNameNode.getChildNodes();
						    		for(int s=0; s<urlNameList.getLength(); s++) {
						    			Node nameNode = urlNameList.item(s);
						    			String[] nameArr = nameNode.getNodeValue().split("\\,");
						    			if(nameArr.length > 0) {
						    				List<Long> idsList = smenusMap.get(urlNodeName);
						    				if(idsList.size() > 0) {
						    					for(int r=0; r<idsList.size(); r++) {
						    						linkMap.put(idsList.get(r), nameArr[r]);
						    						nameIdsMap.put(nameArr[r], idsList.get(r));
						    					}
						    				}
						    			}
						    		}
					    		}else {
					    			Node snode = sList.item(l);
						    		String snodeName  = snode.getNodeName();
						    		NodeList linkNodeList = snode.getChildNodes();
						    		for(int s=0; s<linkNodeList.getLength(); s++) {
						    			Node lnode = linkNodeList.item(s);
						    			String[] link = lnode.getNodeValue().split("\\,");
						    			if(link.length > 0) {
						    				for(String st : link) {
						    					if(st != null && !st.equals("")) {
						    						if(smenusMap.containsKey(snodeName)) {
						    							tempList = smenusMap.get(snodeName);
						    							tempList.add(Long.parseLong(st));
						    							smenusMap.put(snodeName, tempList);
						    						}else {
						    							tempList = new ArrayList<>();
						    							tempList.add(Long.parseLong(st));
						    							smenusMap.put(snodeName, tempList);
						    						}
						    					}
						    						
						    				}
						    			}
						    		}
					    		}
					    	}
						}
					}
				}
				List<Long> sessionIds = new ArrayList<>();
				HttpSession session =request.getSession();
				if(session != null) {
					for(int t=1; t<=sideMenuCount; t++) {
//						System.out.println("t value is :"+t);
						if(session.getAttribute("SMenu"+t) != null) {
							if(!sessionIds.contains(Long.parseLong(t+"")))
								sessionIds.add(Long.parseLong(t+""));
						}
					}
				}
				Long pathId = nameIdsMap.get(path);
				if(pathId != null) {
					if(!sessionIds.contains(pathId))
						flag = false;
					else flag =true;
				}else flag = true;
				
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
			return flag;
		}
	
	/*
	 * Creating Xml File in RealPath Once This file is created Not executing this function
	 * If you want to update this XML File Remove this file from Real path then it will create file again
	 * 
	 */
	public static void createApplicationMenu(FilterConfig config,String patha) {
		try {
			String path=null;
			if(config!=null) {
			  path = config.getServletContext().getRealPath("/");
			}else {
				path=patha;
			}
			 String fileName = path+"\\xmlFile.xml";
		     File file = new File(fileName);
		     Connection con = null;
		     Statement stm = null;
		     String query = "";
		     ResultSet rs = null;
		     Map<Long, List<Long>> smMap = new HashMap<>();
		     Map<Long, String> urlNameMap = new HashMap<>();
		     List<Long> tempList = null;
		     if(!file.exists()) {
	    		if (file.createNewFile()) {
	    			con = new JdbcCollection().getJdbcConnection();
	    			stm = con.createStatement();
	    			if(con != null) {
	    				query = "SELECT id, app_menu, controller_name FROM bedc.app_side_menus";
	    				rs = stm.executeQuery(query);
	    				while(rs.next()) {
	    					Long id = rs.getLong("id");
	    					Long appMenu = rs.getLong("app_menu");
	    					String controlName = rs.getString("controller_name");
	    					if(appMenu != null && (controlName != null && !controlName.equals("")) ) {
	    						urlNameMap.put(id, controlName);
	    						if(smMap.containsKey(appMenu)) {
	    							tempList = smMap.get(appMenu);
	    							tempList.add(id);
	    							smMap.put(appMenu, tempList);
	    						}else {
	    							tempList = new ArrayList<>();
	    							tempList.add(id);
	    							smMap.put(appMenu, tempList);
	    						}
	    					}
	    				}
	    				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		    	        // root elements
		    	        Document doc = docBuilder.newDocument();
		    	        Element rootElement = doc.createElement("URLLINKS");
		    	        doc.appendChild(rootElement);
		    	        Element sideMenus = doc.createElement("SIDEMENUS");
		    	        Element urlNamesElement = doc.createElement("URLNAMES");
		    	        Element smenu = null;
		    	        Element urlName = null;
		    	        for(Map.Entry<Long, List<Long>> map : smMap.entrySet()) {
		    	        	String str = "";
		    	        	String nameStr = "";
		    	        	smenu = doc.createElement("Smenu"+map.getKey());
		    	        	urlName = doc.createElement("Smenu"+map.getKey());
		    	        	List<Long> smIdsList = map.getValue();
		    	        	for(int i =0; i<smIdsList.size(); i++) {
		    	        		if(str.equals(""))
			    	        		str = smIdsList.get(i)+"";
			    	        	else str = str +","+smIdsList.get(i);
		    	        		
		    	        		if(nameStr.equals(""))
		    	        			nameStr = urlNameMap.get(smIdsList.get(i));
			    	        	else nameStr = nameStr +","+urlNameMap.get(smIdsList.get(i));
		    	        	}
		    	        	smenu.appendChild(doc.createTextNode(str));
		    	        	sideMenus.appendChild(smenu);
		    	        	rootElement.appendChild(sideMenus);
		    	        	
		    	        	urlName.appendChild(doc.createTextNode(nameStr));
		    	        	urlNamesElement.appendChild(urlName);
		    	        	rootElement.appendChild(urlNamesElement);
		    	        	
		    	       }
		    	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    	        Transformer transformer = transformerFactory.newTransformer();
		    	        DOMSource source = new DOMSource(doc);
		    	        StreamResult result = new StreamResult(new File(fileName));
		    	        transformer.transform(source, result);
		    	      } 
	    	     }
		    }
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	}

}
