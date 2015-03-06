package com.securitypi.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Root of the SecurityPiServer. Collects information from the system and presents it to the user interface.
 */
@RequestMapping("/**")
@Controller
public class WebController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("tempReading", Readings.getLastReading());
        model.addAttribute("tempAvg", Readings.getAverageTemperature(0));
        return "index";
    }
}
