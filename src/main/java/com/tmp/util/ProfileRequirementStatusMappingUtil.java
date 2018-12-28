package com.tmp.util;

import java.util.ArrayList;

public class ProfileRequirementStatusMappingUtil {
	private String initEvalResult;
	private int initEvalResultValue;
	private String isProfileSharedWithCus;
	private String cusIntStatus;
	private int cusIntStatusValue;
	private String resultStatus;
	private int resultStatusValue;
	private static ArrayList<ProfileRequirementStatusMappingUtil> statusList = new ArrayList<ProfileRequirementStatusMappingUtil>();

	public ProfileRequirementStatusMappingUtil(String initEvalResult, int initEvalResultValue,
	String isProfileSharedWithCus, String cusIntStatus, int cusIntStatusValue, String resultStatus,
	int resultStatusValue) {
	// ArrayList<> statusList = new ArrayList<>();
	super();
	this.initEvalResult = initEvalResult;
	this.initEvalResultValue = initEvalResultValue;
	this.isProfileSharedWithCus = isProfileSharedWithCus;
	this.cusIntStatus = cusIntStatus;
	this.cusIntStatusValue = cusIntStatusValue;
	this.resultStatus = resultStatus;
	this.resultStatusValue = resultStatusValue;


	}
	private  static void populateList() {
	ProfileRequirementStatusMappingUtil status1 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
	"", 0, "OfferProcessing", 18);
	ProfileRequirementStatusMappingUtil status2 = new ProfileRequirementStatusMappingUtil("Rejected", 24, "No", "",
	0, "ProfileSourcing", 15);
	ProfileRequirementStatusMappingUtil status3 = new ProfileRequirementStatusMappingUtil("Hold", 25, "No", "", 0,
	"TechnicalEvaluation", 16);
	ProfileRequirementStatusMappingUtil status4 = new ProfileRequirementStatusMappingUtil("Did not process", 26,
	"No", "", 0, "TechnicalEvaluation", 16);
	ProfileRequirementStatusMappingUtil status5 = new ProfileRequirementStatusMappingUtil("In progress", 60, "No",
	"", 0, "TechnicalEvaluation", 16);
	ProfileRequirementStatusMappingUtil status6 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
	"Shortlisted", 27, "OfferProcessing", 18);
	ProfileRequirementStatusMappingUtil status7 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
	"Rejected", 28, "TechnicalEvaluation", 16);
	ProfileRequirementStatusMappingUtil status8 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
	"Hold", 29, "CustomerEvaluation", 17);
	ProfileRequirementStatusMappingUtil status9 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
	"Did not process", 61, "CustomerEvaluation", 17);
	statusList.add(status1);
	statusList.add(status2);
	statusList.add(status3);
	statusList.add(status4);
	statusList.add(status5);
	statusList.add(status6);
	statusList.add(status7);
	statusList.add(status8);
	statusList.add(status9);
	}

	public static int findDashboardStatus(int initEvalResultValue, String isProfileSharedWithCus,
			int cusIntStatusValue) {
		int result = 0;
		if (statusList != null && statusList.size() == 0)
			populateList();
		for (int i = 0; i < statusList.size(); i++) {

			ProfileRequirementStatusMappingUtil sl = null;
			sl = statusList.get(i);
			if (cusIntStatusValue != 0) {
				if (sl.initEvalResultValue == initEvalResultValue
						&& sl.isProfileSharedWithCus.equalsIgnoreCase(isProfileSharedWithCus)
						&& sl.cusIntStatusValue == cusIntStatusValue) {
					return sl.resultStatusValue;

				}

			} else {
				if (sl.initEvalResultValue == initEvalResultValue
						&& sl.isProfileSharedWithCus.equalsIgnoreCase(isProfileSharedWithCus)) {
					return sl.resultStatusValue;

				}

			}

		}
		return result;

	}
	
}
