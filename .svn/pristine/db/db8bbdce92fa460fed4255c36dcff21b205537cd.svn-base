
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("select q from Question q where q.poll.id = ?1")
	Collection<Question> findByPollId(int pollId);

	@Query("select min(p.questions.size), avg(p.questions.size), max(p.questions.size), stddev(p.questions.size) from Poll p")
	Collection<Object[]> thirstQuery();
}
