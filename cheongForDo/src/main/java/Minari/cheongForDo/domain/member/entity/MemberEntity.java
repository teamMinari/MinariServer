package Minari.cheongForDo.domain.member.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;


@Entity
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotNull
    private Long id;

    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private String profileImage;

    @NotNull
    private String email;

    @NotNull
    private LocalDate registrationDate;

    @Nullable
    private int points;

    @Nullable
    @ManyToOne
    private Long vocaBook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profilePicture) {
        this.profileImage = profilePicture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getVocaBook() {
        return vocaBook;
    }

    public void setVocaBook(@Nullable Long vocaBook) {
        this.vocaBook = vocaBook;
    }
}