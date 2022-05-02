package pl.edu.pwr.java.lab8.model.mapper;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;

public class EventMapper {
    public static pl.edu.pwr.java.lab8.model.entity.Event map(pl.pwr.java.lab8.soap.events.Event event){
        pl.edu.pwr.java.lab8.model.entity.Event output = new pl.edu.pwr.java.lab8.model.entity.Event();
        output.setId(event.getId());
        XMLGregorianCalendar xmlDate = event.getDate();
        output.setDate(Date.valueOf(LocalDate.of(xmlDate.getYear(), xmlDate.getMonth(), xmlDate.getDay())));
        output.setName(event.getName());
        output.setPlace(event.getPlace());
        return output;
    }
    public static pl.pwr.java.lab8.soap.events.Event map(pl.edu.pwr.java.lab8.model.entity.Event event){
        pl.pwr.java.lab8.soap.events.Event output = new pl.pwr.java.lab8.soap.events.Event();
        output.setId(event.getId());
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(event.getDate());
        try {
            XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            output.setDate(xmlDate);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        output.setName(event.getName());
        output.setPlace(event.getPlace());
        return output;
    }
}
