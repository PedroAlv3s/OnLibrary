package models;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Livro extends Model {
	
	@Id
    @GeneratedValue
    public Long id;
	
	@Unique(message = "Esse nome já existe!")
	@Required(message = "Este campo é obrigatório!")
	public String nome;
	
	@Required(message = "Este campo é obrigatório!")
	public String autor;
	
	@Required(message = "Este campo é obrigatório!")
	public String idioma;
	
	@Required(message = "Este campo é obrigatório!")
	public int qtdPaginas;
	
	@ManyToOne
	public Categoria categoria;
	
	@Required(message = "Este campo é obrigatório!")
	@Temporal(TemporalType.DATE)
	public Date dataPublicacao;
	
	@Required(message = "Este campo é obrigatório!")
	@Temporal(TemporalType.DATE)
	public Date dataSistema;
	
	public Blob imagemLivro;
	public Blob pdfLivro;
	
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
