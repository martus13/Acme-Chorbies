
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

	private ChorbiService	chorbiService;


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

		result.setSentMoment(thisMoment.getTime());
		result.setSender(sender);
		result.setCopy(false);

		return result;
	}

	public Chirp save(final Chirp chirp) {

		Assert.notNull(chirp);
		Assert.isTrue(chirp.getSender().equals(this.chorbiService.findByPrincipal()));

		final Chirp copiedChirp = chirp;
		copiedChirp.setCopy(true);

		final Chirp result = this.chirpRepository.save(chirp);
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

	public Collection<Chirp> findAllMySentChirps() {

		final Chorbi principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() != 0);

		final Collection<Chirp> result = this.chirpRepository.findAllMySentChirps(principal.getId());

		return result;
	}

	public Collection<Chirp> findAllMyReceivedChirps() {

		final Chorbi principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() != 0);

		final Collection<Chirp> result = this.chirpRepository.findAllMyReceivedChirps(principal.getId());

		return result;
	}
}
