package resume.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import resume.service.model.LanguageLevel;
import resume.service.model.LanguageType;
import resume.service.StaticDataService;

import java.util.*;

@Service
public class StaticDataServiceImpl implements StaticDataService {

    @Value("${practic.years.ago}")
    private int practicYearsAgo;

    @Value("${education.years.ago}")
    private int educationYearsAgo;

    @Override
    public Collection<LanguageType> getAllLanguageTypes() {
        return EnumSet.allOf(LanguageType.class);
    }

    @Override
    public Collection<LanguageLevel> getAllLanguageLevels() {
        return EnumSet.allOf(LanguageLevel.class);
    }

    @Override
    public Map<Integer, String> mapMonths() {
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        Map<Integer, String> map = new LinkedHashMap<>();
        for (int i = 0; i < months.length; i++) {
            map.put(i + 1, months[i]);
        }
        return map;
    }

    @Override
    public List<Integer> listPracticsYears() {
        return listYears(practicYearsAgo);
    }

    @Override
    public List<Integer> listCourcesYears() {
        return listYears(practicYearsAgo);
    }

    @Override
    public List<Integer> listEducationYears() {
        return listYears(educationYearsAgo);
    }

    protected List<Integer> listYears(int count) {
        List<Integer> years = new ArrayList<>();
        int now = DateTime.now().getYear();
        for (int i = 0; i < count; i++) {
            years.add(now - i);
        }
        return years;
    }

}