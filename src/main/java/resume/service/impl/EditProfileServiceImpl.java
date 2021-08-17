package resume.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import resume.persistence.entity.*;
import resume.service.form.InfoForm;
import resume.service.form.SignUpForm;
import resume.service.model.CurrentProfile;
import resume.persistence.repository.storage.*;
import resume.service.EditProfileService;
import resume.service.util.DataUtil;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@SuppressWarnings("unchecked")
public class EditProfileServiceImpl extends AbstractCreateProfileService implements EditProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditProfileServiceImpl.class);

    private Map<Class<? extends ProfileEntity>, AbstractProfileEntityRepository<? extends ProfileEntity>> profileEntityRepositoryMap;

    private Set<String> profileCollectionsToReIndex;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private PracticRepository practicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Value("${generate.uid.suffix.length}")
    private int generateUidSuffixLength;

    @Value("${generate.uid.alphabet}")
    private String generateUidAlphabet;

    @Value("${generate.uid.max.try.count}")
    private int maxTryCountToGenerateUid;

    @PostConstruct
    private void postConstruct() {
        profileCollectionsToReIndex = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList(
                        new String[]{"languages", "skills", "practics", "courses"})));
        Map<Class<? extends ProfileEntity>, AbstractProfileEntityRepository<? extends ProfileEntity>> map = new HashMap<>();
        map.put(Course.class, courseRepository);
        map.put(Education.class, educationRepository);
        map.put(Language.class, languageRepository);
        map.put(Practic.class, practicRepository);
        map.put(Skill.class, skillRepository);
        profileEntityRepositoryMap = Collections.unmodifiableMap(map);
    }

    @Override
    @Transactional
    public Profile createNewProfile(SignUpForm signUpForm) {
        Profile profile = createNewProfile(signUpForm.getFirstName(), signUpForm.getLastName(), signUpForm.getPassword());
        profileRepository.save(profile);
        showProfileCreatedLogInfoIfTransactionSuccess(profile);
        return profile;
    }

    @Override
    public List<Skill> listSkills(long idProfile) {
        return profileRepository.findOne(idProfile).getSkills();
    }

    @Override
    public List<SkillCategory> listSkillCategories() {
        return skillCategoryRepository.findAll(new Sort("id"));
    }

    @Override
    @Transactional
    public void updateSkills(CurrentProfile currentProfile, List<Skill> skills) {
        populateSkillCategories(skills);
        updateProfileEntities(currentProfile, skills, Skill.class);
    }

    protected void populateSkillCategories(List<Skill> skills) {
        List<SkillCategory> list = listSkillCategories();
        Map<Short, String> map = convertSkillCategoryListToMap(list);
        for (Skill skill : skills) {
            skill.setCategory(map.get(skill.getIdCategory()));
        }
    }

    protected Map<Short, String> convertSkillCategoryListToMap(List<SkillCategory> list) {
        Map<Short, String> map = new HashMap<>();
        for (SkillCategory category : list) {
            map.put(category.getIdCategory(), category.getCategory());
        }
        return map;
    }

    @Override
    public List<Language> listLanguages(CurrentProfile currentProfile) {
        return languageRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
    }

    @Override
    public Profile findProfileById(CurrentProfile currentProfile) {
        return profileRepository.findOne(currentProfile.getId());
    }

    @Override
    public Contacts findContactsById(CurrentProfile currentProfile) {
        return profileRepository.findOne(currentProfile.getId()).getContacts();
    }

    @Override
    public List<Practic> listPractics(CurrentProfile currentProfile) {
        return practicRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
    }

    @Override
    public List<Course> listCourses(CurrentProfile currentProfile) {
        return courseRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
    }

    @Override
    public List<Education> listEducations(CurrentProfile currentProfile) {
        return educationRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
    }

    @Override
    @Transactional
    public void updateInfo(CurrentProfile currentProfile, InfoForm form) {
        Profile loadedProfile = profileRepository.findOne(currentProfile.getId());
        if (!StringUtils.equals(loadedProfile.getInfo(), form.getInfo())) {
            loadedProfile.setInfo(form.getInfo());
            profileRepository.save(loadedProfile);
        } else {
            LOGGER.debug("Profile info not updated");
        }
    }

    @Override
    @Transactional
    public void updateLanguages(CurrentProfile currentProfile, List<Language> languages) {
        updateProfileEntities(currentProfile, languages, Language.class);
    }

    @Override
    @Transactional
    public void updateEducations(CurrentProfile currentProfile, List<Education> educations) {
        updateProfileEntities(currentProfile, educations, Education.class);
    }

    @Override
    @Transactional
    public void updateCourses(CurrentProfile currentProfile, List<Course> courses) {
        updateProfileEntities(currentProfile, courses, Course.class);
    }

    @Override
    @Transactional
    public void updatePractics(CurrentProfile currentProfile, List<Practic> practics) {
        updateProfileEntities(currentProfile, practics, Practic.class);
    }

    @Override
    @Transactional
    public void updateContacts(CurrentProfile currentProfile, Contacts contactsForm) {
        Profile loadedProfile = profileRepository.findOne(currentProfile.getId());
        int copiedFieldsCount = DataUtil.copyFields(contactsForm, loadedProfile.getContacts());
        boolean shouldProfileBeUpdated = copiedFieldsCount > 0;
        if (shouldProfileBeUpdated) {
            profileRepository.save(loadedProfile);
        } else {
            LOGGER.debug("Profile contacts not updated");
        }
    }

    @Override
    @Transactional
    public void updateProfilePhoto(CurrentProfile currentProfile, Profile profileForm) {
        Profile loadedProfile = profileRepository.findOne(currentProfile.getId());
        loadedProfile.setLargePhoto(profileForm.getLargePhoto());
        loadedProfile.setSmallPhoto(profileForm.getLargePhoto());
        profileRepository.save(loadedProfile);
    }


    @Override
    @Transactional
    public void updateProfileData(CurrentProfile currentProfile, Profile profileForm) {
        Profile loadedProfile = profileRepository.findOne(currentProfile.getId());
        loadedProfile.setBirthDay(profileForm.getBirthDay());
        loadedProfile.setCity(profileForm.getCity());
        loadedProfile.setCountry(profileForm.getCountry());
        loadedProfile.setFirstName(profileForm.getFirstName());
        loadedProfile.setLastName(profileForm.getLastName());
        loadedProfile.setObjective(profileForm.getObjective());
        loadedProfile.setPhone(profileForm.getPhone());
        loadedProfile.setEmail(profileForm.getEmail());
        loadedProfile.setSummary(profileForm.getSummary());
        loadedProfile.setCompleted(true);
        profileRepository.save(loadedProfile);
    }

    @Override
    @Transactional
    public void removeProfile(CurrentProfile currentProfile) {
        Profile profile = profileRepository.findOne(currentProfile.getId());
        profileRepository.delete(profile);
    }


    private <E extends ProfileEntity> void updateProfileEntities(CurrentProfile currentProfile, List<E> updatedData, Class<E> entityClass) {
        String collections = DataUtil.getCollectionName(entityClass);
        AbstractProfileEntityRepository<E> repository = findProfileEntityRepository(entityClass);
        List<E> profileData = repository.findByProfileIdOrderByIdAsc(currentProfile.getId());
        DataUtil.removeEmptyElements(updatedData);
        if (Comparable.class.isAssignableFrom(entityClass)) {
            Collections.sort((List<? extends Comparable>) updatedData);
        }
        if (DataUtil.areListsEqual(updatedData, profileData)) {
            LOGGER.debug("Profile {}: nothing to update", collections);
            return;
        } else {
            executeProfileEntitiesUpdate(currentProfile, repository, updatedData);
        }
    }

    private <E extends ProfileEntity> AbstractProfileEntityRepository<E> findProfileEntityRepository(Class<E> entityClass) {
        AbstractProfileEntityRepository<E> repository = (AbstractProfileEntityRepository<E>) profileEntityRepositoryMap.get(entityClass);
        if (repository == null) {
            throw new IllegalArgumentException("ProfileEntityRepository not found for entityClass=" + entityClass);
        }
        return repository;
    }

    private <E extends ProfileEntity> void executeProfileEntitiesUpdate(CurrentProfile currentProfile, AbstractProfileEntityRepository<E> repository, List<E> updatedData) {
        repository.deleteByProfileId(currentProfile.getId());
        repository.flush();
        Profile profileProxy = profileRepository.getOne(currentProfile.getId());
        for (E entity : updatedData) {
            entity.setId(null);
            entity.setProfile(profileProxy);
            repository.saveAndFlush(entity);
        }
    }

}