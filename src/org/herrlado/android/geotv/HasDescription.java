package org.herrlado.android.geotv;

public abstract class HasDescription {

	protected String description;
	
	protected String title;
	
	protected String id;

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
