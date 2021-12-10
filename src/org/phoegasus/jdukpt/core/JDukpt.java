package org.phoegasus.jdukpt.core;

import java.math.BigInteger;

import org.phoegasus.jdukpt.utils.HexUtils;
import org.phoegasus.jdukpt.utils.StringPadder;

/**
 * Provides methods for DUKPT cryptographic operations. 
 * </br>The client can provide their own implementation of the Crypto interface to the constructor, otherwise the default implementation class <code>DefaultCrypto</code> is used.
 * @author Phoegasus
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
public class JDukpt {
	
	private JDukptCore core;
	
	public JDukpt() {
		this.core = new JDukptCore();
	}
	
	public JDukpt(Crypto crypto) {
		this.core = new JDukptCore(crypto);
	}
	
	/**
	 * Encrypt using PIN variant
	 * @param bdk Hexadecimal string representation of the Base Derivation Key
	 * @param ksn Hexadecimal string representation of the Key Serial Number
	 * @param data Hexadecimal string representation of the data to encrypt
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public String encryptPIN(String bdk, String ksn, String data) throws Exception {
		return StringPadder.zeroLeftPadToMultipleOf16(encryptPIN(new BigInteger(bdk, 16), new BigInteger(ksn, 16), new BigInteger(data, 16)).toString(16));
	}
	
	/**
	 * Encrypt using DATA variant
	 * @param bdk Hexadecimal string representation of the Base Derivation Key
	 * @param ksn Hexadecimal string representation of the Key Serial Number
	 * @param data Hexadecimal string representation of the data to encrypt
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public String encryptData(String bdk, String ksn, String data) throws Exception {
		return StringPadder.zeroLeftPadToMultipleOf16(encryptData(new BigInteger(bdk, 16), new BigInteger(ksn, 16), new BigInteger(data, 16)).toString(16));
	}
	
	/**
	 * Decrypt using PIN variant
	 * @param bdk Hexadecimal string representation of the Base Derivation Key
	 * @param ksn Hexadecimal string representation of the Key Serial Number
	 * @param data Hexadecimal string representation of the data to decrypt
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public String decryptPIN(String bdk, String ksn, String data) throws Exception {
		return StringPadder.zeroLeftPadToMultipleOf16(decryptPIN(new BigInteger(bdk, 16), new BigInteger(ksn, 16), new BigInteger(data, 16)).toString(16));
	}
	
	/**
	 * Decrypt using DATA variant
	 * @param bdk Hexadecimal string representation of the Base Derivation Key
	 * @param ksn Hexadecimal string representation of the Key Serial Number
	 * @param data Hexadecimal string representation of the data to decrypt
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public String decryptData(String bdk, String ksn, String data) throws Exception {
		return StringPadder.zeroLeftPadToMultipleOf16(decryptData(new BigInteger(bdk, 16), new BigInteger(ksn, 16), new BigInteger(data, 16)).toString(16));
	}
	
	/**
	 * Encrypt using PIN variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to encrypt
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public byte[] encryptPIN(byte[] bdk, byte[] ksn, byte[] data) throws Exception {
		return HexUtils.hexToBytes(encryptPIN(HexUtils.bytesToHex(bdk), HexUtils.bytesToHex(ksn), HexUtils.bytesToHex(data)));
	}
	
	/**
	 * Encrypt using DATA variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to encrypt
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public byte[] encryptData(byte[] bdk, byte[] ksn, byte[] data) throws Exception {
		return HexUtils.hexToBytes(encryptData(HexUtils.bytesToHex(bdk), HexUtils.bytesToHex(ksn), HexUtils.bytesToHex(data)));
	}
	
	/**
	 * Decrypt using PIN variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to decrypt
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public byte[] decryptPIN(byte[] bdk, byte[] ksn, byte[] data) throws Exception {
		return HexUtils.hexToBytes(decryptPIN(HexUtils.bytesToHex(bdk), HexUtils.bytesToHex(ksn), HexUtils.bytesToHex(data)));
	}
	
	/**
	 * Decrypt using DATA variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to decrypt
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public byte[] decryptData(byte[] bdk, byte[] ksn, byte[] data) throws Exception {
		return HexUtils.hexToBytes(decryptData(HexUtils.bytesToHex(bdk), HexUtils.bytesToHex(ksn), HexUtils.bytesToHex(data)));
	}
	
	/**
	 * Encrypt using PIN variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to encrypt
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public BigInteger encryptPIN(BigInteger bdk, BigInteger ksn, BigInteger data) throws Exception {
		return core.encrypt(bdk, ksn, data, DUKPTVariant.PIN);
	}
	
	/**
	 * Encrypt using DATA variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to encrypt
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public BigInteger encryptData(BigInteger bdk, BigInteger ksn, BigInteger data) throws Exception {
		return core.encrypt(bdk, ksn, data, DUKPTVariant.Data);
	}
	
	/**
	 * Decrypt using PIN variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to decrypt
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public BigInteger decryptPIN(BigInteger bdk, BigInteger ksn, BigInteger data) throws Exception {
		return core.decrypt(bdk, ksn, data, DUKPTVariant.PIN);
	}
	
	/**
	 * Decrypt using DATA variant
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to decrypt
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	public BigInteger decryptData(BigInteger bdk, BigInteger ksn, BigInteger data) throws Exception {
		return core.decrypt(bdk, ksn, data, DUKPTVariant.Data);
	}
}
