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

        /*String startDate = (String) new BeanWrapperImpl(value).getPropertyValue(startDateFieldName);
        String endDate = (String) new BeanWrapperImpl(value).getPropertyValue(endDateFieldName);*/
        Object startDate = new BeanWrapperImpl(value).getPropertyValue(startDateFieldName);
        Object endDate = new BeanWrapperImpl(value).getPropertyValue(endDateFieldName);
        System.out.println("IS VALID METHOD ENTER");
        return isValidDateRange(String.valueOf(startDate), String.valueOf(endDate));
    }

    private boolean isValidDateRange(String start, String end) {
        System.out.println("IS VALID DATE RANGE METHOD ENTER");
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy"); // "EE MMM dd HH:mm:ss z yyyy" Fri Feb 26 00:00:00 CET 2021
        try {
            System.out.println("ENTER INSIDE TRY IN IS VALID DATE RANGE ");
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            if (startDate.before(endDate)) return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
    public static Object getProperty(Object bean, String propertyName) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor propertyDescriptor = Arrays
                .stream(beanInfo.getPropertyDescriptors())
                .filter(pd -> pd.getName().equals(propertyName)).findFirst()
                .get();
        return propertyDescriptor.getReadMethod().invoke(bean);
    }
    /*private Object getPropertyValue(Object bean, String property)
            throws IntrospectionException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Class<?> beanClass = bean.getClass();
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(
                beanClass, property);
        if (propertyDescriptor == null) {
            throw new IllegalArgumentException("No such property " + property
                    + " for " + beanClass + " exists");
        }

        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null) {
            throw new IllegalStateException("No getter available for property "
                    + property + " on " + beanClass);
        }
        return readMethod.invoke(bean);
    }

    private PropertyDescriptor getPropertyDescriptor(Class<?> beanClass,
                                                     String propertyname) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        PropertyDescriptor propertyDescriptor = null;
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor currentPropertyDescriptor = propertyDescriptors[i];
            if (currentPropertyDescriptor.getName().equals(propertyname)) {
                propertyDescriptor = currentPropertyDescriptor;
            }

        }
        return propertyDescriptor;
    }*/
}