package com.fin.ejb;

import com.fin.entity.medical.DiseaseStrength;
import com.fin.entity.medical.Ill;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Startup
@Singleton
public class IllEJB {
    private List<Ill> definedIlls;

    @PostConstruct
    private void initialize() {
        definedIlls = new ArrayList<>();

        definedIlls.add(new Ill("Синяк на руке", DiseaseStrength.MINIMUM));
        definedIlls.add(new Ill("Головная боль", DiseaseStrength.MINIMUM));
        definedIlls.add(new Ill("Царапина на ухе", DiseaseStrength.MINIMUM));
        definedIlls.add(new Ill("Покраснение глаза", DiseaseStrength.MINIMUM));

        definedIlls.add(new Ill("Диарея", DiseaseStrength.MEDIUM));
        definedIlls.add(new Ill("Спинная боль", DiseaseStrength.MEDIUM));
        definedIlls.add(new Ill("Боль в животе", DiseaseStrength.MEDIUM));
        definedIlls.add(new Ill("Кровоточивость десен", DiseaseStrength.MEDIUM));

        definedIlls.add(new Ill("Остеохондроз", DiseaseStrength.HARD));
        definedIlls.add(new Ill("Вывих челюсти", DiseaseStrength.HARD));
        definedIlls.add(new Ill("Перелом пальца", DiseaseStrength.HARD));
        definedIlls.add(new Ill("Перелом пальца", DiseaseStrength.HARD));
        definedIlls.add(new Ill("Рассечение брови", DiseaseStrength.HARD));

        definedIlls.add(new Ill("Вывих плеча", DiseaseStrength.CRITICAL));
        definedIlls.add(new Ill("Перелом бедра", DiseaseStrength.CRITICAL));
        definedIlls.add(new Ill("Лучевая болезнь", DiseaseStrength.CRITICAL));
        definedIlls.add(new Ill("Перелом переносицы", DiseaseStrength.CRITICAL));
        definedIlls.add(new Ill("Пробитие барабанной перепонки", DiseaseStrength.CRITICAL));
        definedIlls.add(new Ill("Злокачественное новообразование", DiseaseStrength.CRITICAL));
    }
}
