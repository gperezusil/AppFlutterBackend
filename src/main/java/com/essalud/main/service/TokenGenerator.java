package com.essalud.main.service;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class TokenGenerator {

	public void crearToken()
	{
		String uid = "some-uid";

		try {
			Map<String, Object> additionalClaims = new HashMap<String, Object>();
			additionalClaims.put("premiumAccount", true);
			String customToken = FirebaseAuth.getInstance()
				    .createCustomToken(uid, additionalClaims);
			
			System.out.println(customToken);
		} catch (FirebaseAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
