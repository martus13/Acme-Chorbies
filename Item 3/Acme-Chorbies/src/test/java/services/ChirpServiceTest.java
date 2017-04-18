package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chirp;
import domain.Chorbi;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class ChirpServiceTest extends AbstractTest {

	//System under test------------------------------------------------------
	
	@Autowired
	private ChirpService chirpService;
	
	@Autowired
	private ChorbiService chorbiService;
	
	
	//Tests------------------------------------------
	// A continuación se van a realizar pruebas para comprobar el correcto funcionamiento de los casos de uso relacionados con Chirp.
	
	//Mostrar los chiprs recibidos y enviados
	
	@Test
	public void driverFind(){
		
			final Object testingData[][] = {
					{
						50, null
					}
			};
			
			for (int i = 0; i < testingData.length; i++) {
				
				this.testFindAllReceived((int) testingData[i][0], (Class<?>) testingData[i][1]);
				this.testFindAllSent((int) testingData[i][0], (Class<?>) testingData[i][1]);
			}
	}
	
	//Enviar (crear) un chirp
	
	@Test
	public void driverCreateAndSave(){
		
		final Object testingData[][] = {
				{	// Bien
					"chorbi1", 52,"Asunto test" ,"Test save",null
				}, {// No se puede enviar chirps a sí mismo
					"chorbi1", 50,"Asunto test" ,"Test save", IllegalArgumentException.class
				}, {// Debe estar logueado
					null, 50, "Asunto test" ,"Test save", IllegalArgumentException.class
				}
			};
		
		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][4]);
			this.testCreateAndSave((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
		
	}
	
	
	//Gestionar los chirps; incluye responder, reenviar y eliminar un chirp
	
	@Test
	public void driverReplyDeleteResend(){
		
		final Object testingData[][] = {
				{	// Bien
					"chorbi1", "chorbi2", 69, 70, null
				}, {// Debe estar logueado
					null, null, 69, 70,IllegalArgumentException.class
				}, {// El chorbi que realiza la acción debe coresponder con el que envía o recibe el chirp en cuestión
					"chorbi1", "chorbi2", 71, 72,IllegalArgumentException.class
				}
				
			};
		
		for (int i = 0; i < testingData.length; i++) {

			this.testResend((String) testingData[i][1] , (int) testingData[i][2], (Class<?> ) testingData[i][4]);
			this.testReply((String) testingData[i][0], (int)  testingData[i][3], (Class<?> ) testingData[i][4]);
			this.testDeleteReceivedChirp((String) testingData[i][0], (int)  testingData[i][3], (Class<?> ) testingData[i][4]);
			this.testDeleteSentChirp((String) testingData[i][1] , (int) testingData[i][2], (Class<?> ) testingData[i][4]);
		}
		
	}
	
	protected void testCreate(final String username, final int chrobiId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Chirp chirp;
			Chorbi chorbi;

			chorbi = this.chorbiService.findOne(chrobiId);
			chirp = this.chirpService.create(chorbi);
			Assert.notNull(chirp);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testCreateAndSave(final String username, final int chrobiId, final String subject, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Chirp chirp;
			Chorbi chorbi;

			chorbi = this.chorbiService.findOne(chrobiId);
			chirp = this.chirpService.create(chorbi);
			chirp.setSubject(subject);
			chirp.setText(text);
			chirp = this.chirpService.save(chirp);
			Assert.isTrue(!chirp.getSubject().isEmpty());
			Assert.isTrue(!chirp.getText().isEmpty());
			Assert.notNull(chirp.getAttachments());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testDeleteSentChirp(final String username, final int chirpId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Chirp chirp;
			Collection<Chirp> chirps;

			chirp = this.chirpService.findOne(chirpId);
			this.chirpService.deleteSentChirp(chirp);

			chirps = this.chirpService.findAllMySentChirps(chorbiService.findByPrincipal());
			Assert.isTrue(!chirps.contains(chirp));
			
			System.out.println("Chirp "+chirpId+" has been deleted");

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
	

protected void testDeleteReceivedChirp(final String username, final int chirpId, final Class<?> expected) {
	Class<?> caught;

	caught = null;
	try {
		this.authenticate(username);

		Chirp chirp;
		Collection<Chirp> chirps;

		chirp = this.chirpService.findOne(chirpId);
		this.chirpService.deleteReceivedChirp(chirp);

		chirps = this.chirpService.findAllMyReceivedChirps(chorbiService.findByPrincipal());
		Assert.isTrue(!chirps.contains(chirp));
		
		System.out.println("Chirp "+chirpId+" has been deleted");

		this.unauthenticate();
	} catch (final Throwable oops) {
		caught = oops.getClass();
	}

	this.checkExceptions(expected, caught);

}

protected void testFindAllSent(final int chorbiId, final Class<?> expected){
	
	Class<?> caught;

	caught = null;
	try {
		final Collection<Chirp> chirps;

		chirps = this.chirpService.findAllMySentChirps(this.chorbiService.findOne(chorbiId));
		System.out.println("Chirps sent by user " + chorbiId + ": " + chirps.size());

	} catch (final Throwable oops) {
		caught = oops.getClass();
	}

	this.checkExceptions(expected, caught);
}

protected void testFindAllReceived(final int chorbiId, final Class<?> expected){
	
	Class<?> caught;

	caught = null;
	try {
		final Collection<Chirp> chirps;

		chirps = this.chirpService.findAllMyReceivedChirps(this.chorbiService.findOne(chorbiId));
		System.out.println("Chirps received by user " + chorbiId + ": " + chirps.size());

	} catch (final Throwable oops) {
		caught = oops.getClass();
	}

	this.checkExceptions(expected, caught);
}

protected void testReply(final String username, final int chirpId, final Class<?> expected){
	
	Class<?> caught;

	caught = null;
	try {
		
		this.authenticate(username);
		
		Chirp chirp = this.chirpService.findOne(chirpId);
		Chirp reply = this.chirpService.reply(chirp);
		
		System.out.println("An answer has been sent to chirp "+chirpId+ " with subject "+reply.getSubject());
		
		this.unauthenticate();
	}catch (final Throwable oops) {
		caught = oops.getClass();
	}
	this.checkExceptions(expected, caught);
}

protected void testResend(final String username, final int chirpId, final Class<?> expected){
	
	Class<?> caught;

	caught = null;
	try {
		this.authenticate(username);
		
		Chirp chirp = this.chirpService.findOne(chirpId);
		Chirp reply = this.chirpService.resend(chirp);
		
		System.out.println("The chirp "+chirpId+ " has been forwarded with subject "+reply.getSubject());
		
		this.unauthenticate();
	}catch (final Throwable oops) {
		caught = oops.getClass();
	}
	this.checkExceptions(expected, caught);
}

}
