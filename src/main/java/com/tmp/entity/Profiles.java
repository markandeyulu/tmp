package com.tmp.entity;

import java.io.Serializable;
import java.util.List;

public class Profiles implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Profile> profiles;

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

}
