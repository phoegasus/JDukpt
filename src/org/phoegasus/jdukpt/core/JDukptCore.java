package org.phoegasus.jdukpt.core;

import java.math.BigInteger;

/**
 * Core class containing DUKPT operations.
 * @author Phoegasus
 * @since 1.1.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
class JDukptCore {
	
	private Crypto crypto;
	
	JDukptCore() {
		this.crypto = new DefaultCrypto();
	}
	
	JDukptCore(Crypto crypto) {
		this.crypto = crypto;
	}
	
	/**
	 * Create Initial PIN Encryption Key
	 * @param ksn Key Serial Number
	 * @param bdk Base Derivation Key
	 * @return Initial PIN Encryption Key
	 * @throws Exception thrown by the Crypto implementation
	 */
	BigInteger createIpek(BigInteger ksn, BigInteger bdk) throws Exception {
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
	BigInteger deriveKey(BigInteger ipek, BigInteger ksn) throws Exception {
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
	BigInteger createSessionKeyPEK(BigInteger ipek, BigInteger ksn) throws Exception {
		return deriveKey(ipek, ksn).xor(Mask.PekMask.getValue());
	}

	/**
	 * Create Session Key with DEK Mask
	 * @param ipek Initial PIN Encryption Key
	 * @param ksn Key Serial Number
	 * @return Session Key
	 * @throws Exception thrown by the Crypto implementation
	 */
	BigInteger createSessionKeyDEK(BigInteger ipek, BigInteger ksn) throws Exception {
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
	BigInteger createSessionKey(BigInteger bdk, BigInteger ksn, DUKPTVariant variant) throws Exception {
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
	BigInteger generateKey(BigInteger key, BigInteger ksn) throws Exception {
		return encryptRegister(key.xor(Mask.KeyMask.getValue()), ksn).shiftLeft(64).or(encryptRegister(key, ksn));
	}

	/**
	 * Encrypt Register
	 * @param key Encryption Key
	 * @param reg8 Register which to encrypt
	 * @return Encrypted register value
	 * @throws Exception thrown by the Crypto implementation
	 */
	BigInteger encryptRegister(BigInteger key, BigInteger reg8) throws Exception {
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
	BigInteger encrypt(BigInteger bdk, BigInteger ksn, BigInteger data, DUKPTVariant variant) throws Exception {
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
	 * Decrypt Data
	 * @param bdk Base Derivation Key
	 * @param ksn Key Serial Number
	 * @param data Data to decrypt
	 * @param variant DUKPT variant
	 * @return Decrypted data
	 * @throws Exception thrown by the Crypto implementation
	 */
	BigInteger decrypt(BigInteger bdk, BigInteger ksn, BigInteger data, DUKPTVariant variant) throws Exception {
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

}
