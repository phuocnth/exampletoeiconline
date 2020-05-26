package vn.myclass.core.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "roleEntity", fetch = FetchType.LAZY)
    private List<UserEntity> userEntityList;


    public RoleEntity() {
    }

    public RoleEntity(Integer roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUsersList() {
        return userEntityList;
    }

    public void setUsersList(List<UserEntity> usersList) {
        this.userEntityList = usersList;
    }
}
