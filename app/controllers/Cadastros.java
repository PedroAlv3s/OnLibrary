package controllers;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import models.Categoria;
import models.Livro;
import models.Status;
import models.TipoUsuario;
import models.Usuario;
import play.data.validation.Valid;
import play.libs.Crypto;
import play.mvc.Controller;
import security.Administrador;

public class Cadastros extends Controller {
	
	private static final int QTD_ENCONTRADOS = 0;
	
	/* Formulário de Cadastro de Usuarios */
	public static void cadastrarUsuario(Usuario usuario) {
		render(usuario);
	}
	
	/* Salvar Usuários */
	public static void salvarUsuario(@Valid Usuario usuario) {
		if(validation.hasErrors()) {
			validation.keep();
			cadastrarUsuario(usuario);
		}
		
		long quantidade = Usuario.count("lower(nome) = ?1 OR lower(email) = ?2 AND status = ?3", usuario.nome, usuario.email, Status.ativo);
		
		boolean usersEncontradas = quantidade == QTD_ENCONTRADOS;
		
		if(!usersEncontradas) {
			flash.error("Falha! Nome de usuário já existe no sistema!");
		}
		
		usuario.senha = Crypto.passwordHash(usuario.senha);			
		usuario.save();
		flash.success("Cadastro realizado com sucesso!");
		Logins.login();
	}
	
	/* Formulário de Cadastro de Livros */
	@Administrador
	public static void cadastrarLivro(Livro livro) {
		List<Categoria> categorias = Categoria.findAll();
		render(categorias);
	}
	
	/* Salvar Livros */
	public static void salvarLivro(@Valid Livro livro) {
		if(validation.hasErrors()) {
			validation.keep();
			cadastrarLivro(livro);
		}
		
		long quantidade = Livro.count("nome = ?1 OR id <> ?2 AND status = ?3", livro.nome, livro.id, Status.ativo);

		boolean encontrouLivro = quantidade == QTD_ENCONTRADOS;

		if (!encontrouLivro) {
			flash.error("Nome do livro já existe no sistema, tente outro nome!");
			cadastrarLivro(livro);
		}
		
		livro.dataPublicacao = new Date();
		livro.dataSistema = new Date();
		livro.save();
		session.put("idLivro", livro.id);
		flash.success("Livro cadastrado com sucesso!");
		Livros.listar();
	}
	
	/* Formulário de Cadastros de Categorias dos Livros */
	@Administrador
	public static void cadastrarCategoria(Categoria categoria) {
		render(categoria);
	}

	/* Salvar Categorias */
	public static void salvarCategoria(@Valid Categoria categoria) {
		if(validation.hasErrors()) {
			validation.keep();
			cadastrarCategoria(categoria);
		}
		
		long quantidade = Categoria.count("lower(categoria) = ?1", categoria.categoria);
		
		boolean catEncontrados = quantidade == QTD_ENCONTRADOS;

		if (!catEncontrados) {
			categoria.save();
			flash.success("Categoria salva com sucesso!");
		} else {
			flash.error("A categoria já existe no sistema, tente outra!");
			cadastrarCategoria(categoria);
		}
	}
}