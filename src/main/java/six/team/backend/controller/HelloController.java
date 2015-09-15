package six.team.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.store.PageStore;

@Controller
@RequestMapping("/")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PageStore printWelcome() {
		PageJsonGen pageJsonGen = new PageJsonGen();
		return pageJsonGen.createPageJson("Index", "This is the index", null);
	}
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	PageStore displayError() {
		PageJsonGen pageJsonGen = new PageJsonGen();
		return pageJsonGen.createPageJson("Index", "You should not perform a POST request here", null);
	}
}