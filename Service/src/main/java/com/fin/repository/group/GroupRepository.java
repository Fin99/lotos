package com.fin.repository.group;

import com.fin.entity.group.Group;
import com.fin.repository.Repository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("groupRepository")
public class GroupRepository extends Repository {

    public List<Group> findGroup(Group groupData) {
        String query = "SELECT g FROM Group g WHERE g.name LIKE '%" + groupData.getName() + "%'";
        return getEntityManager().createQuery(query, Group.class).getResultList();
    }
}
