package controllers;

import java.awt.Image;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.text.Document;

import models.Status;
import models.TipoUsuario;
import models.Usuario;
import play.data.validation.Valid;
import play.db.jpa.GenericModel;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.With;
import security.Administrador;
import security.Seguranca;

@With(Seguranca.class)
public class Usuarios extends Controller {

	public static void perfil() {
		render();
	}
	
	@Administrador
	public static void edicaoAdmin() {
		render();
	}
	
	@Administrador
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
	
	@Administrador
	public static void detalhar(Long id) {
		Usuario usuario = Usuario.findById(id);
		render(usuario);
	}
	
	@Administrador
	public static void adminEditar(Long id) {
		Usuario usuario = Usuario.findById(id);
		List<TipoUsuario> tipos = Arrays.asList(TipoUsuario.values());
		renderTemplate("Cadastros/edicaoAdmin.html", usuario, tipos);
		flash.success("Edição feita com sucesso!");
		Usuarios.listar();
	}
	
	public static void editar(Long id) {
		Usuario usuario = Usuario.findById(id);
		renderTemplate("Cadastros/cadastrar.html", usuario);
		flash.success("Edição feita com sucesso!");
		perfil();
	}
	
	@Administrador
	public static void remover(Long id) {
		Usuario usuario = Usuario.findById(id);
		usuario.inativar();
		usuario.save();
		flash.success("Usuário removido com sucesso!");
		listar();
	}
}