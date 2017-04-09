
package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Genre;
import domain.RelationshipType;
import forms.CreateChorbiForm;

@Controller
@RequestMapping("/profile/actor")
public class ProfileActorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ProfileActorController() {
		super();
	}
	// Register ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView register() {
		final ModelAndView result;
		CreateChorbiForm chorbiForm;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		chorbiForm = this.actorService.desreconstructEditProfile(actor);

		result = this.createEditModelAndView(chorbiForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreateChorbiForm chorbiForm, final BindingResult binding) {

		ModelAndView result;
		Actor actor;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(chorbiForm);

		} else
			try {
				actor = this.actorService.reconstructEditProfile(chorbiForm);
				this.actorService.save(actor);
				result = new ModelAndView("redirect:../../welcome/index.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(chorbiForm, "profile.commit.error");

			}
		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CreateChorbiForm chorbiForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chorbiForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateChorbiForm chorbiForm, final String message) {
		ModelAndView result;
		Genre[] genres;
		RelationshipType[] relationshipTypes;

		genres = Genre.values();
		relationshipTypes = RelationshipType.values();

		result = new ModelAndView("profile/edit");
		result.addObject("chorbiForm", chorbiForm);
		result.addObject("actorForm", "chorbiForm");
		result.addObject("genres", genres);
		result.addObject("relationshipTypes", relationshipTypes);
		result.addObject("requestURI", "profile/actor/edit.do");
		result.addObject("message", message);

		return result;
	}
}
