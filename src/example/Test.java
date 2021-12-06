package example;

import org.phoegasus.jdukpt.Crypto;
import org.phoegasus.jdukpt.JDukpt;

public class Test {
	public static void main(String[] args) throws Exception {
		String key = "A1A1A1A1A1A1A1A1B2B2B2B2B2B2B2B2";
		String ksn = "FFFF9876543210E00004";
		String data = "0412646667ACFEC9";
		Crypto crypto = new CryptoImpl();
		JDukpt jdukpt = new JDukpt(crypto);
		System.out.println(jdukpt.encryptPIN(key, ksn, data).toUpperCase());
	}
}
