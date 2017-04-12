
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int userAccountId);

	@Query("select c from Chorbi c where c.banned=false")
	Collection<Chorbi> findNotBanned();

	// C1: A listing with the number of chorbies per country and city.
	@Query("select c.coordinates.country, c.coordinates.city, count(c) from Chorbi c group by c.coordinates.country, c.coordinates.city")
	Collection<Object[]> findGroupByCountryAndCity();

	// C2: The minimum, the maximum, and the average ages of the chorbies.
	@Query("select min(DATE_FORMAT(FROM_DAYS(DATEDIFF(current_timestamp, c.birthDate)), '%y')), max(DATE_FORMAT(FROM_DAYS(DATEDIFF(current_timestamp, c.birthDate)), '%y')), avg(DATE_FORMAT(FROM_DAYS(DATEDIFF(current_timestamp, c.birthDate)), '%y')) from Chorbi c")
	Object[] findMinMaxAvgAges();

	// C3: The ratio of chorbies who have not registered a credit card or have registered an invalid credit card.
	@Query("select 100*count(c)/(select count(c1) from Chorbi c1) from Chorbi c where c not in (select d.chorbi from CreditCard d) or c in (select d1.chorbi from CreditCard d1 where d1.expirationYear<?1 or (d1.expirationYear=?1 and d1.expirationMonth<=?2))")
	Double findRatioInvalidCreditCard(Integer year, Integer month);

	// C4: The ratios of chorbies who search for "activities", "friendship", and "love".
	//activities
	@Query("select 100*count(c)/(select count(d) from Chorbi d) from Chorbi c where c.relationshipEngage='activities'")
	Double findRatioActivities();

	//friendship
	@Query("select 100*count(c)/(select count(d) from Chorbi d) from Chorbi c where c.relationshipEngage='friendship'")
	Double findRatioFriendship();

	//love
	@Query("select 100*count(c)/(select count(d) from Chorbi d) from Chorbi c where c.relationshipEngage='love'")
	Double findRatioLove();

	// B1: The list of chorbies, sorted by the number of likes they have got.
	@Query("select c from Chorbi c order by c.receivedLikes.size DESC")
	Collection<Chorbi> findAllSortedByReceivedLikes();

	// A3: The chorbies who have got more chirps.

	// A4: The chorbies who have sent more chirps.
}
