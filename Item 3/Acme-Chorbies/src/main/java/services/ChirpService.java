
package services;

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
	private ChirpRepository	chirpRepository;

	//Supporting services----------------------------------
	
	@Autowired
	private ChorbiService chorbiService;
	
	//Constructors----------------------------------------

	public ChirpService() {
		super();
	}

	//Simple CRUD methods----------------------------------

	public Chirp findOne(final int chirpId) {
		Assert.notNull(chirpId);
		Assert.isTrue(chirpId != 0);

		final Chirp result = this.chirpRepository.findOne(chirpId);

		return result;
	}

	public Collection<Chirp> findAll() {

		final Collection<Chirp> result = this.chirpRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Chirp create(final Chorbi receiver) {
		Assert.notNull(receiver);

		final Chirp result = new Chirp();
		Chorbi sender;

		final Calendar thisMoment = Calendar.getInstance();
		thisMoment.set(Calendar.MILLISECOND, -10);

		sender = this.chorbiService.findByPrincipal();
		Assert.notNull(sender);
		Assert.isTrue(sender.getId()!=receiver.getId());

		result.setSentMoment(thisMoment.getTime());
		result.setSender(sender);
		result.setRecipient(receiver);
		result.setCopy(false);

		return result;
	}

	public Chirp save(final Chirp chirp) {

		Assert.notNull(chirp);
		Assert.isTrue(chirp.getSender().equals(this.chorbiService.findByPrincipal()));
		Assert.isTrue(chirp.getSender().getId()!=chirp.getRecipient().getId());
		Assert.isTrue(this.validatorURL(chirp.getAttachments()));

		
		final Chirp copiedChirp = chirp;
		Chirp result = this.chirpRepository.save(chirp);

		copiedChirp.setCopy(true);
		this.chirpRepository.save(copiedChirp);

		return result;
	}

	public void deleteSentChirp(final Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getCopy() == false);
		Assert.isTrue(chirp.getSender().equals(this.chorbiService.findByPrincipal()));

		this.chirpRepository.delete(chirp);

	}

	public void deleteReceivedChirp(final Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getCopy() == true);
		Assert.isTrue(chirp.getRecipient().equals(this.chorbiService.findByPrincipal()));

		this.chirpRepository.delete(chirp);
	}
	

	//Other business methods------------------------------


	public Collection<Chirp> findAllMySentChirps(Chorbi chorbi) {

		Assert.notNull(chorbi);
		Assert.isTrue(chorbi.getId() != 0);

		final Collection<Chirp> result = this.chirpRepository.findAllMySentChirps(chorbi.getId());


		return result;
	}

	public Collection<Chirp> findAllMyReceivedChirps(Chorbi chorbi) {

		Assert.notNull(chorbi);
		Assert.isTrue(chorbi.getId() != 0);

		final Collection<Chirp> result = this.chirpRepository.findAllMyReceivedChirps(chorbi.getId());

		return result;
	}

	public Chirp resend(Chirp chirp){
		
		Chirp forwarded = this.create(chirp.getRecipient());
		
		forwarded.setSubject("Fw: "+chirp.getSubject());
		forwarded.setText(chirp.getText());
		forwarded.setAttachments(chirp.getAttachments());

		
		return forwarded;
		
	}
	
	public Double[] findMinMaxAvgReceived() {

		final Double[] result = new Double[3];

		result[0] = this.chirpRepository.findMinReceived();
		result[1] = this.chirpRepository.findMaxReceived();
		result[2] = this.chirpRepository.findAvgReceived();

		return result;
	}
	
	public Double[] findMinMaxAvgSent() {
		
		final Double[] result = new Double[3];
		
		result[0] = this.chirpRepository.findMinChirpsSent();
		result[1] = this.chirpRepository.findMaxChirpsSent();
		result[2] = this.chirpRepository.findAvgChirpsSent();
		
		return result;
	}
	
	//Devuelve true si la collection esta vacia o si las URLs contenidas en ellas son URLs validas
	public Boolean validatorURL(final Collection<String> lista) {
		Boolean res = false;
		if (!lista.isEmpty()) {
			for (final String aux : lista)
				if (aux.length() > 11) {
					if ((aux.subSequence(0, 11).equals("http://www.") || (aux.subSequence(0,4).equals("www."))||(aux.subSequence(0,8).equals("https://")) || (aux.subSequence(0,7).equals("http://")) || (aux.subSequence(0, 12).equals("https://www."))))
						res = true;
					else {
						res = false;
						break;
					}
				} else {
					res = false;
					break;
				}
		} else
			res = true;

		return res;
	}
}
