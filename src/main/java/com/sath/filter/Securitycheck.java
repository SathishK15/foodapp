package com.sath.filter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Securitycheck extends HttpFilter {
	private final Map<String, Set<String>> allowedUrls = new HashMap<>();

	public void init(FilterConfig fConfig) throws ServletException {

			
			 try {
				 String filePath = fConfig.getServletContext().getRealPath("/WEB-INF/security.xml");
				 System.out.println("Error: XML file not found at " + filePath);
		            File xmlFile = new File(filePath); 
		            System.out.println("Error: XML file not found at " + xmlFile);
		            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder builder = factory.newDocumentBuilder();
		            Document document = builder.parse(xmlFile);
		            document.getDocumentElement().normalize();

		            NodeList urlList = document.getElementsByTagName("url");
		            System.out.println("Total  Found: " + urlList.toString());
		            System.out.println("Total URLs Found: " + urlList.getLength());
		           

		            for (int i = 0; i < urlList.getLength(); i++) {
		                Node urlNode = urlList.item(i);
		                System.out.println("Processing node " + i + ": " + urlNode.getTextContent());
		                if (urlNode.getNodeType() == Node.ELEMENT_NODE) {
		                    Element urlElement = (Element) urlNode;
		                    String urlName = urlElement.getAttribute("name");
		                    System.out.println(" URL Name: " + urlName);
		                    NodeList paramsList = urlElement.getElementsByTagName("params");
		                    System.out.println("Params: " + paramsList.toString());
		                    String paramsValue = (paramsList.getLength() > 0) ? paramsList.item(0).getTextContent() : "";
		                    System.out.println("Paramsss: " + paramsList);
		                    Set<String> requiredParams = new HashSet<>(Arrays.asList(paramsValue.split(",")));
		                    allowedUrls.put(urlName, requiredParams);
		                }
		            }

		            // Print the HashMap
		            System.out.println("Stored Data in HashMap:");
		            for (String key : allowedUrls.keySet()) {
		                System.out.println("URL: " + key + " | Params: " + allowedUrls.get(key));
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
	

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getRequestURI().substring(req.getContextPath().length());

		System.out.println("Full Request URI: " + req.getRequestURI());
		System.out.println("Context Path: " + req.getContextPath());
		System.out.println("Final Path Extracted: " + path);
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		if (path.endsWith(".html") || path.endsWith(".css") || path.endsWith(".js")||path.endsWith("/")) {
			System.out.println("Not an AJAX request..");
			chain.doFilter(request, response);
			return;
		}
		System.out.println("Final Path allowedUrls.containsKey(path: " + allowedUrls.containsKey(path));
        if (!allowedUrls.containsKey(path)) {
            System.out.println("Access denied: URL not found in security.xml");
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);

            res.getWriter().write("{\"error\": \"Unauthorized access. This URL is not allowed.\"}");
            return;
        }
        Set<String> requiredParams = allowedUrls.get(path);
        
        if (!requiredParams.isEmpty()) {
            for (String param : requiredParams) {
                if (param != null && !param.isEmpty() && req.getParameter(param) == null) {
                    System.out.println("Missing required parameter: " + param);
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);

                    res.getWriter().write("{\"error\":  \"Missing required parameter: \" + param}");
                    return;
                }
            }
        }
        // Validate required parameters
        

		chain.doFilter(request, response);
	}

	public void destroy() {
		allowedUrls.clear();
	}
}
