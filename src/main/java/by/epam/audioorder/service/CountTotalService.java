package by.epam.audioorder.service;

import by.epam.audioorder.entity.Bonus;
import by.epam.audioorder.entity.Track;

import java.util.List;

public class CountTotalService {
    public double countTotal(List<Track> cart, List<Bonus> bonuses) {
        double total = 0;
        for (Track track : cart) {
            double trackCost = track.getCost();
            if (bonuses != null) {
                for (Bonus bonus : bonuses) {
                    int after = bonus.getYearAfter();
                    int before = bonus.getYearBefore();
                    if ((after != 0 && after > track.getReleasedYear()) ||
                            (before != 0 && track.getReleasedYear() > before)) {
                       continue;
                    }
                    if (bonus.getBonusValue() >= trackCost) {
                        bonus.setBonusValue(bonus.getBonusValue() - trackCost);
                        trackCost = 0;
                    } else {
                        trackCost -= bonus.getBonusValue();
                        bonus.setBonusValue(0);
                    }
                    trackCost -= bonus.getSale() * trackCost;
                    total += trackCost;
                }
            }
            total += trackCost;
        }
        return total;
    }
}
