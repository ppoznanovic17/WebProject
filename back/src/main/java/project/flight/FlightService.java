package project.flight;

import java.util.List;

public class FlightService {

    public List<String> getComboBoxFlight(){
       return FlightRepository.getInstance().getComboBoxFlight();
    }
}
