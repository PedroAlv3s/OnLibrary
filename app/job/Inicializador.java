package job;

import models.Categoria;
import models.TipoUsuario;
import models.Usuario;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;

@OnApplicationStart
public class Inicializador extends Job {
	
	@Override
	public void doJob() throws Exception {
		if(Usuario.count() == 0) {
			Usuario adimin = new Usuario();
			adimin.email = "pedro203@gmail.com";
			adimin.senha = Crypto.passwordHash("pedroBanana");
			adimin.nome = "Pedro Alves";
			adimin.tipo = TipoUsuario.administrador;
			adimin.save();
		}
		
		if(Categoria.count() == 0) {
			Categoria categoria1 = new Categoria();
			categoria1.categoria = "Comédia";
			categoria1.save();
			
			Categoria categoria2 = new Categoria();
			categoria2.categoria = "Romance";
			categoria2.save();
			
			Categoria categoria3 = new Categoria();
			categoria3.categoria = "Terror";
			categoria3.save();
			
			Categoria categoria4 = new Categoria();
			categoria4.categoria = "Drama";
			categoria4.save();
			
			Categoria categoria5 = new Categoria();
			categoria5.categoria = "Ação";
			categoria5.save();
			
			Categoria categoria6 = new Categoria();
			categoria6.categoria = "Aventura";
			categoria6.save();
			
			Categoria categoria7 = new Categoria();
			categoria7.categoria = "Educacional";
			categoria7.save();
		}
		System.out.println("O banco foi populado!");
	}
}
