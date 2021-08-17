package resume.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import resume.persistence.entity.Profile;
import resume.service.exception.CantCompleteClientRequestException;
import resume.persistence.repository.storage.ProfileRepository;
import resume.service.util.DataUtil;


public abstract class AbstractCreateProfileService {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String UID_DELIMETER = "-";

    @Autowired
    protected ProfileRepository profileRepository;

    @Value("${generate.uid.suffix.length}")
    private int generateUidSuffixLength;

    @Value("${generate.uid.alphabet}")
    private String generateUidAlphabet;

    @Value("${generate.uid.max.try.count}")
    private int maxTryCountToGenerateUid;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected String buildProfileUid(String firstName, String lastName) throws CantCompleteClientRequestException {
        String baseUid = DataUtil.normalizeName(firstName) + UID_DELIMETER + DataUtil.normalizeName(lastName);
        String uid = baseUid;
        for (int i = 0; profileRepository.countByUid(uid) > 0; i++) {
            uid = baseUid + UID_DELIMETER + DataUtil.generateRandomString(generateUidAlphabet, generateUidSuffixLength);
            if (i >= maxTryCountToGenerateUid) {
                throw new CantCompleteClientRequestException("Can't generate unique uid for profile: " + uid);
            }
        }
        return uid;
    }

    protected Profile createNewProfile(String firstName, String lastName, String password) {
        Profile profile = new Profile();
        profile.setUid(buildProfileUid(firstName, lastName));
        profile.setFirstName(DataUtil.capitalizeName(firstName));
        profile.setLastName(DataUtil.capitalizeName(lastName));
        profile.setPassword(passwordEncoder.encode(password));
        profile.setCompleted(false);
        return profile;
    }

    protected void showProfileCreatedLogInfoIfTransactionSuccess(final Profile profile) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                LOGGER.info("New profile created: {}", profile.getUid());
            }
        });
    }
}
