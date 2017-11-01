package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {


    private String str;


    public WelcomeController(@Value("${WELCOME_MESSAGE}")String msg){
        str = msg;
    }
    @GetMapping("/")
    public String sayHello(){

        return str;
    }
}
