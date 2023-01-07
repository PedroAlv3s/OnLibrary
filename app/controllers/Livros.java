package controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.Categoria;
import models.Livro;
import models.Status;
import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.mvc.Controller;
import play.mvc.With;
import security.Administrador;
import security.Seguranca;

@With(Seguranca.class)
public class Livros extends Controller {

	/* Ações feitas nos livros */
	public static void listar() {
		String termo = params.get("termo");
		List<Livro> livros = Collections.EMPTY_LIST;

		if (termo == null || termo.isEmpty()) {
			livros = Livro.find("status = ?1", Status.ativo).fetch();
		} else {
			livros = Livro.find("(lower(nome) like ?1 OR lower(autor) like ?2) AND status = ?3",
							"%" + termo.toLowerCase() + "%", "%" + termo.toLowerCase() + "%", Status.ativo).fetch();
		}
		render(livros, termo);
	}

	public static void detalhar(Long id) {
		List<Categoria> categoria = Categoria.findById(id);
		Livro livro = Livro.findById(id);
		render(livro, categoria);
	}

	@Administrador
	public static void editar(Long id) {
		List<Categoria> categorias = Categoria.findAll();
		Livro l = Livro.findById(id);
		renderTemplate("Livros/cadastrar.html", l, categorias);
	}

	@Administrador
	public static void remover(Long id) {
		Livro livro = Livro.findById(id);
		livro.inativar();
		livro.save();
		flash.success("Livro removido com sucesso!");
		listar();
	}
	
	public static void uploadImagem(Long id) {
		Livro livro = Livro.findById(id);
		renderBinary(livro.imagemLivro.getFile());
	}
	
	public static void downloadPDF(Long id) {
		Livro livro = Livro.findById(id);
		renderBinary(livro.pdfLivro.getFile());
	}
}