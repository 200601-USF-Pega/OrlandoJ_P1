package com.revature.mariokartfighter_v2.menu;

import com.revature.mariokartfighter_v2.service.ConnectionService;

public interface IMenu {
	void start(ConnectionService connectionService, String currPlayerID);
}
