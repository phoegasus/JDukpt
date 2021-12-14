package org.phoegasus.jdukpt.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.phoegasus.jdukpt.core.JDukpt;
import org.phoegasus.jdukpt.utils.HexUtils;

public class JDukptTest {
	
	private JDukpt jdukpt = new JDukpt();

	private final String BDK = "A1A1A1A1A1A1A1A1B2B2B2B2B2B2B2B2";
	private final String KSN = "FFFF9876543210E00002";
	private final String CLEAR_DATA = "041226CBA987EDCB";
	private final String PIN_VARIANT_ENCRYPTED_DATA = "51B0229A9278CECC";
	private final String DATA_VARIANT_ENCRYPTED_DATA = "D667CEE04B7A0EB4";
	
	@Test
	public void testPinEncryptionString() throws Exception {
		assertEquals(jdukpt.encryptPIN(BDK, KSN, CLEAR_DATA).toUpperCase(), PIN_VARIANT_ENCRYPTED_DATA);
	}
	
	@Test
	public void testPinDecryptionString() throws Exception {
		assertEquals(jdukpt.decryptPIN(BDK, KSN, PIN_VARIANT_ENCRYPTED_DATA).toUpperCase(), CLEAR_DATA);
	}
	
	@Test
	public void testPinEncryptionByteArray() throws Exception {
		assertArrayEquals(jdukpt.encryptPIN(HexUtils.hexToBytes(BDK), HexUtils.hexToBytes(KSN), HexUtils.hexToBytes(CLEAR_DATA)), HexUtils.hexToBytes(PIN_VARIANT_ENCRYPTED_DATA));
	}
	
	@Test
	public void testPinDecryptionByteArray() throws Exception {
		assertArrayEquals(jdukpt.decryptPIN(HexUtils.hexToBytes(BDK), HexUtils.hexToBytes(KSN), HexUtils.hexToBytes(PIN_VARIANT_ENCRYPTED_DATA)), HexUtils.hexToBytes(CLEAR_DATA));
	}
	
	@Test
	public void testPinEncryptionBigInteger() throws Exception {
		assertEquals(jdukpt.encryptPIN(new BigInteger(BDK, 16), new BigInteger(KSN, 16), new BigInteger(CLEAR_DATA, 16)), new BigInteger(PIN_VARIANT_ENCRYPTED_DATA, 16));
	}
	
	@Test
	public void testPinDecryptionBigInteger() throws Exception {
		assertEquals(jdukpt.decryptPIN(new BigInteger(BDK, 16), new BigInteger(KSN, 16), new BigInteger(PIN_VARIANT_ENCRYPTED_DATA, 16)), new BigInteger(CLEAR_DATA, 16));
	}
	
	@Test
	public void testDataEncryptionString() throws Exception {
		assertEquals(jdukpt.encryptData(BDK, KSN, CLEAR_DATA).toUpperCase(), DATA_VARIANT_ENCRYPTED_DATA);
	}
	
	@Test
	public void testDataDecryptionString() throws Exception {
		assertEquals(jdukpt.decryptData(BDK, KSN, DATA_VARIANT_ENCRYPTED_DATA).toUpperCase(), CLEAR_DATA);
	}
	
	@Test
	public void testDataEncryptionByteArray() throws Exception {
		assertArrayEquals(jdukpt.encryptData(HexUtils.hexToBytes(BDK), HexUtils.hexToBytes(KSN), HexUtils.hexToBytes(CLEAR_DATA)), HexUtils.hexToBytes(DATA_VARIANT_ENCRYPTED_DATA));
	}
	
	@Test
	public void testDataDecryptionByteArray() throws Exception {
		assertArrayEquals(jdukpt.decryptData(HexUtils.hexToBytes(BDK), HexUtils.hexToBytes(KSN), HexUtils.hexToBytes(DATA_VARIANT_ENCRYPTED_DATA)), HexUtils.hexToBytes(CLEAR_DATA));
	}
	
	@Test
	public void testDataEncryptionBigInteger() throws Exception {
		assertEquals(jdukpt.encryptData(new BigInteger(BDK, 16), new BigInteger(KSN, 16), new BigInteger(CLEAR_DATA, 16)), new BigInteger(DATA_VARIANT_ENCRYPTED_DATA, 16));
	}
	
	@Test
	public void testDataDecryptionBigInteger() throws Exception {
		assertEquals(jdukpt.decryptData(new BigInteger(BDK, 16), new BigInteger(KSN, 16), new BigInteger(DATA_VARIANT_ENCRYPTED_DATA, 16)), new BigInteger(CLEAR_DATA, 16));
	}
}
