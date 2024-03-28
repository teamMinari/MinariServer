package Minari.cheongForDo.domain.user.entity;

public class User {
    private Long id; // 유저 id

    private String name; // 유저 이름

    public Long getId() { // 유저 id getter, setter
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { // 유저 이름 getter, setter
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
