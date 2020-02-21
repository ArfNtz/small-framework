package fr.toyframework.model;

import fr.toyframework.type.Identifier;

public abstract class DefaultModel extends A_ProcessModel {

	private Identifier id_user;
	private Identifier id_group;

	public Identifier getId_user() {
		return id_user;
	}
	public void setId_user(Identifier id_user) {
		this.id_user = id_user;
	}
	public Identifier getId_group() {
		return id_group;
	}
	public void setId_group(Identifier id_group) {
		this.id_group = id_group;
	}
}
