package org.phoegasus.jdukpt;

import java.math.BigInteger;

/**
 * Enumeration containing the different masks used in DUKPT.
 * @author Phoegasus
 * @version 1.0.0
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
public enum Mask {
	
	Reg3Mask("1FFFFF"),
    ShiftRegMask("100000"),
    Reg8Mask("FFFFFFFFFFE00000"),
    Ls16Mask("FFFFFFFFFFFFFFFF"),
    Ms16Mask("FFFFFFFFFFFFFFFF0000000000000000"),
    KeyMask("C0C0C0C000000000C0C0C0C000000000"),
    PekMask("FF00000000000000FF"),
    KsnMask("FFFFFFFFFFFFFFE00000"),
    DekMask("0000000000FF00000000000000FF0000");
	
	//----------------------------------------------------------------------//
	
	private BigInteger value;
	
	public BigInteger getValue() {
		return value;
	}
	
	private Mask(String hexStringValue) {
		this.value = new BigInteger(hexStringValue, 16);
	}
}