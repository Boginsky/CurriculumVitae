package resume.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import resume.service.EditProfileService;
import resume.service.util.SecurityUtil;

@Controller
public class RemoveProfileController {

    @Autowired
    private EditProfileService editProfileService;

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String showRemovePage() {
        return "remove";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeProfile() {
        editProfileService.removeProfile(SecurityUtil.getCurrentProfile());
        return "redirect:/sign-out";
    }
}