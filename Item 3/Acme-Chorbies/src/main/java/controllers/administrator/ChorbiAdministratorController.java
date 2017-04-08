
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi/administrator")
public class ChorbiAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public ChorbiAdministratorController() {
		super();
	}

	// Ban --------------------------------------------------------------------		

	@RequestMapping(value = "/ban", method = RequestMethod.POST, params = "ban")
	public ModelAndView ban(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(chorbiId);

		this.chorbiService.ban(chorbi);

		result = new ModelAndView("redirect:../actor/list.do");

		return result;
	}

	// Unban ------------------------------------------------------------------		

	@RequestMapping(value = "/unban", method = RequestMethod.POST, params = "unban")
	public ModelAndView unban(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(chorbiId);

		this.chorbiService.unban(chorbi);

		result = new ModelAndView("redirect:../actor/list.do");

		return result;
	}
}
