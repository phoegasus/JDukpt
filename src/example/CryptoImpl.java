package example;

import java.math.BigInteger;

import org.phoegasus.jdukpt.Crypto;
import org.phoegasus.jdukpt.HexUtils;

public class CryptoImpl implements Crypto {

	@Override
	public BigInteger encryptDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', 16));
		byte[] encData = DES.encrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}

	@Override
	public BigInteger decryptDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', 16));
		byte[] encData = DES.decrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}

	@Override
	public BigInteger encryptTDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', 16));
		byte[] encData = DES.encrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}

	@Override
	public BigInteger decryptTDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', 16));
		byte[] encData = DES.decrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}
	
}
