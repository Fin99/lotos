package com.fin.ejb;

import com.fin.entity.Child;
import com.fin.entity.game.Fight;
import com.fin.entity.game.Fighter;
import com.fin.entity.game.Hit;
import com.fin.entity.game.HitDirection;
import com.fin.entity.medical.DiseaseStrength;
import com.fin.entity.medical.Ill;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.json.JsonObject;
import java.util.Random;

@Getter
@Setter
@Singleton
public class FightEJB {

    @EJB
    private IllEJB illEJB;

    public boolean isFightPossible(Child radiant, Child dire) {
        boolean radiantAbility = radiant.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        boolean direAbility = dire.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        return radiantAbility && direAbility;
    }

    public JsonObject generateReport(Child firstChild, Child secondChild) {
        Fighter radiant;
        Fighter dire;
        if (Math.random() < 0.5) {
            radiant = new Fighter(firstChild);
            dire = new Fighter(secondChild);
        } else {
            radiant = new Fighter(secondChild);
            dire = new Fighter(firstChild);
        }

        Fight fight = new Fight(radiant, dire);
        for (double time = 0; radiant.getHp() > 0 || dire.getHp() > 0; time += 0.1) {
            if (time % radiant.getCooldown() < 0.01) {
                fight.getHitList().add(initializeHit(radiant, dire, HitDirection.TO_DIRE, fight, time));
                if (dire.getHp() <= 0) {
                    break;
                }
            }

            if (time % dire.getCooldown() < 0.01) {
                fight.getHitList().add(initializeHit(radiant, dire, HitDirection.TO_RADIANT, fight, time));
                if (radiant.getHp() <= 0) {
                    break;
                }
            }
        }

        Fighter loser = radiant.getHp() > 0 ? dire : radiant;
        Random rand = new Random();
        Ill ill = illEJB.getDefinedIlls().get(rand.nextInt(illEJB.getDefinedIlls().size()));
        loser.getChild().getMedicalBook().getIllSet().add(ill);

        return fight.toJson();
    }

    private Hit initializeHit(Fighter radiant, Fighter dire, HitDirection hitDirection, Fight fight, double time) {
        Hit hit = new Hit();
        hit.setTime(time);
        hit.setHitDirection(hitDirection);
        hit.setFight(fight);
        if (hitDirection == HitDirection.TO_RADIANT) {
            attack(dire, radiant, hit);
        } else {
            attack(radiant, dire, hit);
        }
        return hit;
    }

    private void attack(Fighter attacker, Fighter defender, Hit hit) {
        double damage = attacker.getDamage();
        if (Math.random() < attacker.getCriticalChance()) {
            damage *= 2;
            hit.setCritical(true);
        }
        if (Math.random() < defender.getBlockChance()) {
            damage *= 0.8;
            hit.setBlocked(true);
        }
        defender.setHp(defender.getHp() - damage > 0 ? defender.getHp() - damage : 0);
    }
}
