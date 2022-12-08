package controllers;

import java.util.Collections;
import java.util.List;

import models.Status;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.With;
import security.Adiministrador;
import security.Seguranca;

@With(Seguranca.class)
public class Usuarios extends Controller {
	
	public static void cadastrar() {
		render();
	}
	
	public static void perfil() {
		render();
	}
	
	public static void salvar(Usuario usuario) {
		long quantidade = Usuario.count("lower(nome) = ?1 and status = ?2", usuario.nome, Status.ativo);
		
		if(quantidade == 0) {
			usuario.save();
			flash.success("Usuário cadastrado com sucesso!");
		} else {
			flash.error("Nome de usuário já existe no sistema, tente outro nome!");
			cadastrar();
		}
		Principal.iniciar();
	}
	
	@Adiministrador
	public static void listar() {
		String termo = params.get("termo");
		List<Usuario> usuario = Collections.EMPTY_LIST;
		
		if(termo == null || termo.isEmpty()) {
			usuario = Usuario.find("status = ?1", Status.ativo).fetch();
		} else {
			usuario = Usuario.find("(lower(email) like ?1 OR lower(senha) like ?2 OR lower(nome) like ?3) AND status = ?4",
					  "%" + termo.toLowerCase() + "%",
					  "%" + termo.toLowerCase() + "%",
					  "%" + termo.toLowerCase() + "%",
					  Status.ativo).fetch();
		}
		render(usuario, termo);
	}
	
	public static void detalhar(Long id) {
		Usuario usuario = Usuario.findById(id);
		render(usuario);
	}
	
	public static void editar(Long id) {
		Usuario usuario = Usuario.findById(id);
		renderTemplate("Usuarios/cadastrar.html", usuario);
		flash.success("Edição feita com sucesso!");
		Principal.iniciar();
	}
	
	public static void remover(Long id) {
		Usuario usuario = Usuario.findById(id);
		usuario.inativar();
		usuario.save();
		flash.success("Usuário removido com sucesso!");
		listar();
	}
}