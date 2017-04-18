package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

		@Autowired
		private AdministratorService	administratorService;


		// Tests ------------------------------------------------------------------

		@Test
		public void driverFind() {
			final Object testingData[][] = {
				{
					46, 38, null
				}, {
					0, 0, IllegalArgumentException.class
				}
			};

			for (int i = 0; i < testingData.length; i++) {
				this.testFindOne((int) testingData[i][0], (Class<?>) testingData[i][2]);
				this.testFindUserAccount((int) testingData[i][1], (Class<?>) testingData[i][2]);
				
			}
		}
		
		
		protected void testFindOne(final int adminId, final Class<?> expected) {
			Class<?> caught;

			caught = null;
			try {
				Administrator admin;

				admin = this.administratorService.findOne(adminId);
				Assert.notNull(admin);

			} catch (final Throwable oops) {
				caught = oops.getClass();
			}

			this.checkExceptions(expected, caught);
		}
		
	protected void testFindUserAccount(final int userAccountId, final Class<?> expected){
			Class<?> caught;

			caught = null;
			try {
				Administrator admin;

				admin = this.administratorService.findByUserAccountId(userAccountId);
				Assert.notNull(admin);
				
			}catch (final Throwable oops) {
					caught = oops.getClass();
				}

			this.checkExceptions(expected, caught);
		}
		
		@Test
		public void testFindAll() {
			Collection<Administrator> admins;

			admins = this.administratorService.findAll();
			Assert.isTrue(admins.size() == 1);
		}
		
		
		
		
}
