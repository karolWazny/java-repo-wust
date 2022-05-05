package pl.edu.pwr.java.lab8.model.mapper.soap;

public class EventMapper {
    public static pl.edu.pwr.java.lab8.model.entity.Event map(pl.pwr.java.lab8.soap.Event event){
        pl.edu.pwr.java.lab8.model.entity.Event output = new pl.edu.pwr.java.lab8.model.entity.Event();
        output.setId(event.getId());
        output.setDate(DateMapper.map(event.getDate()));
        output.setName(event.getName());
        output.setPlace(event.getPlace());
        return output;
    }
    public static pl.pwr.java.lab8.soap.Event map(pl.edu.pwr.java.lab8.model.entity.Event event){
        pl.pwr.java.lab8.soap.Event output = new pl.pwr.java.lab8.soap.Event();
        output.setId(event.getId());
        try {
            output.setDate(DateMapper.map(event.getDate()));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        output.setName(event.getName());
        output.setPlace(event.getPlace());
        return output;
    }
}
