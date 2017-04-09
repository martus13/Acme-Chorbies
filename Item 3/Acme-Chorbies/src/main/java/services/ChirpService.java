package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;
import domain.Chorbi;

@Service
@Transactional
public class ChirpService {

	//Managed repository-----------------------------------
	@Autowired
	private ChirpRepository chirpRepository;
	
	//Supporting services----------------------------------
	
	private ChorbiService chorbiService;
	
	//Constructors----------------------------------------
	
	public ChirpService(){
		super();
	}
	
	//Simple CRUD methods----------------------------------
	
	public Chirp findOne(int chirpId){
		Assert.notNull(chirpId);
		Assert.isTrue(chirpId != 0);
		
		Chirp result = this.chirpRepository.findOne(chirpId);
		
		return result;
	}
	
	public Collection<Chirp> findAll(){
		
		Collection<Chirp> result = this.chirpRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public Chirp create(Chorbi receiver){
		Assert.notNull(receiver);
		
		Chirp result = new Chirp();
		Chorbi sender;
		
		final Calendar thisMoment = Calendar.getInstance();
		thisMoment.set(Calendar.MILLISECOND, -10);
		result.setSentMoment(thisMoment.getTime());
		
		sender = this.chorbiService.findByPrincipal();
		Assert.notNull(sender);
		
		result.setSender(sender);
		result.setCopy(false);
		
		return result;
	}
	
	public Chirp save (Chirp chirp){
		
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getSender().equals(this.chorbiService.findByPrincipal()));
		
		Chirp copiedChirp = chirp;
		copiedChirp.setCopy(true);
		
		Chirp result = this.chirpRepository.save(chirp);
		this.chirpRepository.save(copiedChirp);
		
		return result;
	}
	
	public void deleteSentChirp(Chirp chirp){
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getCopy()==false);
		Assert.isTrue(chirp.getSender().equals(this.chorbiService.findByPrincipal()));
		
		this.chirpRepository.delete(chirp);
		
	}
	
	public void deleteReceivedChirp(Chirp chirp){
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getCopy()==true);
		Assert.isTrue(chirp.getRecipient().equals(this.chorbiService.findByPrincipal()));
		
		this.chirpRepository.delete(chirp);
	}
	
	//Other business methods------------------------------
	
	public Collection<Chirp> findAllMySentChirps(){
		
		Chorbi principal= this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId()!=0);
		
		Collection<Chirp> result = this.chirpRepository.findAllMySentChirps(principal.getId());
		
		return result;
	}
	
	public Collection<Chirp> findAllMyReceivedChirps(){
	
		Chorbi principal= this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId()!=0);
		
		Collection<Chirp> result = this.chirpRepository.findAllMyReceivedChirps(principal.getId());
		
		return result;
	}
}
