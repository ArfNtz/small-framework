package fr.toyframework.connector.api;

import java.io.Serializable;

public class ServiceSessionBean implements Serializable {
	
	private static final long serialVersionUID = -6869433388067516789L;
	
	private String idThread;
	private String idDevice;
	private String idUser;
	private String idGroup;
	private String userName;
	private String groupName;

	public ServiceSessionBean() {}

	public ServiceSessionBean(ServiceSessionBean sb) {
		this.idThread = sb.getIdThread();
		this.idDevice = sb.getIdDevice();
		this.idUser = sb.getIdUser();
		this.userName = sb.getUserName();
		this.groupName = sb.getGroupName();
	}

	public String getIdThread() {
		return idThread;
	}
	public void setIdThread(String idThread) {
		this.idThread = idThread;
	}

	public String getIdDevice() {
		if (idDevice==null)idDevice="";
		return idDevice;
	}
	public void setIdDevice(String string) {
		idDevice = string;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}
	
	public String toString() {
		return idDevice+";"+idThread+";"+idGroup+";"+idUser;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupName() {
		return groupName;
	}
}
