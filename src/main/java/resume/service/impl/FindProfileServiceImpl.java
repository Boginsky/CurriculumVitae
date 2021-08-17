package resume.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import resume.persistence.entity.Profile;
import resume.service.model.CurrentProfile;
import resume.persistence.repository.storage.ProfileRepository;
import resume.service.FindProfileService;

@Service
public class FindProfileServiceImpl implements FindProfileService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FindProfileServiceImpl.class);
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile findByUid(String uid) {
		return profileRepository.findByUid(uid);
	}

	@Override
	public Page<Profile> findAll(Pageable pageable) {
		return profileRepository.findAll(pageable);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Profile profile = findProfile(username);
		if (profile != null) {
			return new CurrentProfile(profile);
		} else {
			LOGGER.error("Profile not found by " + username);
			throw new UsernameNotFoundException("Profile not found by " + username);
		}
	}

	private Profile findProfile(String anyUnigueId) {
		Profile profile = profileRepository.findByUid(anyUnigueId);
		if (profile == null) {
			profile = profileRepository.findByEmail(anyUnigueId);
			if (profile == null) {
				profile = profileRepository.findByPhone(anyUnigueId);
			}
		}
		return profile;
	}
}