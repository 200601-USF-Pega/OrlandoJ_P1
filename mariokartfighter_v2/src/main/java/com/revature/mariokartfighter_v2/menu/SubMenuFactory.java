package com.revature.mariokartfighter_v2.menu;

public class SubMenuFactory {
	public IMenu getMenu(String menuType) {
		switch (menuType) {
		case "character":
			return new CharacterMenu();
		case "item":
			return new ItemMenu();
		case "fight":
			return new FightMenu();
		case "matches":
			return new MatchesMenu();
		default:
			return null;
		}
	}
}
