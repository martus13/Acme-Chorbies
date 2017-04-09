
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	// C3: The ratio of chorbies who have not registered a credit card or have registered an invalid credit card.
	@Query("select 100*count(c)/(select count(c1) from Chorbi c1) from Chorbi c where c not in (select d.chorbi from CreditCard d) or c in (select d1.chorbi from CreditCard d1 where d1.expirationYear<?1 or (d1.expirationYear=?1 and d1.expirationMonth<=?2))")
	Double findRatioCreditCard(Integer year, Integer month);

}
