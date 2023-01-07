package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class Categoria extends Model {
	
	@Id
    @GeneratedValue
    public Long id;
    
	@Unique(message = "Essa categoria já existe!")
	@Required(message = "Este campo é obrigatório!")
	public String categoria;
	
	@Override
	public String toString() {
		return categoria;
	}
}
