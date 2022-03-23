package lib;

public interface DataSource {
    String[] getContinents();
    String[] getCountries(String continent);
    String[] getAdminDivisions(String country);
}
