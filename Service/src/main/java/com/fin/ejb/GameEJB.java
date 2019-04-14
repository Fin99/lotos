package com.fin.ejb;

import com.fin.entity.Children;
import com.fin.entity.medical.DiseaseStrength;

import javax.ejb.Singleton;

@Singleton
public class GameEJB {
    // TODO add method to calculate fight and generate report
    // TODO add method to get the reason why the fight can't start

    public boolean isFightPossible(Children radiant, Children dire) {
        boolean radiantAbility = radiant.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        boolean direAbility = dire.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        return radiantAbility && direAbility;
    }

    public void startFight(Children radiant, Children dire) {
        // TODO
    }
}
