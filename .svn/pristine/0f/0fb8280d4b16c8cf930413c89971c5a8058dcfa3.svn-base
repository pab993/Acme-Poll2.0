
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Poll extends DomainEntity {

	private String	title;
	private String	description;
	private String	banner;
	private Date	startActive;
	private Date	endActive;
	private String	ticker;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@Size(min = 0, max = 250)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@Size(min = 0, max = 250)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@URL
	@NotBlank
	@Size(min = 0, max = 250)
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartActive() {
		return startActive;
	}
	public void setStartActive(Date startActive) {
		this.startActive = startActive;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndActive() {
		return endActive;
	}
	public void setEndActive(Date endActive) {
		this.endActive = endActive;
	}

	@Pattern(regexp = "^[a-zA-Z]{2}-[0-9]{5}$")
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}


	//RelationShips
	// ===========================================================

	private Poller					poller;
	private Collection<Instance>	instances;
	private Collection<Question>	questions;
	private Collection<Actor>		actors;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Poller getPoller() {
		return this.poller;
	}

	public void setPoller(final Poller poller) {
		this.poller = poller;
	}

	@Valid
	@OneToMany(mappedBy = "poll")
	public Collection<Instance> getInstances() {
		return instances;
	}
	public void setInstances(Collection<Instance> instances) {
		this.instances = instances;
	}

	@Valid
	@OneToMany(mappedBy = "poll")
	public Collection<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	@Valid
	@OneToMany
	public Collection<Actor> getActors() {
		return actors;
	}
	public void setActors(Collection<Actor> actors) {
		this.actors = actors;
	}

}
