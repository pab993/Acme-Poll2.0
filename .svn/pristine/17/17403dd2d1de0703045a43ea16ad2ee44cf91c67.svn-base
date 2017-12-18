
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Instance extends DomainEntity {

	private String	name;
	private String	genre;
	private String	city;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 0, max = 250)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 0, max = 250)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}


	//Relationships
	// ==================================================

	private Poll				poll;
	private Collection<Answer>	answers;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	@Valid
	@ManyToMany
	public Collection<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}

}
