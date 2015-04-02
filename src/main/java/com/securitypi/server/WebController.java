package com.securitypi.server;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

/**
 * Root of the SecurityPiServer. Collects information from the system and presents it to the user interface.
 */
@RequestMapping("/**")
@Controller
public class WebController {

	@Autowired
	private ApiTokenHandler apiTokenHandler;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("tempReading", TemperatureReadingsHandler.getLastReading());
        model.addAttribute("tempAvg", TemperatureReadingsHandler.getAverageTemperatureLastHours(0));
		model.addAttribute("events", EventHandler.getNumberOfEvents(5));
        return "index";
    }

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String getAllEvents(Model model) {
		Collection<Event> events = EventHandler.getEvents();
		model.addAttribute("events", events);
		return "all_events";
	}

	// TODO: Implement roles later
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminIndex(Model model) {
		Collection<ApiToken> apitokens = apiTokenHandler.getAllApiTokens();
		model.addAttribute("apitokens", apitokens);
		return "admin_index";
	}

}
