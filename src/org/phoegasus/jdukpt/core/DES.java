package org.phoegasus.jdukpt.core;

import java.nio.ByteBuffer;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES and TripleDES implementation.
 * @author Phoegasus
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
class DES {
	
	private static final byte[] IV = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	
	private static byte[] process(byte[] key, byte[] data, int mode) throws Exception {
		if(key.length != 8 && key.length != 16 && key.length != 24) {
			throw new Exception("Key should be 8, 16, or 24 bytes long");
		}
		
		if(data.length % 8 != 0) {
			throw new Exception("Data length should be a multiple of 8");
		}
		
		if(key.length == 16) {
			ByteBuffer bb = ByteBuffer.wrap(new byte[24]);
			bb.put(key, 0, 16);
			bb.put(key, 0, 8);
			key = bb.array();
		}

		SecretKey secretKey = new SecretKeySpec(key, key.length == 8 ? "DES" : "DESede");
		
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV);
		
		Cipher cipher = Cipher.getInstance(key.length == 8 ? "DES/CBC/NoPadding" : "DESede/CBC/NoPadding");
		
		cipher.init(mode, secretKey, ivSpec);
		
		return cipher.doFinal(data);
	}
	
	static byte[] encrypt(byte[] key, byte[] data) throws Exception {
		return process(key, data, Cipher.ENCRYPT_MODE);
	}
	
	static byte[] decrypt(byte[] key, byte[] data) throws Exception {
		return process(key, data, Cipher.DECRYPT_MODE);
	}
}
