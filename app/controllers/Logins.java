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
			session.put("idUsuario", usuario.id);
			session.put("usuario", usuario.nome);
			session.put("email", usuario.email);
			session.put("tipo", usuario.tipo);
			session.put("adm", usuario.tipo.administrador);
			flash.success("Você fez login no sistema!");
			Principal.iniciar();
		}
	}
	
	public static void logout() {
		session.clear();
		flash.success("Você saiu do sistema!");
		login();
	}
}