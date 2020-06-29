package com.revature.mariokartfighter_v2.dao;

import java.util.List;

import com.revature.mariokartfighter_v2.models.MatchRecord;

public interface IMatchRecordRepo {
	public MatchRecord addMatchRecord(MatchRecord match);
	
	public List<MatchRecord> getAllMatches();
	public List<MatchRecord> getPlayerMatches(String playerID);
	
	public void deleteMatchRecord(String matchRecordID);
}	
