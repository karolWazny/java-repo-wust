package pl.edu.pwr.java.lab8.model.mapper.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pwr.java.lab8.model.entity.Event;
import pl.edu.pwr.java.lab8.model.entity.Installment;

@Component
@RequiredArgsConstructor
public class InstallmentMapper {
    public static Installment map(pl.pwr.java.lab8.soap.Installment installment){
        Installment output = new Installment();
        output.setId(installment.getId());
        output.setInstallmentNumber(installment.getInstallmentNumber());
        output.setAmount(installment.getAmount());
        Event event = new Event();
        event.setId(installment.getEventId());
        output.setEvent(event);
        output.setDueDate(DateMapper.map(installment.getDueDate()));
        return output;
    }

    public static pl.pwr.java.lab8.soap.Installment map(Installment installment){
        pl.pwr.java.lab8.soap.Installment output = new pl.pwr.java.lab8.soap.Installment();
        output.setAmount(installment.getAmount());
        output.setId(installment.getId());
        output.setInstallmentNumber(installment.getInstallmentNumber());
        output.setDueDate(DateMapper.map(installment.getDueDate()));
        output.setEventId(installment.getEvent().getId());
        return output;
    }
}
