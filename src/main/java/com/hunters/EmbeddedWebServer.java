package com.hunters;

import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.catalina.startup.Tomcat;

public class EmbeddedWebServer {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = -6777759990550225380L;

	public static void main(String[] args) throws Exception {

		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(Integer.parseInt(webPort));

		// Load the war (assumes this class in in root of war file)
		final ProtectionDomain domain = EmbeddedWebServer.class
				.getProtectionDomain();
		final URL location = domain.getCodeSource().getLocation();
		tomcat.addWebapp("/", location.toURI().getPath());

		// Start server
		tomcat.start();
		tomcat.getServer().await();
	}
}
