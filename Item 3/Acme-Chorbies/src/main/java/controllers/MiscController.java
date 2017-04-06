
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/misc")
public class MiscController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public MiscController() {
		super();
	}

	// Display profile --------------------------------------------------------	
	@RequestMapping(value = "/conditions", method = RequestMethod.GET)
	public ModelAndView conditions() {
		ModelAndView result;

		result = new ModelAndView("conditions");

		return result;
	}
}
