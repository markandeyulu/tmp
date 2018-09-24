package com.tmp.entity;

import java.util.HashMap;
import java.util.Map;

public enum Role {
	ADMIN(1, "Admin"),
	IBU_HEAD(2, "IBUHead"),
	DELIVERY_MANAGER(3, "DeliveryManager"),
	PROJECT_MANAGER(4, "ProjectManager"),
	VIEWER(0, "Viewer");
	
	private final int id;
	private final String display;
	

	private static final Map<Integer, Role> lookup = new HashMap<Integer, Role>();

	static {
		for (Role role : Role.values()) {
			lookup.put(role.id, role);
		}
	}

	Role(int id, String display) {
		this.id = id;
		this.display = display;
	}

	public int getId() {
		return this.id;
	}

	public String getDisplay() {
		return this.display;
	}

	public int toInt() {
		return this.getId();
	}

	public static Role fromInt(int id) {
		Role role = lookup.get(Integer.valueOf(id));

		if (role == null) {
			role = Role.VIEWER;
		}

		return role;
	}

	public static Role fromString(String name) {
		Role role = null;

		try {
			role = Role.valueOf(name);
		} catch (IllegalArgumentException e) {
			role = Role.VIEWER;
		}

		return role;
	}

	public static Map<String, String> getDisplayText() {
		Map<String, String> select = new HashMap<String, String>();

		for (Role role : Role.values()) {
			select.put(role.toString(), role.display);
		}

		return select;
	}

}
