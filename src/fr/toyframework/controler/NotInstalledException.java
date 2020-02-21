package fr.toyframework.controler;

import fr.toyframework.process.ProcessException;

public class NotInstalledException extends ProcessException {

	private static final long serialVersionUID = 8154873085440360298L;

	public NotInstalledException(String message) {
		super(message);
	}

}
