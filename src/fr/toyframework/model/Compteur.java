package fr.toyframework.model;

import fr.toyframework.type.Chars;
import fr.toyframework.type.Identifier;

public class Compteur extends A_Model {

	private Identifier id;
	private Chars name;

	public void setName(Chars name) {
		if (name==null) { name = new Chars(); name.setDbKey(true); }
		this.name = name;
	}
	public Chars getName() {
		return name;
	}
	public void setId(Identifier id) {
		this.id = id;
	}
	public Identifier getId() {
		if (id==null) id = new Identifier();
		return id;
	}
}
