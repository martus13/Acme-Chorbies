package controllers.chorbi;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import domain.Chirp;
import domain.Chorbi;

@Controller
@RequestMapping("/chirp/chorbi")
public class ChirpChorbiController {
	
	//Supporting services------------------------------------
	
	@Autowired
	private ChirpService chirpService;
	
	@Autowired
	private ChorbiService chorbiService;
	
	//Constructors-------------------------------------------
	
	public ChirpChorbiController(){
		super();
	}
	
	//Create--------------------------------------------
	
	@RequestMapping(value="/create", method=RequestMethod.GET )
	public ModelAndView create(@RequestParam int receiverId){
		
		ModelAndView result;
		Chorbi receiver = this.chorbiService.findOne(receiverId);
		
		Chirp chirp = this.chirpService.create(receiver);
		
		result = this.createEditModelAndView(chirp);
		
		return result;
	}
	
	//Save--------------------------------------------------------------------
	
	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Chirp chirp, BindingResult binding){
		ModelAndView result = new ModelAndView(); 
		
		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(chirp);

		} else
			try {
				this.chirpService.save(chirp);
				result = new ModelAndView("redirect:../../chorbi/actor/list.do");
				
			}catch (final Throwable oops) {
					System.out.println(oops);

					result = this.createEditModelAndView(chirp, "chirp.commit.error");
			}
		
		return result;
	}
	
	//List-------------------------------------------------
	
	@RequestMapping(value="/receivedChirps", method=RequestMethod.GET)
	public ModelAndView listReceivedChirps(){
		
		ModelAndView result;
		boolean imSender=false;
		Chorbi principal= this.chorbiService.findByPrincipal();
		
		Collection<Chirp> receivedChirps = this.chirpService.findAllMyReceivedChirps(principal);
		
		result= new ModelAndView("chirp/list");
		result.addObject("imSender", imSender);
		result.addObject("chirps", receivedChirps);
		result.addObject("requestURI", "chirp/chorbi/receivedChirps.do");
		
		return result;
	}
	
	@RequestMapping(value="/sentChirps", method=RequestMethod.GET)
	public ModelAndView listSentChirps(){
		
		ModelAndView result;
		boolean imSender=true;
		Chorbi principal= this.chorbiService.findByPrincipal(); 
		
		Collection<Chirp> sentChirps = this.chirpService.findAllMySentChirps(principal);
		
		result= new ModelAndView("chirp/list");
		result.addObject("imSender", imSender);
		result.addObject("chirps", sentChirps);
		result.addObject("requestURI", "chirp/chorbi/sentChirps.do");
		
		return result;
	}
	
	//Reply---------------------------------------------------------------
	
	@RequestMapping(value="/reply", method=RequestMethod.POST, params="reply")
	public ModelAndView reply(@RequestParam int chirpId){
		
		ModelAndView result;
		Chirp chirp = this.chirpService.findOne(chirpId);
		
		Chorbi receiver = chirp.getSender(); 
		Chirp reply = this.chirpService.create(receiver);
		reply.setSubject("Re: "+chirp.getSubject());
		
		result= this.createEditModelAndView(reply);
		
		return result;
	}
	
	//Re-send-----------------------------------------------------------------
	
	@RequestMapping(value="/resend", method=RequestMethod.POST, params="resend")
	public ModelAndView resend(@RequestParam int chirpId){
		
		ModelAndView result;
		Chirp chirp = this.chirpService.findOne(chirpId);
		
		Chirp forwarded = this.chirpService.resend(chirp);
		
		this.chirpService.save(forwarded);
		
		result = new ModelAndView("redirect:sentChirps.do");
		
		return result;
	}
	
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Chirp chirp) {
		ModelAndView result;

		result = this.createEditModelAndView(chirp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp, final String message) {
		ModelAndView result;

		result = new ModelAndView("chirp/create");
		result.addObject("chirp", chirp);
		result.addObject("message", message);

		return result;
	}

}
