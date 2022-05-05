package pl.edu.pwr.java.lab8.model.mapper;

import pl.edu.pwr.java.lab8.lib.DataSet;
import pl.edu.pwr.java.lab8.model.entity.Event;
import pl.edu.pwr.java.lab8.model.entity.Installment;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataSetToInstallmentMapper {
    private String[][] data;
    private String[] headers;
    private Map<String, Integer> headersToIndexes;

    public List<Installment> map(DataSet dataSet){
        this.data = dataSet.getData();
        this.headers = dataSet.getHeader();
        mapHeadersToIndexes();
        List<Installment> installments = new LinkedList<>();

        for (String[] datum : data) {
            installments.add(installmentFrom(datum));
        }

        return installments;
    }

    private Installment installmentFrom(String[] values){
        Integer number = Integer.parseInt(values[headersToIndexes.get("installment_number")].trim());
        Date dueDate = Date.valueOf(values[headersToIndexes.get("due_date")].trim());
        Integer amount = Integer.parseInt(values[headersToIndexes.get("amount")].trim());
        Long eventId = Long.parseLong(values[headersToIndexes.get("event_id")].trim());
        Event event = new Event();
        event.setId(eventId);

        Installment installment = new Installment();
        installment.setInstallmentNumber(number);
        installment.setAmount(amount);
        installment.setDueDate(dueDate);
        installment.setEvent(event);

        return installment;
    }

    private void mapHeadersToIndexes(){
        headersToIndexes = new HashMap<>();
        for(int i = 0; i < headers.length; i++){
            headersToIndexes.put(headers[i], i);
        }
    }
}
