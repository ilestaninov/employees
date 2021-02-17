package thymealeaf.demo.validation;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {
    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(final DateRange constraintAnnotation) {
        this.startDateFieldName = constraintAnnotation.absence_start();
        this.endDateFieldName = constraintAnnotation.absence_end();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Object startDate = new BeanWrapperImpl(value).getPropertyValue(startDateFieldName);
        Object endDate = new BeanWrapperImpl(value).getPropertyValue(endDateFieldName);
        return isValidDateRange(String.valueOf(startDate), String.valueOf(endDate));
    }

    private boolean isValidDateRange(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy"); // "EE MMM dd HH:mm:ss z yyyy" Fri Feb 26 00:00:00 CET 2021
        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            if (startDate.before(endDate) || startDate.equals(endDate)) return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

}