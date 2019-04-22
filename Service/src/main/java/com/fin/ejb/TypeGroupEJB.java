package com.fin.ejb;

import com.fin.entity.Jsonable;
import com.fin.entity.group.TypeGroup;
import com.fin.repository.MainRepository;
import com.fin.repository.group.TypeGroupRepository;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.List;

@Getter
@Setter
@Stateless
public class TypeGroupEJB {

    @Inject
    private MainRepository mainRepository;

    @Inject
    private TypeGroupRepository typeGroupRepository;

    public JsonObject getGroupTypes() {
        List<TypeGroup> groupTypes = mainRepository.findAll(TypeGroup.class);
        return Jsonable.wrapList(groupTypes).asJsonObject();
    }

    public boolean addGroupType(TypeGroup typeGroup) {
        TypeGroup received = typeGroupRepository.findGroupTypeByName(typeGroup.getName());
        if (received != null) {
            return false;
        }
        mainRepository.create(typeGroup);
        return true;
    }
}
