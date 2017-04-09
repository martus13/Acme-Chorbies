
package controllers.chorbi;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.LikeService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Like;

@Controller
@RequestMapping("/like/chorbi")
public class LikeChorbiController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private LikeService		likeService;

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public LikeChorbiController() {
		super();
	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int chorbiToId) {
		ModelAndView result;
		Like like;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(chorbiToId);

		like = this.likeService.create(chorbi);

		result = this.createEditModelAndView(like);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Like like, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(like);

		} else
			try {
				like = this.likeService.save(like);
				result = new ModelAndView("redirect:../../chorbi/actor/list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(like, "like.commit.error");

			}
		return result;

	}

	// Cancel like ------------------------------------------------------------		
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int chorbiToId) {

		ModelAndView result;
		Like like;
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();
		like = this.likeService.findByGivenToIdAndGivenById(chorbiToId, chorbi.getId());

		this.likeService.delete(like);

		result = new ModelAndView("redirect:../../chorbi/actor/list.do");

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Like like) {
		ModelAndView result;

		result = this.createEditModelAndView(like, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Like like, final String message) {
		ModelAndView result;

		result = new ModelAndView("like/create");
		result.addObject("like", like);
		result.addObject("message", message);

		return result;
	}
}
