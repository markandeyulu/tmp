package com.tmp.util;

import java.util.HashMap;
import java.util.Map;


public class EnumClasses {
	
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
	
	public enum Critical {

		CRITICAL(1, "Critical"),
		HIGH(2, "High"),
		MEDIUM(3, "Medium"),
		LOW(4, "Low");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, Critical> lookup = new HashMap<Integer, Critical>();
	
		static {
			for (Critical critical : Critical.values()) {
				lookup.put(critical.id, critical);
			}
		}
	
		Critical(int id, String display) {
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
	
		public static Critical fromInt(int id) {
			Critical critical = lookup.get(Integer.valueOf(id));
	
			if (critical == null) {
				critical = Critical.LOW;
			}
	
			return critical;
		}
	
		public static Critical fromString(String name) {
			Critical critical = null;
	
			try {
				critical = Critical.valueOf(name);
			} catch (IllegalArgumentException e) {
				critical = Critical.LOW;
			}
	
			return critical;
		}
	
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (Critical critical : Critical.values()) {
				select.put(critical.id, critical.display);
			}
	
			return select;
		}

	
	}
	
	public enum Location {
		
		ONSITE(5, "Onsite"),
		OFFSHORE(6, "Offshore");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, Location> lookup = new HashMap<Integer, Location>();
	
		static {
			for (Location location : Location.values()) {
				lookup.put(location.id, location);
			}
		}
	
		Location(int id, String display) {
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
	
		public static Location fromInt(int id) {
			Location location = lookup.get(Integer.valueOf(id));
	
			if (location == null) {
				location = Location.OFFSHORE;
			}
	
			return location;
		}
	
		public static Location fromString(String name) {
			Location location = null;
	
			try {
				location = Location.valueOf(name);
			} catch (IllegalArgumentException e) {
				location = Location.OFFSHORE;
			}
	
			return location;
		}
	
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (Location location : Location.values()) {
				select.put(location.id, location.display);
			}
	
			return select;
		}

	}
	
	public enum IntimationMode {
		
		MAIL(7, "Mail"),
		TOOL(8, "Tool"),
		TELEPHONIC(9, "Telephonic");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, IntimationMode> lookup = new HashMap<Integer, IntimationMode>();
	
		static {
			for (IntimationMode inimationMode : IntimationMode.values()) {
				lookup.put(inimationMode.id, inimationMode);
			}
		}
	
		IntimationMode(int id, String display) {
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
	
		public static IntimationMode fromInt(int id) {
			IntimationMode inimationMode = lookup.get(Integer.valueOf(id));
	
			if (inimationMode == null) {
				inimationMode = IntimationMode.MAIL;
			}
	
			return inimationMode;
		}
	
		public static IntimationMode fromString(String name) {
			IntimationMode inimationMode = null;
	
			try {
				inimationMode = IntimationMode.valueOf(name);
			} catch (IllegalArgumentException e) {
				inimationMode = IntimationMode.MAIL;
			}
	
			return inimationMode;
		}
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (IntimationMode inimationMode : IntimationMode.values()) {
				select.put(inimationMode.id, inimationMode.display);
			}
	
			return select;
		}
		

	}
	
	public enum RequirementType {
		
		CONFIRMED(10, "Confirmed"),
		PSO(11, "Pso"),
		REPLACEMENT(12, "Telephonic"),
		FORECAST(13,"Forecast");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, RequirementType> lookup = new HashMap<Integer, RequirementType>();
	
		static {
			for (RequirementType requirementType : RequirementType.values()) {
				lookup.put(requirementType.id, requirementType);
			}
		}
	
		RequirementType(int id, String display) {
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
	
		public static RequirementType fromInt(int id) {
			RequirementType requirementType = lookup.get(Integer.valueOf(id));
	
			if (requirementType == null) {
				requirementType = requirementType.CONFIRMED;
			}
	
			return requirementType;
		}
	
		public static RequirementType fromString(String name) {
			RequirementType requirementType = null;
	
			try {
				requirementType = RequirementType.valueOf(name);
			} catch (IllegalArgumentException e) {
				requirementType = RequirementType.CONFIRMED;
			}
	
			return requirementType;
		}
		
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (RequirementType requirementType : RequirementType.values()) {
				select.put(requirementType.id, requirementType.display);
			}
	
			return select;
		}
	

	}
	
	public enum PositionStatus {
		
		LEAD_GENERATION(14, "Lead Generation"),
		PROFILE_SOURCING(15, "Profile Sourcing"),
		TECHNICAL_EVALUATION(16, "Technical Evaluation"),
		CUSTOMER_EVALUATION(17, "Customer Evaluation"),
		OFFER_PROCESSING(18, "Offer Processing"),
		ONBOARDING(19,"Onboarding");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, PositionStatus> lookup = new HashMap<Integer, PositionStatus>();
	
		static {
			for (PositionStatus positionStatus : PositionStatus.values()) {
				lookup.put(positionStatus.id, positionStatus);
			}
		}
	
		PositionStatus(int id, String display) {
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
	
		
		public static PositionStatus fromInt(int id) {
			PositionStatus positionStatus = lookup.get(Integer.valueOf(id));
	
			if (positionStatus == null) {
				positionStatus = positionStatus.PROFILE_SOURCING;
			}
	
			return positionStatus;
		}
		
		public static PositionStatus fromString(String name) {
			PositionStatus positionStatus = null;
	
			try {
				positionStatus = PositionStatus.valueOf(name);
			} catch (IllegalArgumentException e) {
				positionStatus = PositionStatus.PROFILE_SOURCING;
			}
	
			return positionStatus;
		}
	
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (PositionStatus positionStatus : PositionStatus.values()) {
				select.put(positionStatus.id, positionStatus.display);
			}
	
			return select;
		}

	}
	
	public enum ProfileSource {
		
		INTERNAL(20, "Internal"),
		EXTERNAL(21, "External"),
		SUBCON(22, "Subcon");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, ProfileSource> lookup = new HashMap<Integer, ProfileSource>();
	
		static {
			for (ProfileSource profileSource : ProfileSource.values()) {
				lookup.put(profileSource.id, profileSource);
			}
		}
	
		ProfileSource(int id, String display) {
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
	
		public static ProfileSource fromInt(int id) {
			ProfileSource profileSource = lookup.get(Integer.valueOf(id));
	
			if (profileSource == null) {
				profileSource = ProfileSource.INTERNAL;
			}
	
			return profileSource;
		}
	
		public static ProfileSource fromString(String name) {
			ProfileSource profileSource = null;
	
			try {
				profileSource = ProfileSource.valueOf(name);
			} catch (IllegalArgumentException e) {
				profileSource = ProfileSource.INTERNAL;
			}
	
			return profileSource;
		}
		
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (ProfileSource profileSource : ProfileSource.values()) {
				select.put(profileSource.id, profileSource.display);
			}
	
			return select;
		}

	}
	
	public enum OpportunityStatus {
		
		HOLD(25, "Hold"),
		OPEN(28, "Open"),
		CLOSED(29, "Closed"),
		LOST(30, "Lost");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, OpportunityStatus> lookup = new HashMap<Integer, OpportunityStatus>();
	
		static {
			for (OpportunityStatus opportunityStatus : OpportunityStatus.values()) {
				lookup.put(opportunityStatus.id, opportunityStatus);
			}
		}
	
		OpportunityStatus(int id, String display) {
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
	
		public static OpportunityStatus fromInt(int id) {
			OpportunityStatus opportunityStatus = lookup.get(Integer.valueOf(id));
	
			if (opportunityStatus == null) {
				opportunityStatus = OpportunityStatus.OPEN;
			}
	
			return opportunityStatus;
		}
	
		public static OpportunityStatus fromString(String name) {
			OpportunityStatus opportunityStatus = null;
	
			try {
				opportunityStatus = OpportunityStatus.valueOf(name);
			} catch (IllegalArgumentException e) {
				opportunityStatus = OpportunityStatus.OPEN;
			}
	
			return opportunityStatus;
		}
	
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (OpportunityStatus opportunityStatus : OpportunityStatus.values()) {
				select.put(opportunityStatus.id, opportunityStatus.display);
			}
	
			return select;
		}

	}
	
	public enum InitialEvaluationResult {
		
		DID_NOT_PROCESS(26, "Did not Process"),
		IN_PROGRESS(60, "In Progress"),
		SHORTLISTED(23, "Shortlisted"),
		REJECTED(24, "Rejected"),
		HOLD(25, "Hold");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, InitialEvaluationResult> lookup = new HashMap<Integer, InitialEvaluationResult>();
	
		static {
			for (InitialEvaluationResult initialEvaluationResult : InitialEvaluationResult.values()) {
				lookup.put(initialEvaluationResult.id, initialEvaluationResult);
			}
		}
	
		InitialEvaluationResult(int id, String display) {
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
	
		public static InitialEvaluationResult fromInt(int id) {
			InitialEvaluationResult initialEvaluationResult = lookup.get(Integer.valueOf(id));
	
			if (initialEvaluationResult == null) {
				initialEvaluationResult = InitialEvaluationResult.IN_PROGRESS;
			}
	
			return initialEvaluationResult;
		}
	
		public static InitialEvaluationResult fromString(String name) {
			InitialEvaluationResult initialEvaluationResult = null;
	
			try {
				initialEvaluationResult = InitialEvaluationResult.valueOf(name);
			} catch (IllegalArgumentException e) {
				initialEvaluationResult = InitialEvaluationResult.IN_PROGRESS;
			}
	
			return initialEvaluationResult;
		}
	
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (InitialEvaluationResult initialEvaluationResult : InitialEvaluationResult.values()) {
				select.put(initialEvaluationResult.id, initialEvaluationResult.display);
			}
	
			return select;
		}
		

	}
	
	public enum CustomerInterviewStatus {
		
		SHORTLISTED(27, "Shortlisted"),
		REJECTED(28, "Rejected"),
		HOLD(29, "Hold"),
		YET_TO_PROCESS(61, "Yet to Process");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, CustomerInterviewStatus> lookup = new HashMap<Integer, CustomerInterviewStatus>();
	
		static {
			for (CustomerInterviewStatus customerInterviewStatus : CustomerInterviewStatus.values()) {
				lookup.put(customerInterviewStatus.id, customerInterviewStatus);
			}
		}
	
		CustomerInterviewStatus(int id, String display) {
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
	
		public static CustomerInterviewStatus fromInt(int id) {
			CustomerInterviewStatus customerInterviewStatus = lookup.get(Integer.valueOf(id));
	
			if (customerInterviewStatus == null) {
				customerInterviewStatus = CustomerInterviewStatus.YET_TO_PROCESS;
			}
	
			return customerInterviewStatus;
		}
	
		public static CustomerInterviewStatus fromString(String name) {
			CustomerInterviewStatus customerInterviewStatus = null;
	
			try {
				customerInterviewStatus = CustomerInterviewStatus.valueOf(name);
			} catch (IllegalArgumentException e) {
				customerInterviewStatus = CustomerInterviewStatus.YET_TO_PROCESS;
			}
	
			return customerInterviewStatus;
		}
	
				
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (CustomerInterviewStatus customerInterviewStatus : CustomerInterviewStatus.values()) {
				select.put(customerInterviewStatus.id, customerInterviewStatus.display);
			}
	
			return select;
		}

	}
	
	public enum SkillCategory {
		
		CORE_JAVA_WITH_SPRING(34, "Core Java with Spring"),
		ADMS(36, "Adms"),
		SAP_ABAB(45, "Sap_Abab"),
		SAP_HANA(47,"Sap_Hana"),
		SAP1(49, "Sap1"),
		CSHARP(51,"c#"),
		WEB_SERVICES(53, "Web services"),
		JAVA_FRONT_END(54,"Java front end"),
		SAP_ABAP(55, "Sab_Abap"),
		MIDDLEWARE_ADMIN(56,"Middleware Admin");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, SkillCategory> lookup = new HashMap<Integer, SkillCategory>();
	
		static {
			for (SkillCategory skillCategory : SkillCategory.values()) {
				lookup.put(skillCategory.id, skillCategory);
			}
		}
	
		SkillCategory(int id, String display) {
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
	
		public static SkillCategory fromInt(int id) {
			SkillCategory skillCategory = lookup.get(Integer.valueOf(id));
	
			if (skillCategory == null) {
				skillCategory = skillCategory.CORE_JAVA_WITH_SPRING;
			}
	
			return skillCategory;
		}
	
		public static SkillCategory fromString(String name) {
			SkillCategory skillCategory = null;
	
			try {
				skillCategory = SkillCategory.valueOf(name);
			} catch (IllegalArgumentException e) {
				skillCategory = SkillCategory.CORE_JAVA_WITH_SPRING;
			}
	
			return skillCategory;
		}
		
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (SkillCategory skillCategory : SkillCategory.values()) {
				select.put(skillCategory.id, skillCategory.display);
			}
	
			return select;
		}
	

	}
	
	public enum PrimarySkill {
		
		PHP(33, "Php"),
		JAVA_WITH_WEBSERVICES(35, "Java with Webservices"),
		JAVA(31, "Java"),
		NET(32,".net"),
		SAP_HANA(47, "Sap Hana"),
		SAP_DB(48,"Sap DB"),
		EOF(43, "Eof"),
		REST_SERVICE(44,"Rest Service"),
		SAP(46, "Sap"),
		VMWARE(57,"VMWare");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, PrimarySkill> lookup = new HashMap<Integer, PrimarySkill>();
	
		static {
			for (PrimarySkill primarySkill : PrimarySkill.values()) {
				lookup.put(primarySkill.id, primarySkill);
			}
		}
	
		PrimarySkill(int id, String display) {
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
	
		public static PrimarySkill fromInt(int id) {
			PrimarySkill primarySkill = lookup.get(Integer.valueOf(id));
	
			if (primarySkill == null) {
				primarySkill = primarySkill.JAVA;
			}
	
			return primarySkill;
		}
	
		public static PrimarySkill fromString(String name) {
			PrimarySkill primarySkill = null;
	
			try {
				primarySkill = PrimarySkill.valueOf(name);
			} catch (IllegalArgumentException e) {
				primarySkill = PrimarySkill.JAVA;
			}
	
			return primarySkill;
		}
		
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (PrimarySkill primarySkill : PrimarySkill.values()) {
				select.put(primarySkill.id, primarySkill.display);
			}
	
			return select;
		}
	

	}
public enum IsAllocated {
		
		YES(58, "Yes"),
		NO(59,"No");
		
		private final int id;
		private final String display;
		
	
		private static final Map<Integer, IsAllocated> lookup = new HashMap<Integer, IsAllocated>();
	
		static {
			for (IsAllocated isAllocated : IsAllocated.values()) {
				lookup.put(isAllocated.id, isAllocated);
			}
		}
	
		IsAllocated(int id, String display) {
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
	
		public static IsAllocated fromInt(int id) {
			IsAllocated isAllocated = lookup.get(Integer.valueOf(id));
	
			if (isAllocated == null) {
				isAllocated = isAllocated.NO;
			}
	
			return isAllocated;
		}
	
		public static IsAllocated fromString(String name) {
			IsAllocated isAllocated = null;
	
			try {
				isAllocated = IsAllocated.valueOf(name);
			} catch (IllegalArgumentException e) {
				isAllocated = IsAllocated.NO;
			}
	
			return isAllocated;
		}
		
		public static Map<Integer, String> getDisplayText() {
			Map<Integer, String> select = new HashMap<Integer, String>();
	
			for (IsAllocated isAllocated : IsAllocated.values()) {
				select.put(isAllocated.id, isAllocated.display);
			}
	
			return select;
		}
	

	}
	

	
	
}


