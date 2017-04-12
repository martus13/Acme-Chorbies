
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select c from Chirp c where c.sender=?1 and c.copy=false")
	Collection<Chirp> findAllMySentChirps(int myId);

	@Query("select c from Chirp c where c.recipient=?1 and c.copy=true")
	Collection<Chirp> findAllMyReceivedChirps(int myId);

	// A1: The minimum, the maximum, and the average number of chirps that a chorbi receives from other chorbies.
	////Min:
	@Query("select distinct(select count(d) from Chirp d where d.copy=true and d.recipient=c) from Chorbi c where (select count(d) from Chirp d where d.copy=true and d.recipient=c)<=ALL(select (select count(d1) from Chirp d1 where d1.copy=true and d1.recipient=c1) from Chorbi c1)")
	Double findMinReceived();

	//// Max:
	@Query("select distinct(select count(d) from Chirp d where d.copy=true and d.recipient=c) from Chorbi c where (select count(d) from Chirp d where d.copy=true and d.recipient=c)>=ALL(select (select count(d1) from Chirp d1 where d1.copy=true and d1.recipient=c1) from Chorbi c1)")
	Double findMaxReceived();

	////Avg:
	@Query("select 1.0*(select count(d) from Chirp d where d.copy=true)/count(c) from Chorbi c")
	Double findAvgReceived();

	// A2: The minimum, the maximum, and the average number of chirps that a chorbi sends to other chorbies.
}
