package controllers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.Categoria;
import models.Livro;
import models.Status;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.With;
import security.Adiministrador;
import security.Seguranca;

@With(Seguranca.class)
public class Livros extends Controller {
	
	@Adiministrador
	public static void cadastrar() {
		List<Categoria> categorias = Categoria.findAll();
		render(categorias);
	}

	public static void salvar(Livro livro) {
		long quantidade = Livro.count("nome = ?1 AND status = ?2", livro.nome, Status.ativo);
		
		if(quantidade == 0) {
			//livro.dataPublicacao = new Date();
			livro.save();
			flash.success("Livro cadastrado com sucesso!");
		} else {
			flash.error("Nome do livro já existe no sistema, tente outro nome!");
			cadastrar();
		}
		Principal.iniciar();
	}
	
	public static void listar() {
		String termo = params.get("termo");
		List<Livro> livros = Collections.EMPTY_LIST;
		
		if(termo == null || termo.isEmpty()) {
			livros = Livro.find("status = ?1", Status.ativo).fetch();
		} else {
			livros = Livro.find("(lower(nome) like ?1 OR lower(autor) like ?2 OR lower(categoria) like ?3) AND status = ?4",
					 "%" + termo.toLowerCase() + "%",
					 "%" + termo.toLowerCase() + "%",
					 "%" + termo.toLowerCase() + "%",
					 Status.ativo).fetch();
		}
		render(livros, termo);
	}
	
	public static void detalhar(Long id) {
		Livro livro = Livro.findById(id);
		render(livro);
	}
	
	public static void editar(Long id) {
		List<Categoria> categoria = Categoria.findAll();
		Livro livro = Livro.findById(id);
		renderTemplate("Livros/cadastrar.html", livro, categoria);
	}
	
	public static void remover(Long id) {
		Livro livro = Livro.findById(id);
		livro.inativar();
		livro.save();
		flash.success("Livro removido com sucesso!");
		listar();
	}
}
