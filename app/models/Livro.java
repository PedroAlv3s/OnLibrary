package models;

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
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Livro extends Model {
	
	@Id
    @GeneratedValue
    public Long id;
	
	@Required
	public String nome;
	
	@Required
	public String autor;
	
	@Required
	public String idioma;
	
	@Required
	public int qtdPaginas;
	
	public Blob imagemLivro;
	
	@ManyToOne
	public Categoria categoria;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date dataPublicacao;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date dataSistema;
	
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
