package com.fin.repository.group;

import com.fin.entity.group.TypeGroup;
import com.fin.repository.MainRepository;
import com.fin.repository.Repository;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("typeGroupRepository")
public class TypeGroupRepository extends Repository {

    public TypeGroup findByName(String name) {
        String query = "SELECT t FROM TypeGroup t WHERE t.name='" + name + "'";
        return MainRepository.getElementOrNull(getEntityManager().createQuery(query, TypeGroup.class).getResultList());
    }

}
