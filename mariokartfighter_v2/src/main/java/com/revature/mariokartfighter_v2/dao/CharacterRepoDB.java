package com.revature.mariokartfighter_v2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.web.ConnectionService;

public class CharacterRepoDB implements ICharacterRepo {
	
	@Override
	public PlayableCharacter addCharacter(PlayableCharacter character) {
		try {			
			PreparedStatement characterInsert = ConnectionService.getConnection().prepareStatement(
					"INSERT INTO playablecharacter VALUES (?, ?, ?, ?, ?, ?, ?)");
			characterInsert.setString(1, character.getCharacterID());
			characterInsert.setString(2, character.getCharacterName());
			characterInsert.setString(3, character.getType());
			characterInsert.setInt(4, character.getMaxHealth());
			characterInsert.setDouble(5, character.getAttackStat());
			characterInsert.setDouble(6, character.getDefenseStat());
			characterInsert.setInt(7, character.getUnlockAtLevel());
			
			characterInsert.executeUpdate();
			
			//check if image url exists
			if (character.getCharacterImage() != null) {
				PreparedStatement characterImageInsert = ConnectionService.getConnection().prepareStatement(
						"INSERT INTO characterImages VALUES (?, ?);");
				characterImageInsert.setString(1, character.getCharacterID());
				characterImageInsert.setString(2, character.getCharacterImage());
				
				characterImageInsert.executeUpdate();
			}
			
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
			PreparedStatement getCharacters = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM playablecharacter ORDER BY unlockAtLevel, characterType, name;");
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
			PreparedStatement getCharacters = ConnectionService.getConnection().prepareStatement(
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
			PreparedStatement removeCharacters = ConnectionService.getConnection().prepareStatement(
					"DELETE FROM playablecharacter "
					+ "WHERE characterID LIKE ?");
			removeCharacters.setString(1, name+'%');
			removeCharacters.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public PlayableCharacter getCharacterInfo(String name) {
		try {			
			PreparedStatement getCharacters = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM playablecharacter WHERE playablecharacter.name = ?;");
			getCharacters.setString(1, name);
			ResultSet charactersRS = getCharacters.executeQuery();
			
			while(charactersRS.next()) {				
				PlayableCharacter newCharacter = new PlayableCharacter(
					charactersRS.getString("characterID"),
					charactersRS.getString("characterType"),
					charactersRS.getString("name"),
					charactersRS.getInt("maxHealth"),
					charactersRS.getDouble("attackStat"), 
					charactersRS.getDouble("defenseStat"),
					charactersRS.getInt("unlockAtLevel"));	
							
				return newCharacter;
			}	
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCharacterImageURL(String characterID) {
		try {			
			PreparedStatement getCharacterURL = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM characterImages WHERE characterID = ?;");
			getCharacterURL.setString(1, characterID);
			ResultSet charactersRS = getCharacterURL.executeQuery();
			
			while(charactersRS.next()) {				
				return charactersRS.getString("url");
			}	
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void setCharacterImageURl(String characterID, String url) {
		try {
			PreparedStatement updateImgURL = ConnectionService.getConnection().prepareStatement(
					"UPDATE characterImages "
					+ "SET url = ? "
					+ "WHERE characterID = ?;");
			updateImgURL.setString(1, url);
			updateImgURL.setString(2, characterID);
			updateImgURL.executeUpdate();
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteCharacterImageURL(String characterID) {
		try {
			PreparedStatement deleteImgURL = ConnectionService.getConnection().prepareStatement(
					"DELETE FROM characterImages "
					+ "WHERE characterID = ?;");
			deleteImgURL.setString(1, characterID);
			deleteImgURL.executeUpdate();
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
