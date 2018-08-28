package com.tmp.entity;

import java.io.Serializable;
import java.util.List;

public class Requirements implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Requirement> requirements;

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

}
