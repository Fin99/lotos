package com.fin.ejb;

import com.fin.entity.Child;
import com.fin.entity.medical.DiseaseStrength;

import javax.ejb.Singleton;

@Singleton
public class FightEJB {
    // TODO add method to calculate fight and generate report

    public boolean isFightPossible(Child radiant, Child dire) {
        boolean radiantAbility = radiant.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        boolean direAbility = dire.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        return radiantAbility && direAbility;
    }

    public void startFight(Child radiant, Child dire) {
        // TODO

    }
}
