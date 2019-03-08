package com.tmp.util;

import java.util.ArrayList;

public class ProfileRequirementStatusMappingUtil {
	private String initEvalResult;
	private int initEvalResultValue;
	private String isProfileSharedWithCus;
	private String cusIntStatus;
	private int cusIntStatusValue;
	private String offProStatus;
	private int offProStatusValue;
	private String resultStatus;
	private int resultStatusValue;
	private static ArrayList<ProfileRequirementStatusMappingUtil> statusList = new ArrayList<ProfileRequirementStatusMappingUtil>();

	public ProfileRequirementStatusMappingUtil(String initEvalResult, int initEvalResultValue,
			String isProfileSharedWithCus, String cusIntStatus, int cusIntStatusValue, String offProStatus, int offProStatusValue, 
			String resultStatus,
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
		this.offProStatus = offProStatus;
		this.offProStatusValue = offProStatusValue;
	}

	private static void populateList() {
		ProfileRequirementStatusMappingUtil status1 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
				"", 0, "A", 279, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status2 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
				"", 0, "B", 280, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status3 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
				"", 0, "C", 281, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status4 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
				"", 0, "D", 282, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status5 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
				"", 0, "E", 283, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status6 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
				"", 0, "F", 284, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status7 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "No",
				"", 0, "G", 285, "OnBoarding", 19);
		ProfileRequirementStatusMappingUtil status8 = new ProfileRequirementStatusMappingUtil("Rejected", 24, "No", "",
				0, "", 0, "ProfileSourcing", 15);
		ProfileRequirementStatusMappingUtil status9 = new ProfileRequirementStatusMappingUtil("Hold", 25, "No", "", 0,
				"", 0, "TechnicalEvaluation", 16);
		ProfileRequirementStatusMappingUtil status10 = new ProfileRequirementStatusMappingUtil("Did not process", 26,
				"No", "", 0, "", 0, "TechnicalEvaluation", 16);
		ProfileRequirementStatusMappingUtil status11 = new ProfileRequirementStatusMappingUtil("In progress", 60, "No",
				"", 0, "", 0, "TechnicalEvaluation", 16);
		ProfileRequirementStatusMappingUtil status13 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Shortlisted", 27, "A", 279, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status14 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Shortlisted", 27, "B", 280, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status15 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Shortlisted", 27, "C", 281, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status16 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Shortlisted", 27, "D", 282, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status17 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Shortlisted", 27, "E", 283, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status18 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Shortlisted", 27, "F", 284, "OfferProcessing", 18);
		ProfileRequirementStatusMappingUtil status19 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Shortlisted", 27, "G", 285, "Onboarding", 19);
		ProfileRequirementStatusMappingUtil status20 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Rejected", 28, "", 0, "TechnicalEvaluation", 16);
		ProfileRequirementStatusMappingUtil status21 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Hold", 29, "", 0, "CustomerEvaluation", 17);
		ProfileRequirementStatusMappingUtil status22 = new ProfileRequirementStatusMappingUtil("Shortlisted", 23, "Yes",
				"Did not process", 61, "", 0, "CustomerEvaluation", 17);
		statusList.add(status1);
		statusList.add(status2);
		statusList.add(status3);
		statusList.add(status4);
		statusList.add(status5);
		statusList.add(status6);
		statusList.add(status7);
		statusList.add(status8);
		statusList.add(status9);
		statusList.add(status10);
		statusList.add(status11);
		//statusList.add(status12);
		statusList.add(status13);
		statusList.add(status14);
		statusList.add(status15);
		statusList.add(status16);
		statusList.add(status17);
		statusList.add(status18);
		statusList.add(status19);
		statusList.add(status20);
		statusList.add(status21);
		statusList.add(status22);
	}

	
	public static int findDashboardStatus(int initEvalResultValue, String isProfileSharedWithCus,
			int cusIntStatusValue, int offProStatusValue) {
		int result = 0;
		if (statusList != null && statusList.size() == 0)
			populateList();
		for (int i = 0; i < statusList.size(); i++) {

			ProfileRequirementStatusMappingUtil sl = null;
			sl = statusList.get(i);
			if (cusIntStatusValue != 0) {
				if (sl.initEvalResultValue == initEvalResultValue
						&& sl.isProfileSharedWithCus.equalsIgnoreCase(isProfileSharedWithCus)
						&& sl.cusIntStatusValue == cusIntStatusValue
						&& sl.offProStatusValue == offProStatusValue) {
					return sl.resultStatusValue;

				}

			} else {
				if (sl.initEvalResultValue == initEvalResultValue
						&& sl.isProfileSharedWithCus.equalsIgnoreCase(isProfileSharedWithCus)
						&& sl.offProStatusValue == offProStatusValue) {
					return sl.resultStatusValue;

				}

			}

		}
		return result;

	}

}
