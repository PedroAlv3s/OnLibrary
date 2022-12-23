package controllers;

import java.io.File;

import models.Status;
import models.Usuario;
import play.libs.Crypto;
import play.mvc.Controller;

public class Cadastros extends Controller {
	
	public static void cadastrar() {
		render();
	}
	
	public static void salvar(Usuario usuario, File fotoPerfil) {
		fotoPerfil.renameTo(new File("./imagensUploads/" + fotoPerfil.getName()));
		
		long quantidade = Usuario.count("lower(nome) = ?1 AND status = ?2", usuario.nome, Status.ativo);
		
		if(quantidade == 0) {
			usuario.nomeFoto = fotoPerfil.getName();
			usuario.senha = Crypto.passwordHash(usuario.senha);			
			usuario.save();
			flash.success("Usuário cadastrado com sucesso!");
		} else {
			flash.error("Nome de usuário já existe no sistema, tente outro nome!");
			Cadastros.cadastrar();
		}
		Logins.login();
	}
	
}
