package org.melodicdeath;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String Home(Principal principal){
        System.out.println(principal.getName());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {

            Object _principal = authentication.getPrincipal();
            System.out.println(_principal);
        }
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
