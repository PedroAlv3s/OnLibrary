package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Livro extends Model {
	
	public String nome;
	public String autor;
	public String idioma;
	public int qtdPaginas;
	
	public Categoria categoria;
	
	@Temporal(TemporalType.DATE)
	public Date dataPublicacao;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Livro() {
		status = status.ativo;
	}
	
	public void inativar() {
		status = Status.inativo;
	}
	
	public void ativar() {
		status = Status.ativo;
	}
}
