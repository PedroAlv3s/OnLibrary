package security;

import controllers.Logins;
import models.TipoUsuario;
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
		Adiministrador admAnotacao = getActionAnnotation(Adiministrador.class);
		if(admAnotacao != null && !TipoUsuario.adiministrador.name().equals(tipo)) {
			forbidden("Acesso restrito para os Adimins!");
		}
	}
}
