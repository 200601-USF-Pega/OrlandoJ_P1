package com.revature.mariokartfighter_v2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.web.ConnectionService;

public class PlayableCharacterRepoDB implements IPlayableCharacterRepo {
	ConnectionService connectionService;
	
	public PlayableCharacterRepoDB(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	
	@Override
	public PlayableCharacter addCharacter(PlayableCharacter character) {
		try {			
			PreparedStatement characterInsert = connectionService.getConnection().prepareStatement(
					"INSERT INTO playablecharacter VALUES (?, ?, ?, ?, ?, ?, ?)");
			characterInsert.setString(1, character.getCharacterID());
			characterInsert.setString(2, character.getCharacterName());
			characterInsert.setString(3, character.getType());
			characterInsert.setInt(4, character.getMaxHealth());
			characterInsert.setDouble(5, character.getAttackStat());
			characterInsert.setDouble(6, character.getDefenseStat());
			characterInsert.setInt(7, character.getUnlockAtLevel());
			
			characterInsert.executeUpdate();
			
			return character;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PlayableCharacter> getAllCharacters() {
		try {			
			PreparedStatement getCharacters = connectionService.getConnection().prepareStatement(
					"SELECT * FROM playablecharacter;");
			ResultSet charactersRS = getCharacters.executeQuery();
			
			List<PlayableCharacter> retrievedCharacters = 
					new ArrayList<PlayableCharacter>();
			
			while(charactersRS.next()) {				
				PlayableCharacter newCharacter = new PlayableCharacter(
					charactersRS.getString("characterID"),
					charactersRS.getString("characterType"),
					charactersRS.getString("name"),
					charactersRS.getInt("maxHealth"),
					charactersRS.getDouble("attackStat"), 
					charactersRS.getDouble("defenseStat"),
					charactersRS.getInt("unlockAtLevel"));	
				
				retrievedCharacters.add(newCharacter);
			}	
			return retrievedCharacters;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<PlayableCharacter>();
	}

	@Override
	public List<PlayableCharacter> getSomeCharacters(int level) {
		try {			
			PreparedStatement getCharacters = connectionService.getConnection().prepareStatement(
					"SELECT * FROM playablecharacter WHERE unlockAtLevel <= ?;");
			getCharacters.setInt(1, level);
			ResultSet charactersRS = getCharacters.executeQuery();
			
			List<PlayableCharacter> retrievedCharacters = 
					new ArrayList<PlayableCharacter>();
			
			while(charactersRS.next()) {				
				PlayableCharacter newCharacter = new PlayableCharacter(
					charactersRS.getString("characterID"),
					charactersRS.getString("characterType"),
					charactersRS.getString("name"),
					charactersRS.getInt("maxHealth"),
					charactersRS.getDouble("attackStat"), 
					charactersRS.getDouble("defenseStat"),
					charactersRS.getInt("unlockAtLevel"));	
				
				retrievedCharacters.add(newCharacter);
			}	
			return retrievedCharacters;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<PlayableCharacter>();
	}

	@Override
	public void removeCharacters(String name) {
		try {
			PreparedStatement removeCharacters = connectionService.getConnection().prepareStatement(
					"DELETE FROM playablecharacter "
					+ "WHERE characterID LIKE ?");
			removeCharacters.setString(1, name+'%');
			removeCharacters.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
