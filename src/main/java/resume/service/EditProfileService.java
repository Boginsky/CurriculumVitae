package resume.service;

import resume.persistence.entity.*;
import resume.service.form.InfoForm;
import resume.service.form.SignUpForm;
import resume.service.model.CurrentProfile;

import java.util.List;


public interface EditProfileService {

    Profile createNewProfile(SignUpForm signUpForm);

    List<Skill> listSkills(long idProfile);

    List<SkillCategory> listSkillCategories();

    void updateSkills( CurrentProfile currentProfile, List<Skill> skills);

    List<Language> listLanguages(CurrentProfile currentProfile);

    Profile findProfileById(CurrentProfile currentProfile);

    Contacts findContactsById(CurrentProfile currentProfile);

    List<Practic> listPractics(CurrentProfile currentProfile);

    List<Course> listCourses(CurrentProfile currentProfile);

    List<Education> listEducations(CurrentProfile currentProfile);

    void updateInfo(CurrentProfile currentProfile, InfoForm form);

    void updateLanguages(CurrentProfile currentProfile, List<Language> languages);

    void updateEducations(CurrentProfile currentProfile, List<Education> educations);

    void updateCourses(CurrentProfile currentProfile, List<Course> courses);

    void updatePractics(CurrentProfile currentProfile, List<Practic> practics);

    void updateContacts(CurrentProfile currentProfile, Contacts data);

    void updateProfileData(CurrentProfile currentProfile, Profile data);

    void updateProfilePhoto(CurrentProfile currentProfile, Profile data);

    void removeProfile( CurrentProfile currentProfile);


}
