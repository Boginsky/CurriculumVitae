package resume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import resume.persistence.entity.Profile;
import resume.service.form.SignUpForm;
import resume.service.model.CurrentProfile;
import resume.service.EditProfileService;
import resume.service.FindProfileService;
import resume.service.util.SecurityUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

import static resume.service.constants.Constants.MAX_PROFILES_PER_PAGE;


@Controller
public class PublicDataController {

    @Autowired
    private FindProfileService findProfileService;

    @Autowired
    private EditProfileService editProfileService;


    @RequestMapping(value = "/{uid}")
    public String profile(@PathVariable String uid, Model model) {
        Profile profile = findProfileService.findByUid(uid);
        if (profile == null) {
            return "profile-not-found";
        } else if (!profile.isCompleted()) {
            CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
            if (currentProfile == null || !currentProfile.getId().equals(profile.getId())) {
                return "profile-not-found";
            } else {
                return "redirect:/edit";
            }
        } else {
            model.addAttribute("profile", profile);
            return "profile";
        }
    }

    @RequestMapping(value = { "/welcome" })
    public String listAll(Model model) {
        Page<Profile> profiles = findProfileService.findAll(new PageRequest(0, MAX_PROFILES_PER_PAGE, new Sort("id")));
        model.addAttribute("profiles", profiles.getContent());
        model.addAttribute("page", profiles);
        return "welcome";
    }

    @RequestMapping(value="/error", method=RequestMethod.GET)
    public String getError(){
        return "error";
    }


    @RequestMapping(value = "/fragment/more", method = RequestMethod.GET)
    public String moreProfiles(Model model,
                               @RequestParam(value="query", required=false) String query,
                               @PageableDefault(size=MAX_PROFILES_PER_PAGE) @SortDefault(sort="id") Pageable pageable) throws UnsupportedEncodingException {
        Page<Profile> profiles = null;
        profiles = findProfileService.findAll(pageable);
        model.addAttribute("profiles", profiles.getContent());
        return "fragment/profile-items";
    }

    @RequestMapping(value = "/sign-in")
    public String signIn() {
        return "sign-in";
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("profileForm", new SignUpForm());
        return "sign-up";
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public String signUp(@Valid @ModelAttribute("profileForm") SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        } else {
            Profile profile = editProfileService.createNewProfile(signUpForm);
            SecurityUtil.authentificateWithRememberMe(profile);
            return "redirect:/success";
        }
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String signUpSuccess() {
        return "sign-up-success";
    }

    @RequestMapping(value = "/error")
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/sign-in-failed")
    public String signInFailed(HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null) {
            return "redirect:/sign-in";
        } else {
            return "sign-in";
        }
    }
}
