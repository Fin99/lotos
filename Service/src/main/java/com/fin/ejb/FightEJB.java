package com.fin.ejb;

import com.fin.dto.BetDto;
import com.fin.entity.Bet;
import com.fin.entity.Child;
import com.fin.entity.Parent;
import com.fin.entity.game.Fight;
import com.fin.entity.game.Fighter;
import com.fin.entity.game.Hit;
import com.fin.entity.game.HitDirection;
import com.fin.entity.medical.DiseaseStrength;
import com.fin.entity.medical.Ill;
import com.fin.entity.money.Wallet;
import com.fin.repository.MainRepository;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Singleton
public class FightEJB {

    @EJB
    private IllEJB illEJB;

    @Inject
    private MainRepository mainRepository;

    private Fight lastFight;

    private double radiantCoefficient;
    private double direCoefficient;

    private boolean isBetTaken;
    private List<Bet> bets;

    public Fight getLastFight() {
        if (isBetTaken) {
            return lastFight;
        }

        Date now = new Date();
        if (now.getTime() - lastFight.getStartDate().getTime() > 5 * 60 * 1000) {
            isBetTaken = true;
            generateReport(lastFight);
            return lastFight;
        } else {
            return null;
        }
    }

    public boolean isFightPossible(Child radiant, Child dire) {
        boolean radiantAbility = radiant.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        boolean direAbility = dire.getMedicalBook().getIllSet().stream()
                .noneMatch(ill -> ill.getDiseaseStrength() == DiseaseStrength.CRITICAL);
        return radiantAbility && direAbility;
    }

    private void calculateCoefficients(Fighter radiant, Fighter dire) {
        radiantCoefficient = (dire.getStrength() + dire.getAgility() + dire.getIntellect()) /
                (radiant.getStrength() + radiant.getAgility() + radiant.getIntellect()) + 1;
        direCoefficient = (radiant.getStrength() + radiant.getAgility() + radiant.getIntellect()) /
                (dire.getStrength() + dire.getAgility() + dire.getIntellect()) + 1;
    }

    public boolean placeBet(BetDto betDto) {
        if (isBetTaken) {
            return false;
        }
        Parent parent = mainRepository.find(Parent.class, betDto.getParentId());
        Child child = mainRepository.find(Child.class, betDto.getChildId());
        if (child == null || parent == null || parent.getWallet().getAccount() < betDto.getRateAmount()) {
            return false;
        }

        if (lastFight.getDire().getChild().getId() == betDto.getChildId()) {
            bets.add(new Bet(parent, direCoefficient, lastFight.getDire(), betDto.getRateAmount()));
            return true;
        } else if (lastFight.getRadiant().getChild().getId() == betDto.getChildId()) {
            bets.add(new Bet(parent, radiantCoefficient, lastFight.getRadiant(), betDto.getRateAmount()));
            return true;
        } else {
            return false;
        }
    }

    public void startPreparation(Child firstChild, Child secondChild) {
        isBetTaken = false;
        bets.clear();

        Fighter radiant;
        Fighter dire;
        if (Math.random() < 0.5) {
            radiant = new Fighter(firstChild);
            dire = new Fighter(secondChild);
        } else {
            radiant = new Fighter(secondChild);
            dire = new Fighter(firstChild);
        }

        calculateCoefficients(radiant, dire);
        lastFight = new Fight(radiant, dire);
    }

    private void generateReport(Fight fight) {
        Fighter radiant = fight.getRadiant();
        Fighter dire = fight.getDire();

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

        giveWinnersMoney(loser);
    }

    private void giveWinnersMoney(Fighter loser) {
        bets.forEach(bet -> {
            if (bet.getFighter().getChild().getId() != loser.getChild().getId()) {
                Wallet wallet = bet.getParent().getWallet();
                wallet.setAccount(wallet.getAccount() + bet.getRateAmount() * bet.getCoefficient());
                mainRepository.update(wallet);
            }
        });
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
        hit.setDamage(damage);
        defender.setHp(defender.getHp() - damage > 0 ? defender.getHp() - damage : 0);
    }
}
