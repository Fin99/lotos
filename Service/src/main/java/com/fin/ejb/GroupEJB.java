package com.fin.ejb;

import com.fin.entity.Jsonable;
import com.fin.entity.group.Group;
import com.fin.repository.MainRepository;
import com.fin.repository.group.GroupRepository;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.List;

@Getter
@Setter
@Stateless
public class GroupEJB {

    @Inject
    private MainRepository mainRepository;

    @Inject
    private GroupRepository groupRepository;

    public JsonObject getGroups() {
        List<Group> groups = mainRepository.findAll(Group.class);
        return Jsonable.wrapList(groups).asJsonObject();
    }

    public boolean addGroup(Group group) {
        Group received = groupRepository.findByName(group.getName());
        if (received != null) {
            return false;
        }
        mainRepository.create(group);
        return true;
    }

    public boolean removeGroup(long id) {
        if (mainRepository.find(Group.class, id) == null) {
            return false;
        }
        mainRepository.remove(Group.class, id);
        return true;
    }

    public boolean updateGroup(Group group) {
        if (mainRepository.find(Group.class, group.getId()) == null) {
            return false;
        }
        mainRepository.update(group);
        return true;
    }

}
