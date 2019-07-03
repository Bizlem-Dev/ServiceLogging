package com.userPanel.service;

import java.io.IOException;

public interface JamesAccountCreationService {

	boolean addUser(String string, String string2) throws IOException;

	boolean verifyUser(String parameter) throws IOException;

}
