package security;

import controllers.Cadastros;
import controllers.Logins;
import models.TipoUsuario;
import models.Usuario;
import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {
	
	@Before
	static void autenticar() {
		if(session.get("usuario") == null) {
			flash.error("É necessário se autenticar no sistema!");
			Logins.login();
		}
	}
	
	@Before
	static void verificarAdiministrador() {
		String tipo = session.get("tipo");
		Administrador admAnotacao = getActionAnnotation(Administrador.class);
		if(admAnotacao != null && !TipoUsuario.administrador.name().equals(tipo)) {
			forbidden("Acesso restrito aos Adimins!");
		}
	}
}