package com.revature.mariokartfighter_v2.dao;

import java.util.List;

import com.revature.mariokartfighter_v2.models.PlayableCharacter;

public interface IPlayableCharacterRepo {
	public PlayableCharacter addCharacter(PlayableCharacter character);
	public List<PlayableCharacter> getAllCharacters();
	public List<PlayableCharacter> getSomeCharacters(int level);
	public void removeCharacters(String name);
}
