package com.revature.mariokartfighter_v2.dao;

import java.util.List;

import com.revature.mariokartfighter_v2.models.PlayableCharacter;

public interface ICharacterRepo {
	public PlayableCharacter addCharacter(PlayableCharacter character);
	
	public List<PlayableCharacter> getAllCharacters();
	public List<PlayableCharacter> getSomeCharacters(int level);
	public PlayableCharacter getCharacterInfo(String name);
	public String getCharacterImageURL(String characterID);
	
	public void setCharacterImageURl(String characterID, String url);

	public void removeCharacters(String name);
	public void deleteCharacterImageURL(String characterID);
}
