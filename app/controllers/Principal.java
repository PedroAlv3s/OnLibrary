package controllers;

import java.util.List;

import models.Livro;
import play.mvc.Controller;

public class Principal extends Controller {
	
	public static void iniciar() {
		List<Livro> livros = Livro.findAll();
		render(livros);
	}
}
