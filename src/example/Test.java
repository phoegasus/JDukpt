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
		String decPin = jdukpt.encryptPIN(key, ksn, data).toUpperCase();
		System.out.println(decPin);
		String encPin = jdukpt.decryptPIN(key, ksn, decPin).toUpperCase();
		System.out.println(encPin);
		String decData = jdukpt.encryptData(key, ksn, data).toUpperCase();
		System.out.println(decData);
		String encData = jdukpt.decryptData(key, ksn, decData).toUpperCase();
		System.out.println(encData);
	}
}
