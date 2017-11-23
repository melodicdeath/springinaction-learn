package org.melodicdeath.oauth2_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Oauth22Application {

	public static void main(String[] args) {
		SpringApplication.run(Oauth22Application.class, args);
	}

	//curl -X POST -vu clientapp:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=spring&username=roy&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"
	//curl trusted-app:secret@localhost:8080/oauth/token -d "grant_type=password&username=user&password=password"
}
