package com.tmp.entity;

public class Departments 
{
String x,y,label;

public String getX() {
	return x;
}

public void setX(String x) {
	this.x = x;
}

public String getY() {
	return y;
}

public void setY(String y) {
	this.y = y;
}

public Departments(String x, String y, String label) {
	super();
	this.x = x;
	this.y = y;
	this.label = label;
}

public String getLabel() {
	return label;
}

public void setLabel(String label) {
	this.label = label;
}



}
