package by.epam.audioorder.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bonus {
    private long bonusId;
    private User user;
    private Genre genre;
    private int yearAfter;
    private int yearBefore;
    private double bonusValue;
    private double sale;
    private LocalDateTime expired;

    public long getBonusId() {
        return bonusId;
    }

    public void setBonusId(long bonusId) {
        this.bonusId = bonusId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getYearAfter() {
        return yearAfter;
    }

    public void setYearAfter(int yearAfter) {
        this.yearAfter = yearAfter;
    }

    public int getYearBefore() {
        return yearBefore;
    }

    public void setYearBefore(int yearBefore) {
        this.yearBefore = yearBefore;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public double getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(double bonusValue) {
        this.bonusValue = bonusValue;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bonus bonus = (Bonus) o;
        return bonusId == bonus.bonusId &&
                yearAfter == bonus.yearAfter &&
                yearBefore == bonus.yearBefore &&
                Double.compare(bonus.bonusValue, bonusValue) == 0 &&
                Double.compare(bonus.sale, sale) == 0 &&
                Objects.equals(user, bonus.user) &&
                genre == bonus.genre &&
                Objects.equals(expired, bonus.expired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bonusId, user, genre, yearAfter, yearBefore, bonusValue, sale, expired);
    }
}
