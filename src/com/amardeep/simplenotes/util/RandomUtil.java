package com.amardeep.simplenotes.util;

import java.security.SecureRandom;

public class RandomUtil {
	
	public static String generateNoteId(){
		return String.valueOf((int)(Math.random()*100000000));
	}
	public static int generateRandomIndex()
	{
		return (int)(Math.random()*10);
	}
    

}
