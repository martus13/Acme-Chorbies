
package controllers.chorbi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.CreditCardService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.CreditCard;

@Controller
@RequestMapping("/creditCard/chorbi")
public class CreditCardChorbiController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ChorbiService		chorbiService;


	// Constructors -----------------------------------------------------------

	public CreditCardChorbiController() {
		super();
	}

	// Listing ----------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		CreditCard creditCard = new CreditCard();
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();
		creditCard = this.creditCardService.findByChorbi(chorbi.getId());

		result = new ModelAndView("creditCard/list");
		result.addObject("requestURI", "creditCard/chorbi/list.do");
		result.addObject("creditCard", creditCard);

		return result;
	}
}
