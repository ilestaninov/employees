package thymealeaf.demo.helpers;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Helpers {

    public int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        daysdiff = (int) diffDays;
        return daysdiff;
    }
}
