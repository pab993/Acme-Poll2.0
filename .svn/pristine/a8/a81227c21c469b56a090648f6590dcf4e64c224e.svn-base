
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Poller extends Actor {

	//Relationships
	// =================================================================

	private Collection<Poll>	polls;


	@Valid
	@OneToMany(mappedBy = "poller")
	public Collection<Poll> getPolls() {
		return this.polls;
	}

	public void setPolls(final Collection<Poll> polls) {
		this.polls = polls;
	}

}
