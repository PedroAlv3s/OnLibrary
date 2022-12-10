package controllers;

import org.apache.commons.mail.EmailAttachment;

import models.Usuario;
import play.Play;
import play.mvc.Mailer;

public class Emails extends Mailer {
	
	public static void mensagem(Usuario usuario) {
		setSubject("Bem vindo %s", usuario.nome);
	    addRecipient(usuario.email);
	    setFrom("Adimin <adimin@gmail.com>");
	    EmailAttachment attachment = new EmailAttachment();
	    attachment.setDescription("Uma imagem");
	    attachment.setPath(Play.getFile("Logo.jpg").getPath());
	    addAttachment(attachment);
	    send(usuario);
	}
	
	public static void lostPassword(Usuario usuario) {
		String senha = usuario.senha;
		setFrom("Robot <robot@gmail.com>");
		setSubject("Sua senha foi alterada!");
		addRecipient(usuario.email);
		send(usuario, senha);
	}
}
