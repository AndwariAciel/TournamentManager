package de.andwari.tournamentcore.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;

import de.andwari.tournamentcore.utils.entity.Password;
import de.andwari.tournamentcore.utils.repos.PasswordRepository;

public class PasswordManager {

	@Inject
	private PasswordRepository passRepo;

	private final static String MASTER_USER = "master";
	private final static String DEFAULT_MASTER_PASSWORD = "magic";

	public boolean verifyMasterPassword(String password) {
		Password masterPassword = findMasterPassword();
		String hash = createNewPassword(password, masterPassword.getSalt());
		return hash.equals(masterPassword.getHash());
	}

	public void initDefaulMasterPassword() {
		findMasterPassword();
	}

	public void updateMasterPassword(String password) {
		Password masterpassword = findMasterPassword();
		saveNewPassword(masterpassword, password);
	}

	public void saveNewPassword(Password user, String password) {
		String salt = generateNewSalt();
		String hash = createNewPassword(password, salt);
		user.setHash(hash);
		user.setSalt(salt);
		passRepo.update(user);
	}

	private String createNewPassword(String password, String salt) {
		byte[] decodedSalt = Base64.getDecoder().decode(salt);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), decodedSalt, 65536, 128);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = factory.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Password findMasterPassword() {
		Password masterpassword = passRepo.findByUser(MASTER_USER);
		if (masterpassword == null) {
			masterpassword = createMaster();
		}
		return masterpassword;
	}

	private Password createMaster() {
		Password master = new Password();
		master.setUser(MASTER_USER);
		master.setSalt(generateNewSalt());
		master.setHash(createNewPassword(DEFAULT_MASTER_PASSWORD, master.getSalt()));
		passRepo.create(master);
		return master;
	}

	private String generateNewSalt() {
		SecureRandom rnd = new SecureRandom();
		byte[] b = new byte[16];
		rnd.nextBytes(b);
		String salt = Base64.getEncoder().encodeToString(b);
		return salt;
	}

}
