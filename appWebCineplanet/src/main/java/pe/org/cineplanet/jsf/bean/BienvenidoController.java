package pe.org.cineplanet.jsf.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("bienvenidoController")
@Scope("session")
public class BienvenidoController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(BienvenidoController.class);

	public BienvenidoController() {

	}

}
