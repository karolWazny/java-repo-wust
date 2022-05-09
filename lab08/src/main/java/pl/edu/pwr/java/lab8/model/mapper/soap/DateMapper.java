package pl.edu.pwr.java.lab8.model.mapper.soap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;

public class DateMapper {
    public static XMLGregorianCalendar map(Date date){
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException("Dupa");
        }
    }

    public static Date map(XMLGregorianCalendar xmlDate){
        return Date.valueOf(LocalDate.of(xmlDate.getYear(), xmlDate.getMonth(), xmlDate.getDay()));
    }
}
