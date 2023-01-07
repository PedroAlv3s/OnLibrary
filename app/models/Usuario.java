package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model {
	
	@Email
	@Required(message = "Este campo é obrigatório!")
	@Unique(message = "Esse e-mail já existe!")
	public String email;
	
	@Required(message = "Este campo é obrigatório!")
	@MinSize(8)
	public String senha;
	
	@Unique(message = "Esse nome de usuário já existe!")
	@Required(message = "Este campo é obrigatório!")
	public String nome;
	
	public Blob fotoPerfil;
	
	@Enumerated(EnumType.STRING)
	public TipoUsuario tipo;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Usuario() {
		tipo = tipo.leitor;
		status = status.ativo;
	}
	
	public void inativar() {
		status = Status.inativo;
	}
	
	public void ativar() {
		status = Status.ativo;
	}
	
	public void acharPeloNome(String nome) {
		this.nome = nome;
	}
}
