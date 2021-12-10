package org.phoegasus.jdukpt.core;

import java.math.BigInteger;

import org.phoegasus.jdukpt.utils.HexUtils;
import org.phoegasus.jdukpt.utils.StringPadder;

/**
 * Default implementation of the <code>Crypto</code> interface.
 * @author Phoegasus
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
public class DefaultCrypto implements Crypto {

	@Override
	public BigInteger encryptDES(BigInteger key, BigInteger data) throws Exception {
		return encryptTDES(key, data);
	}

	@Override
	public BigInteger decryptDES(BigInteger key, BigInteger data) throws Exception {
		return decryptTDES(key, data);
	}

	@Override
	public BigInteger encryptTDES(BigInteger key, BigInteger data) throws Exception {
		return new BigInteger(1, DES.encrypt(HexUtils.hexToBytes(StringPadder.zeroLeftPadToMultipleOf16(key.toString(16))), HexUtils.hexToBytes(StringPadder.zeroLeftPadToMultipleOf16(data.toString(16)))));
	}

	@Override
	public BigInteger decryptTDES(BigInteger key, BigInteger data) throws Exception {
		return new BigInteger(1, DES.decrypt(HexUtils.hexToBytes(StringPadder.zeroLeftPadToMultipleOf16(key.toString(16))), HexUtils.hexToBytes(StringPadder.zeroLeftPadToMultipleOf16(data.toString(16)))));
	}
	
}
