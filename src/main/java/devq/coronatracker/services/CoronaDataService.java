package devq.coronatracker.services;

import devq.coronatracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaDataService {

    @Value("${data.url}")
    private String dataUrl;

    private List<LocationStats> locationStatsList = new ArrayList();
    private int totalReportCaseCount = 0;

    @PostConstruct
    @Scheduled(cron="* * 1  * * *")
    public void fetchCoronaData() {

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(dataUrl))
                    .build();
            HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            StringReader csvReader = new StringReader(httpResponse.body().toString());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
            for (CSVRecord record : records) {
                LocationStats locationStats = new LocationStats();
                locationStats.setState(record.get("Province/State"));
                locationStats.setCountry(record.get("Country/Region"));
                int latestCount = Integer.parseInt(record.get(record.size() - 1));
                locationStats.setLatestReportCaseNumber(latestCount);
                locationStatsList.add(locationStats);

                totalReportCaseCount += latestCount;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LocationStats> getLocationStatsList() {
        return locationStatsList;
    }

    public int getTotalReportCaseCount() {
        return totalReportCaseCount;
    }
}
