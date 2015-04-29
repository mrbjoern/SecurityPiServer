package com.securitypi.server;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;
import com.securitypi.server.securitypi.SecurityPiHandler;
import com.securitypi.server.temperatures.TemperatureReadingsHandler;
import com.securitypi.server.users.RoleBean;
import com.securitypi.server.users.User;
import com.securitypi.server.users.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Root of the SecurityPiServer. Collects information from the system and presents it to the user interface.
 */
@RequestMapping("/**")
@Controller
public class WebController {

	@Autowired
	private ApiTokenHandler apiTokenHandler;

	@Autowired
	private EventHandler eventHandler;

	@Autowired
	private TemperatureReadingsHandler temperatureReadingsHandler;

	@Autowired
	private UserHandler userHandler;

	@Autowired
	private SecurityPiHandler securityPiHandler;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("tempReading", temperatureReadingsHandler.getLastReading());
        model.addAttribute("tempAvg", temperatureReadingsHandler.getAverageTemperatureLastHours(24));
		model.addAttribute("events", eventHandler.getNumberOfEvents(4));
        return "index";
    }

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUserPage(Model model, Principal principal) {
		model.addAttribute("fake_user", new User());
		model.addAttribute("user", userHandler.findByUsername(principal.getName()));
		return "user_index";
	}

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String getAllEvents(Model model) {
		Collection<Event> events = eventHandler.getEvents();
		model.addAttribute("events", events);
		return "all_events";
	}

	// TODO: Implement roles later
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminIndex(Model model) {
		Collection<ApiToken> apitokens = apiTokenHandler.getAllApiTokens();
		model.addAttribute("apitokens", apitokens);
		Collection<User> users = userHandler.getAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("fake_user", new User());
		model.addAttribute("security_pies", securityPiHandler.getAllSecurityPies());
		model.addAttribute("security_pi_handler", securityPiHandler);

		List<String> allRoles = new LinkedList<>();
		allRoles.add("ROLE_USER");
		allRoles.add("ROLE_ADMIN");
		model.addAttribute("allRoles", allRoles);

		RoleBean fakeRoles = new RoleBean();
		List<String> userRoles = new LinkedList<>();
		fakeRoles.setUserRoles(userRoles);
		model.addAttribute("role_bean", fakeRoles);
		return "admin_index";
	}

}
