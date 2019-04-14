package com.fin.ejb;

import com.fin.entity.Child;
import com.fin.entity.game.Fight;
import com.fin.entity.game.Fighter;
import com.fin.entity.medical.DiseaseStrength;

import javax.ejb.Singleton;
import javax.json.JsonObject;

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

    public JsonObject generateReport(Child radiant, Child dire) {
        Fighter radiantFighter = new Fighter(radiant);
        Fighter direFighter = new Fighter(dire);
        Fight fight = new Fight(radiantFighter, direFighter);

        // TODO
        return null;
    }
}
