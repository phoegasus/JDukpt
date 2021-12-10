package org.phoegasus.jdukpt.core;

import java.math.BigInteger;

/**
 * Adapter interface for the client's DES and TripleDES implementations.</br>
 * @author Phoegasus
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 * @implNote 
 * Uses BigInteger to avoid conversions before and after bitwise operations in the JDukpt class.</br></br>
 * <code>BigInteger bigInt = new BigInteger(hexString, 16); //Hex String to BigInteger</code></br></br>
 * <code>String hexString = bigInt.toString(16); //BigInteger to hex String</code>
 */
public interface Crypto {
	
	/**
	 * Client implementation of DES encryption
	 * @param key Encryption Key
	 * @param data Data to encrypt
	 * @return Encrypted data
	 * @throws Exception
	 */
	public BigInteger encryptDES(BigInteger key, BigInteger data) throws Exception;
	
	/**
	 * Client implementation of DES decryption
	 * @param key Decryption Key
	 * @param data Data to decrypt
	 * @return Decrypted data
	 * @throws Exception
	 */
	public BigInteger decryptDES(BigInteger key, BigInteger data) throws Exception;
	
	/**
	 * Client implementation of TripleDES encryption
	 * @param key Encryption Key
	 * @param data Data to encrypt
	 * @return Encrypted data
	 * @throws Exception
	 */
	public BigInteger encryptTDES(BigInteger key, BigInteger data) throws Exception;

	/**
	 * Client implementation of TripleDES decryption
	 * @param key Decryption Key
	 * @param data Data to decrypt
	 * @return Decrypted data
	 * @throws Exception
	 */
	public BigInteger decryptTDES(BigInteger key, BigInteger data) throws Exception;
}
