package com.tmp.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.tmp.entity.Associate;
import com.tmp.entity.Departments;

public interface CandidateDAO {
	public void setDataSource(DataSource dataSource);
	public int insertCandidateList(ArrayList<Associate> candidateList,String userId, String userName );
	public int insertCandidateDetails(Associate candidate, String userId, String userName);
	public  void populateCandidateDetailsForInsert(PreparedStatement ps, Associate candidate, String userId);
	public int getIbuByName(String ibuName);
	public int insertIbuName(String ibuName, String ibuDescription);
	public int getSkillByName(String skillName);
	public int insertSkillName(String skillName, String skillDescription);
	public int getProjectAccountDetails(String projectId);
	public int insertAssociateProjectDetails(int associateId, int projectId, Associate candidate);
	public int isResourceExist(Associate associate, String userId, String userName);
	public ArrayList<Departments> getSkillChartData(String location);
	public ArrayList<Departments> getAccountChartData(String location);
}
