package com.securitypi.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mrbjoern on 3/2/15.
 */
@RequestMapping("/api/report/**")
@Controller
public class ReportController {

    @RequestMapping("/")
    public String test() {
        return "index";
    }

    @RequestMapping(value = "/temperature", method = {RequestMethod.POST, RequestMethod.GET}, produces = {"application/json"} )
    public TemperatureReading addReading(TemperatureReading tr, Model model) {

        return tr;
    }
}
