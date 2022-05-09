package pl.edu.pwr.java.lab8.model.mapper.soap;

import pl.edu.pwr.java.lab8.model.entity.Event;
import pl.edu.pwr.java.lab8.model.entity.Person;
import pl.pwr.java.lab8.soap.Payment;

public class PaymentMapper {
    public static Payment map(pl.edu.pwr.java.lab8.model.entity.Payment payment){
        Payment output = new Payment();
        output.setId(payment.getId());
        output.setAmount(payment.getAmount());
        output.setEventId(payment.getEvent().getId());
        output.setDatePaid(DateMapper.map(payment.getDatePaid()));
        output.setPersonId(payment.getPerson().getId());
        output.setInstallmentNumber(payment.getInstallmentNumber());
        return output;
    }

    public static pl.edu.pwr.java.lab8.model.entity.Payment map(Payment payment){
        pl.edu.pwr.java.lab8.model.entity.Payment output = new pl.edu.pwr.java.lab8.model.entity.Payment();
        output.setId(payment.getId());
        output.setEvent(new Event(payment.getEventId(), null, null, null));
        output.setAmount(payment.getAmount());
        output.setInstallmentNumber(payment.getInstallmentNumber());
        output.setDatePaid(DateMapper.map(payment.getDatePaid()));
        output.setPerson(new Person(payment.getPersonId(), null, null));
        return output;
    }
}
