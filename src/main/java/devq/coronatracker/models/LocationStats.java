package devq.coronatracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestReportCaseNumber;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestReportCaseNumber() {
        return latestReportCaseNumber;
    }

    public void setLatestReportCaseNumber(int latestReportCaseNumber) {
        this.latestReportCaseNumber = latestReportCaseNumber;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestReportCaseNumber=" + latestReportCaseNumber +
                '}';
    }
}
