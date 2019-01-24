package com.tmp.entity;

public class Hierarchy 
{
	String Id;
	String innerHTML;
	String Parent;
	String collapsed;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getInnerHTML() {
		return innerHTML;
	}
	public void setInnerHTML(String innerHTML) {
		this.innerHTML = innerHTML;
	}
	public String getParent() {
		return Parent;
	}
	public void setParent(String parent) {
		Parent = parent;
	}
	public String getCollapsed() {
		return collapsed;
	}
	public void setCollapsed(String collapsed) {
		this.collapsed = collapsed;
	}
	public Hierarchy(String id, String innerHTML, String parent,String collapsed) 
	{
		super();
		Id = id;
		this.innerHTML = innerHTML;
		Parent = parent;
		this.collapsed = collapsed;
	}


}
