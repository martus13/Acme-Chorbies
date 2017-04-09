
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChorbiService;
import controllers.AbstractController;
import domain.Actor;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi/actor")
public class ChorbiActorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ChorbiActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Actor actor;
		Collection<Chorbi> chorbies;

		actor = this.actorService.findByPrincipal();

		if (this.actorService.checkAuthority(actor, "ADMIN"))
			chorbies = this.chorbiService.findAll();
		else
			chorbies = this.chorbiService.findNotBanned();

		result = new ModelAndView("chorbi/list");
		result.addObject("requestURI", "chorbi/actor/list.do");
		result.addObject("chorbies", chorbies);

		return result;
	}

	// Display ----------------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(chorbiId);

		result = new ModelAndView("chorbi/display");
		result.addObject("requestURI", "chorbi/actor/display.do");
		result.addObject("chorbi", chorbi);

		return result;
	}
}
