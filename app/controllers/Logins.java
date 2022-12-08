package controllers;

import models.Usuario;
import play.libs.Crypto;
import play.mvc.Controller;

public class Logins extends Controller {
	
	public static void login() {
		render();
	}
	
	public static void logar(String nome, String senha) {
		Usuario usuario = Usuario.find("lower(nome) = ?1 AND lower(senha) = ?2",
				nome, Crypto.passwordHash(senha)).first();
		
		if(usuario == null) {
			flash.error("Nome ou senha inválidos!");
			login();
		} else {
			session.put("usuario", usuario.nome);
			Principal.iniciar();
		}
		
		render();
	}
	
	public static void sair() {
		session.clear();
		flash.success("Você saiu do sistema!");
		login();
	}
}