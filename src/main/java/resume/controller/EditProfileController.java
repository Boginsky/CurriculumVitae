package resume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import resume.persistence.entity.Contacts;
import resume.persistence.entity.Profile;
import resume.controller.exception.FormValidationException;
import resume.service.form.*;
import resume.service.model.CurrentProfile;
import resume.service.EditProfileService;
import resume.service.StaticDataService;
import resume.service.util.SecurityUtil;

import javax.validation.Valid;


@Controller
public class EditProfileController {

    @Autowired
    private StaticDataService staticDataService;

    @Autowired
    private EditProfileService editProfileService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditProfile(Model model) {
        model.addAttribute("profileForm", editProfileService.findProfileById(SecurityUtil.getCurrentProfile()));
        return "edit/profile";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEditProfile(@Valid @ModelAttribute("profileForm") Profile profileForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/my-profile";
        } else {
            try {
                editProfileService.updateProfilePhoto(SecurityUtil.getCurrentProfile(),profileForm);
                editProfileService.updateProfileData(SecurityUtil.getCurrentProfile(), profileForm);
                return "redirect:/my-profile";
            } catch (FormValidationException e) {
                bindingResult.addError(e.buildFieldError("profileForm"));
                return "edit/profile";
            }
        }
    }

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public String getEditTechSkills(Model model) {
        model.addAttribute("skillForm", new SkillForm(editProfileService.listSkills(SecurityUtil.getCurrentIdProfile())));
        return gotoSkillsJSP(model);
    }


    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    public String saveEditTechSkills(@Valid @ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return gotoSkillsJSP(model);
        } else {
            editProfileService.updateSkills(SecurityUtil.getCurrentProfile(), form.getItems());
            return "redirect:/my-profile";
        }
    }

    @RequestMapping(value = "/languages", method = RequestMethod.GET)
    public String getEditLanguages(Model model) {
        model.addAttribute("languageForm", new LanguageForm(editProfileService.listLanguages(SecurityUtil.getCurrentProfile())));
        return gotoLanguagesJSP(model);
    }

    @RequestMapping(value = "/languages", method = RequestMethod.POST)
    public String saveEditLanguages(@Valid @ModelAttribute("languageForm") LanguageForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return gotoLanguagesJSP(model);
        } else {
            editProfileService.updateLanguages(SecurityUtil.getCurrentProfile(), form.getItems());
            return "redirect:/my-profile";
        }
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String getEditContactsProfile(Model model) {
        model.addAttribute("contactsForm", editProfileService.findContactsById(SecurityUtil.getCurrentProfile()));
        return "contacts";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public String saveEditContactsProfile(@Valid @ModelAttribute("contactsForm") Contacts contactsForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "contacts";
        } else {
            editProfileService.updateContacts(SecurityUtil.getCurrentProfile(), contactsForm);
            return "redirect:/my-profile";
        }
    }

    @RequestMapping(value = "/practics", method = RequestMethod.GET)
    public String getEditPractics(Model model) {
        model.addAttribute("practicForm", new PracticForm(editProfileService.listPractics(SecurityUtil.getCurrentProfile())));
        return gotoPracticsJSP(model);
    }

    @RequestMapping(value = "/practics", method = RequestMethod.POST)
    public String saveEditPractics(@Valid @ModelAttribute("practicForm") PracticForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return gotoPracticsJSP(model);
        } else {
            editProfileService.updatePractics(SecurityUtil.getCurrentProfile(), form.getItems());
            return "redirect:/my-profile";
        }
    }

    private String gotoSkillsJSP(Model model) {
        model.addAttribute("skillCategories", editProfileService.listSkillCategories());
        return "edit/skills";
    }


    @RequestMapping(value = "/my-profile")
    public String getMyProfile(@AuthenticationPrincipal CurrentProfile currentProfile) {
        return "redirect:/" + currentProfile.getUsername();
    }

    private String gotoLanguagesJSP(Model model) {
        model.addAttribute("languageTypes", staticDataService.getAllLanguageTypes());
        model.addAttribute("languageLevels", staticDataService.getAllLanguageLevels());
        return "edit/languages";
    }

    private String gotoPracticsJSP(Model model) {
        model.addAttribute("years", staticDataService.listPracticsYears());
        model.addAttribute("months", staticDataService.mapMonths());
        return "edit/practics";
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String getEditCourses(Model model) {
        model.addAttribute("courseForm", new CourseForm(editProfileService.listCourses(SecurityUtil.getCurrentProfile())));
        return gotoCoursesJSP(model);
    }

    @RequestMapping(value = "/courses", method = RequestMethod.POST)
    public String saveEditCourses(@Valid @ModelAttribute("courseForm") CourseForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return gotoCoursesJSP(model);
        } else {
            editProfileService.updateCourses(SecurityUtil.getCurrentProfile(), form.getItems());
            return "redirect:/my-profile";
        }
    }

    private String gotoCoursesJSP(Model model) {
        model.addAttribute("years", staticDataService.listCourcesYears());
        model.addAttribute("months", staticDataService.mapMonths());
        return "edit/courses";
    }

    @RequestMapping(value = "/education", method = RequestMethod.GET)
    public String getEditEducation(Model model) {
        model.addAttribute("educationForm", new EducationForm(editProfileService.listEducations(SecurityUtil.getCurrentProfile())));
        return gotoEducationJSP(model);
    }

    @RequestMapping(value = "/education", method = RequestMethod.POST)
    public String saveEditEducation(@Valid @ModelAttribute("educationForm") EducationForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return gotoEducationJSP(model);
        } else {
            editProfileService.updateEducations(SecurityUtil.getCurrentProfile(), form.getItems());
            return "redirect:/my-profile";
        }
    }

    private String gotoEducationJSP(Model model) {
        model.addAttribute("years", staticDataService.listEducationYears());
        model.addAttribute("months", staticDataService.mapMonths());
        return "edit/education";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getEditProfileInfo(Model model) {
        model.addAttribute("infoForm", new InfoForm(editProfileService.findProfileById(SecurityUtil.getCurrentProfile())));
        return "edit/info";
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String saveEditProfileInfo(@Valid @ModelAttribute("infoForm") InfoForm infoForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit/info";
        } else {
            editProfileService.updateInfo(SecurityUtil.getCurrentProfile(), infoForm);
            return "redirect:/my-profile";
        }
    }
}
