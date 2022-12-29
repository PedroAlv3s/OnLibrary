package controllers;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import models.Status;
import models.TipoUsuario;
import models.Usuario;
import play.data.validation.Valid;
import play.libs.Crypto;
import play.mvc.Controller;
import security.Administrador;

public class Cadastros extends Controller {
	
	public static void cadastrar(Usuario usuario) {
		render(usuario);
	}
	
	public static void salvar(@Valid Usuario usuario) {
		if(validation.hasErrors()) {
			validation.keep();
			cadastrar(usuario);
		}
		
		long quantidade = Usuario.count("lower(nome) = ?1 AND status = ?2", usuario.nome, Status.ativo);
		
		if(quantidade == 0) {
			usuario.senha = Crypto.passwordHash(usuario.senha);			
			usuario.save();
			flash.success("SUCESSO!");
		} else {
			flash.error("Falha! Nome de usuário já existe no sistema!");
		}
		Logins.login();
	}
}