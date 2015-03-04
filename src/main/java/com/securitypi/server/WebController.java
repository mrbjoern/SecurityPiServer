package com.securitypi.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Root of the SecurityPiServer. Collects information from the system and presents it to the user interface.
 */
@RequestMapping("/**")
@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/test")
    public String test() {
        return "index";
    }

}
