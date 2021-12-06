package org.phoegasus.jdukpt;

import java.math.BigInteger;

/**
 * Provides methods for DUKPT cryptographic operations. Client should provide their implementation of the Crypto interface to the constructor.
 * @author Omar Lakhlifi
 * @version 1.0.0
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
public class JDukpt {
	
	private Crypto crypto;
	
	public JDukpt(Crypto crypto) {
		this.crypto = crypto;
	}
	
	/**
	 * Create Initial PIN Encryption Key
	 * @param ksn Key Serial Number
	 * @param bdk Base Derivation Key
	 * @return Initial PIN Encryption Key
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger createIpek(BigInteger ksn, BigInteger bdk) throws Exception {
		return crypto.encryptTDES(bdk, ksn.and(Mask.KsnMask.getValue()).shiftRight(16)).shiftLeft(64)
				.or(crypto.encryptTDES(bdk.xor(Mask.KeyMask.getValue()), ksn.and(Mask.KsnMask.getValue()).shiftRight(16)));
	}

	/**
	 * Derive Key from IPEK and KSN
	 * @param ipek Initial PIN Encryption Key
	 * @param ksn Key Serial Number
	 * @return Key derived from IPEK and KSN
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger deriveKey(BigInteger ipek, BigInteger ksn) throws Exception {
		BigInteger ksnReg = ksn.and(Mask.Ls16Mask.getValue()).and(Mask.Reg8Mask.getValue());
		BigInteger curKey = ipek;
		for (BigInteger shiftReg = Mask.ShiftRegMask.getValue(); shiftReg.compareTo(BigInteger.ZERO) > 0; shiftReg = shiftReg.shiftRight(1)) {
			if(shiftReg.and(ksn).and(Mask.Reg3Mask.getValue()).compareTo(BigInteger.ZERO) > 0) {
				ksnReg = ksnReg.or(shiftReg);
				curKey = generateKey(curKey, ksnReg);
			}
		}
		return curKey;
	}

	/**
	 * Create Session Key with PEK Mask
	 * @param ipek Initial PIN Encryption Key
	 * @param ksn Key Serial Number
	 * @return Session Key
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger createSessionKeyPEK(BigInteger ipek, BigInteger ksn) throws Exception {
		return deriveKey(ipek, ksn).xor(Mask.PekMask.getValue());
	}

	/**
	 * Create Session Key with DEK Mask
	 * @param ipek Initial PIN Encryption Key
	 * @param ksn Key Serial Number
	 * @return Session Key
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger createSessionKeyDEK(BigInteger ipek, BigInteger ksn) throws Exception {
		BigInteger key = deriveKey(ipek, ksn).xor(Mask.DekMask.getValue());
		return crypto.encryptTDES(key, key.and(Mask.Ms16Mask.getValue()).shiftRight(64)).shiftLeft(64)
				.or(crypto.encryptTDES(key, key.and(Mask.Ls16Mask.getValue())));
	}

	/**
	 * Create Session Key
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param DUKPTVariant DUKPT variant used to determine session key creation method
	 * @return Session Key
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger createSessionKey(BigInteger bdk, BigInteger ksn, DUKPTVariant variant) throws Exception {
		BigInteger ksnBigInt = ksn;
        BigInteger ipek = createIpek(ksnBigInt, bdk);
        return variant == DUKPTVariant.PIN ? createSessionKeyPEK(ipek, ksn) : createSessionKeyDEK(ipek, ksn);
	}

	/**
	 * Generate Key
	 * @param key Encryption Key
	 * @param ksn Key Serial Number
	 * @return Key generated from provided key and KSN
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger generateKey(BigInteger key, BigInteger ksn) throws Exception {
		return encryptRegister(key.xor(Mask.KeyMask.getValue()), ksn).shiftLeft(64).or(encryptRegister(key, ksn));
	}

	/**
	 * Encrypt Register
	 * @param key Encryption Key
	 * @param reg8 Register which to encrypt
	 * @return Encrypted register value
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger encryptRegister(BigInteger key, BigInteger reg8) throws Exception {
		return key.and(Mask.Ls16Mask.getValue()).xor(crypto.encryptDES(key.and(Mask.Ms16Mask.getValue()).shiftRight(64), key.and(Mask.Ls16Mask.getValue()).xor(reg8)));
	}

	/**
	 * Encrypt Data
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to encrypt
	 * @param variant DUKPT variant
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger encrypt(BigInteger bdk, BigInteger ksn, BigInteger data, DUKPTVariant variant) throws Exception {
		if (bdk == null){
            throw new IllegalArgumentException("bdk is null");
        }
        if (ksn == null){
            throw new IllegalArgumentException("ksn is null");
        }
        if (data == null){
            throw new IllegalArgumentException("data is null");
        }
        
        return crypto.encryptTDES(createSessionKey(bdk, ksn, variant), data);
	}


	/**
	 * Encrypt Data
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Hexadecimal string representation of the data to encrypt
	 * @param variant DUKPT variant
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	private String encrypt(String bdk, String ksn, String data, DUKPTVariant variant) throws Exception {
		if (bdk == null || bdk.isEmpty()){
            throw new IllegalArgumentException("bdk is null or empty");
        }
        if (ksn == null || ksn.isEmpty()){
            throw new IllegalArgumentException("ksn is null or empty");
        }
        if (data == null || data.isEmpty()){
            throw new IllegalArgumentException("hexData is null or empty");
        }
        
        return encrypt(new BigInteger(bdk, 16), new BigInteger(ksn, 16), new BigInteger(data, 16), variant).toString(16);
	}

	/**
	 * Encrypt Data
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to encrypt
	 * @param variant DUKPT variant
	 * @return Encrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	private byte[] encrypt(byte[] bdk, byte[] ksn, byte[] data, DUKPTVariant variant) throws Exception {
		if (bdk == null){
            throw new IllegalArgumentException("bdk is null");
        }
        if (ksn == null){
            throw new IllegalArgumentException("ksn is null");
        }
        if (data == null){
        	throw new IllegalArgumentException("data is null");
        }
        
        return HexUtils.hexToBytes(encrypt(HexUtils.bytesToHex(bdk), HexUtils.bytesToHex(ksn), HexUtils.bytesToHex(data), variant));
	}

	/**
	 * Decrypt Data
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to decrypt
	 * @param variant DUKPT variant
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	private BigInteger decrypt(BigInteger bdk, BigInteger ksn, BigInteger data, DUKPTVariant variant) throws Exception {
		if (bdk == null){
            throw new IllegalArgumentException("bdk is null");
        }
        if (ksn == null){
            throw new IllegalArgumentException("ksn is null");
        }
        if (data == null){
            throw new IllegalArgumentException("data is null");
        }
        
        return crypto.decryptTDES(createSessionKey(bdk, ksn, variant), data);
	}

	/**
	 * Decrypt Data
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Hexadecimal string representation of the data to decrypt
	 * @param variant DUKPT variant
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	private String decrypt(String bdk, String ksn, String data, DUKPTVariant variant) throws Exception {
		if (bdk == null || bdk.isEmpty()){
            throw new IllegalArgumentException("bdk is null or empty");
        }
        if (ksn == null || ksn.isEmpty()){
            throw new IllegalArgumentException("ksn is null or empty");
        }
        if (data == null || data.isEmpty()){
            throw new IllegalArgumentException("hexData is null or empty");
        }
        
        return decrypt(new BigInteger(bdk, 16), new BigInteger(ksn, 16), new BigInteger(data, 16), variant).toString(16);
	}

	/**
	 * Decrypt Data
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to decrypt
	 * @param variant DUKPT variant
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	private byte[] decrypt(byte[] bdk, byte[] ksn, byte[] data, DUKPTVariant variant) throws Exception {
		if (bdk == null){
            throw new IllegalArgumentException("bdk is null");
        }
        if (ksn == null){
            throw new IllegalArgumentException("ksn is null");
        }
        if (data == null){
        	throw new IllegalArgumentException("data is null");
        }
        
        return HexUtils.hexToBytes(decrypt(HexUtils.bytesToHex(bdk), HexUtils.bytesToHex(ksn), HexUtils.bytesToHex(data), variant));
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
		return encrypt(bdk, ksn, data, DUKPTVariant.PIN);
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
		return encrypt(bdk, ksn, data, DUKPTVariant.Data);
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
		return decrypt(bdk, ksn, data, DUKPTVariant.PIN);
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
		return decrypt(bdk, ksn, data, DUKPTVariant.Data);
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
		return encrypt(bdk, ksn, data, DUKPTVariant.PIN);
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
		return encrypt(bdk, ksn, data, DUKPTVariant.Data);
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
		return decrypt(bdk, ksn, data, DUKPTVariant.PIN);
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
		return decrypt(bdk, ksn, data, DUKPTVariant.Data);
	}
}
