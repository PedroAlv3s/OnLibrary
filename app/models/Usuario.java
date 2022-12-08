package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.db.jpa.Model;

@Entity
public class Usuario extends Model {
	
	public String email;
	public String senha;
	public String nome;
	public int livrosBaixados;
	
	@Enumerated(EnumType.STRING)
	public TipoUsuario tipo;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Usuario() {
		//tipo = tipo.normal;
		status = status.ativo;
	}
	
	public void inativar() {
		status = Status.inativo;
	}
	
	public void ativar() {
		status = Status.ativo;
	}
}
