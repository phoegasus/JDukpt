package example;

import java.math.BigInteger;

import org.phoegasus.jdukpt.Crypto;
import org.phoegasus.jdukpt.HexUtils;
import org.phoegasus.jdukpt.StringPadder;

public class CryptoImpl implements Crypto {

	@Override
	public BigInteger encryptDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
        int length = data.toString(16).length();
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', length % 16 != 0 ? length + (16 - length % 16) : length));
		byte[] encData = DES.encrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}

	@Override
	public BigInteger decryptDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
        int length = data.toString(16).length();
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', length % 16 != 0 ? length + (16 - length % 16) : length));
		byte[] encData = DES.decrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}

	@Override
	public BigInteger encryptTDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
        int length = data.toString(16).length();
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', length % 16 != 0 ? length + (16 - length % 16) : length));
		byte[] encData = DES.encrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}

	@Override
	public BigInteger decryptTDES(BigInteger key, BigInteger data) throws Exception {
		byte[] keyBytes = HexUtils.hexToBytes(key.toString(16));
        int length = data.toString(16).length();
		byte[] dataBytes = HexUtils.hexToBytes(StringPadder.lPad(data.toString(16), '0', length % 16 != 0 ? length + (16 - length % 16) : length));
		byte[] encData = DES.decrypt(keyBytes, dataBytes);
		String encDataStr = HexUtils.bytesToHex(encData);
		return new BigInteger(encDataStr, 16);
	}
	
}
