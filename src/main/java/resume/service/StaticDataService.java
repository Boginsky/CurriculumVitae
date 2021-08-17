package resume.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


import resume.service.model.LanguageLevel;
import resume.service.model.LanguageType;


public interface StaticDataService {

     Collection<LanguageType> getAllLanguageTypes();

     Collection<LanguageLevel> getAllLanguageLevels();

     Map<Integer, String> mapMonths();

     List<Integer> listPracticsYears();

     List<Integer> listCourcesYears();

     List<Integer> listEducationYears();

}