package Server;

public class file {

	private int uid;
	private String name;
	private String path;
	
	public file(int uid, String name, String path) {
		this.uid = uid;
		this.name = name;
		this.path = path;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}
