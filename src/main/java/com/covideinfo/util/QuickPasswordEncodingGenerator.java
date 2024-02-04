package com.covideinfo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class QuickPasswordEncodingGenerator {

	public static void main(String[] args) {
		String password = "ABCD";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode(password));
	}

}
//$2a$10$Vp/nlCPjNIfVWtXTYB.ya.edW3/OimJ2/F7UUX09B5FIeZDHqJbFO
//$2a$10$TEv/W70p74HjcKL9v71Li.k5dmGFL.XYw/l1NWVr5Vu.yruJWuY8y
//$2a$10$j5blkFLrj//R1Tzxdyx1hOEuTIAtnMM9pMhuUeLtOoud/GfdlypAe
//$2a$10$sIT.nB45VPfL.6fXpbDA9.ZJLfp7JSfZRVvkdg22boFySSqYWi6JK

