package controllers;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.Categoria;
import models.Livro;
import models.Status;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.With;
import security.Administrador;
import security.Seguranca;

@With(Seguranca.class)
public class Livros extends Controller {
	
	@Administrador
	public static void cadastrar() {
		List<Categoria> categorias = Categoria.findAll();
		render(categorias);
	}
	
	@Administrador
	public static void cadastrarCategoria() {
		render();
	}
	
	public static void salvarCategoria(Categoria categoria) {
		long quantidade = Categoria.count("categoria = ?1", categoria.categoria);
		
		if(quantidade == 0) {
			categoria.save();
			flash.success("Categoria salva com sucesso!");
		} else {
			flash.error("A categoria já existe no sistema, tente outra!");
			cadastrarCategoria();
		}
	}
	
	public static void salvar(Livro livro, File imagemLivro) {
		long quantidade = Livro.count("nome = ?1 OR id <> ?2 AND status = ?3", livro.nome, livro.id, Status.ativo);
		
		if(quantidade == 0) {
			livro.dataPublicacao = new Date();
			livro.save();
			flash.success("Livro cadastrado com sucesso!");
		} else {
			flash.error("Nome do livro já existe no sistema, tente outro nome!");
			cadastrar();
		}
		listar();
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
}
