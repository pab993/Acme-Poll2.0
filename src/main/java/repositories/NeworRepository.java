
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newor;

@Repository
public interface NeworRepository extends JpaRepository<Newor, Integer> {

	@Query("select d from Newor d where d.administrator.id = ?1 and d.poll.id = ?2")
	Newor findOneByAdministratorAndPoll(int actorId, int pollId);

	@Query("select d from Newor d where d.poll.id = ?1 and d.cancel = false")
	Collection<Newor> findByPoll(int courseId);

	@Query("select d from Newor d where d.poll.id = ?1")
	Collection<Newor> findByPoll2(int courseId);

	@Query("select d from Newor d where d.mark = ?1")
	Newor findByCode(String code);

	@Query("select d from Newor d where d.poll.id = ?1 and d.displayMoment <= current_timestamp()")
	Collection<Newor> findByPoll3(int courseId);

}
