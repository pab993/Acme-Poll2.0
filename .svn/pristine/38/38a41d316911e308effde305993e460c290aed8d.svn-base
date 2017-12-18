
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date		moment;
	private String		subject;
	private String		body;
	private Priority	priority;


	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(final Priority priority) {
		this.priority = priority;
	}


	//Relationships
	private Actor				send;
	private Collection<Actor>	receives;
	private Folder				folder;


	@Valid
	@ManyToOne(optional = false)
	public Actor getSend() {
		return this.send;
	}

	public void setSend(final Actor send) {
		this.send = send;
	}

	@Valid
	@ManyToMany()
	public Collection<Actor> getReceives() {
		return this.receives;
	}

	public void setReceives(final Collection<Actor> receives) {
		this.receives = receives;
	}

	@Valid
	@ManyToOne(optional = false)
	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(final Folder folder) {
		this.folder = folder;
	}

	@Override
	public Message clone() {

		final Message msg = new Message();
		msg.setBody(this.body);
		msg.setMoment(this.moment);
		msg.setPriority(this.priority);
		msg.setReceives(this.receives);
		msg.setSubject(this.subject);
		msg.setSend(this.send);

		return msg;
	}

}
