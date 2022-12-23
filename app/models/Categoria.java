package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Categoria extends Model {
	
	@Id
    @GeneratedValue
    public Long id;
    
	public String categoria;
	
	@Override
	public String toString() {
		return categoria;
	}
}
