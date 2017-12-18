
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.Priority;
import domain.Spam;

@Service
@Transactional
public class MessageService {

	public MessageService() {
		super();
	}


	@Autowired
	private MessageRepository	messageRepository;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private SpamService			spamService;


	// CRUD methods --------------------------------------------------------------------------------
	public Message create() {
		actorService.checkActorIsAuthenticated();
		Message res = new Message();
		res.setPriority(Priority.NEUTRAL);
		long milliseconds = System.currentTimeMillis() - 100;
		Date moment = new Date(milliseconds);
		res.setSend(actorService.findByPrincipal());
		res.setMoment(moment);
		return res;
	}

	public Message findOne(int messageId) {
		// Comprueba si el actor está autenticado
		actorService.checkActorIsAuthenticated();
		Message res;
		res = messageRepository.findOne(messageId);
		return res;
	}

	public Collection<Message> findAll() {
		// Comprueba si el actor está autenticado
		actorService.checkActorIsAuthenticated();
		Collection<Message> res = messageRepository.findAll();
		return res;
	}

	public Message save(Message message) {
		// Comprueba si el actor está autenticado
		actorService.checkActorIsAuthenticated();
		return messageRepository.save(message);
	}

	public void delete(Message message) {
		// Comprueba si el actor está autenticado
		actorService.checkActorIsAuthenticated();
		//Coprobamos que el mensaje existe
		Assert.isTrue(messageRepository.exists(message.getId()));
		Assert.isTrue(message.getFolder().getActor().getId() == actorService.findByPrincipal().getId());
		if (message.getFolder().getName().equals("trashbox") && message.getFolder().getIsSystem() == true) {
			messageRepository.delete(message);
		} else {
			moveMessage(folderService.findActorAndFolder(actorService.findByPrincipal().getId(), "trashbox"), message);
		}

	}

	// Other methods ------------------------------------------------------------------

	//método para mover un mensaje de una carpeta a otra
	public void moveMessage(Folder destinyFolder, Message msg) {
		// Comprueba si el actor está autenticado
		actorService.checkActorIsAuthenticated();
		// Comprobamos que la carpeta destino pertenece al actor
		Actor sender = actorService.findByPrincipal();
		Collection<Folder> folders = sender.getFolders();
		Assert.isTrue(folders.contains(destinyFolder));
		msg.setFolder(destinyFolder);
		messageRepository.save(msg);
	}

	public void sendMessage(Message message) {
		actorService.checkActorIsAuthenticated();
		Assert.isTrue(!message.getReceives().contains(message.getSend()));
		Folder folderOutbox;
		Message msg = message.clone();
		for (Folder f : message.getSend().getFolders()) {
			if (f.getName().equalsIgnoreCase("outbox")) {
				folderOutbox = f;
				msg.setFolder(folderOutbox);
				messageRepository.save(msg);
				break;
			}
		}

		Boolean isSpam = isSpam(message);
		for (Actor a : message.getReceives()) {
			Message msgRe = message.clone();
			Folder folderInbox;
			for (Folder f : a.getFolders()) {
				if ((!isSpam && f.getName().equalsIgnoreCase("inbox") && f.getIsSystem() == true) || (isSpam && f.getName().equalsIgnoreCase("spambox") && f.getIsSystem() == true)) {
					folderInbox = f;
					msgRe.setFolder(folderInbox);
					messageRepository.save(msgRe);
					break;
				}
			}
		}
	}

	private Boolean isSpam(Message message) {
		Collection<Spam> spam = spamService.findAll();
		Boolean isSpam = false;
		String body = message.getBody();
		String subject = message.getSubject();
		body = body.toUpperCase();
		subject = subject.toUpperCase();
		for (Spam s : spam) {
			if (body.contains(s.getKeywords().toUpperCase()) || subject.contains(s.getKeywords().toUpperCase())) {
				isSpam = true;
				break;
			}
		}

		return isSpam;
	}

	public void reconstruct(Message message, BindingResult binding) {
		FieldError error;
		String[] codigos;

		if (message.getReceives() == null) {
			codigos = new String[1];
			codigos[0] = "message.receives.error";
			error = new FieldError("sendMessage", "receives", message.getReceives(), false, codigos, null, "");
			binding.addError(error);
		} else {

		}
	}

}
