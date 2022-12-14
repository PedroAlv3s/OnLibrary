package controllers;

import models.Usuario;
import play.libs.Crypto;
import play.mvc.Controller;

public class Logins extends Controller {
	
	public static void login() {
		render();
	}
	
	public static void logar(String nome, String senha) {
		Usuario usuario = Usuario.find("nome = ?1 and senha = ?2",
				nome, Crypto.passwordHash(senha)).first();
		
		if(usuario == null) {
			flash.error("Nome ou senha inválidos!");
			login();
		} else {
			session.put("usuario", usuario.nome);
			session.put("tipo", usuario.tipo);
			Principal.iniciar();
		}	
	}
	
	public static void logout() {
		session.clear();
		flash.success("Você saiu do sistema!");
		login();
	}
}