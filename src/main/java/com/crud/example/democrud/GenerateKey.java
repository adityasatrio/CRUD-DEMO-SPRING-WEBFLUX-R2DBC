package com.crud.example.democrud;

import java.security.*;
import java.util.Base64;

public class GenerateKey {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		byte[] privateKeyBytes = privateKey.getEncoded();
		byte[] publicKeyBytes = publicKey.getEncoded();

		String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyBytes);
		String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyBytes);

		System.out.println("privateKeyBase64 key: " + privateKeyBase64);
		System.out.println("---------------------------------------");
		System.out.println("publicKeyBase64 key: " + publicKeyBase64);

	}

}
