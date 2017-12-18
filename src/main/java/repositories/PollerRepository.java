
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Poller;

@Repository
public interface PollerRepository extends JpaRepository<Poller, Integer> {

	@Query("select p from Poller p where p.userAccount.id = ?1")
	Poller findByUserAccountId(int userAccountId);

}
