package lib;

import lib.models.Territory;

import java.util.List;

public interface DataSource {
    List<Territory> getContinents();

    List<Territory> getCountries(Territory territory);

    String[] getAdminDivisions(String country);
}
