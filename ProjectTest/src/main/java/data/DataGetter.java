package data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class DataGetter {

    // Cantor Table
    // Table data getter
    public static DataTableCurrency[] getTableCurrencyData(LocalDate date){

        DataTableCurrency[] dataTableArray = new DataTableCurrency[0];

        try{

            // Creating HttpClient
            HttpClient client = HttpClient.newHttpClient();
            // Establishing connection, creating request
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/tables/c/" +
                    date.toString() + "/?format=json")).build();
            // Getting parsed data
            dataTableArray = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(DataGetter::parseToTableCurrency)
                    .join();

        } catch (Exception e){

            // Case there is no data
            System.out.println("No data. Choose another date.");

        }

        return dataTableArray;
    }

    // Table data parser
    public static DataTableCurrency[] parseToTableCurrency(String responseBody){

        // Get through json structure
        JSONArray jsonBody = new JSONArray(responseBody);
        JSONObject json0 = jsonBody.getJSONObject(0);
        JSONArray rates =  json0.getJSONArray("rates");

        DataTableCurrency[] dataTableArray = new DataTableCurrency[rates.length()];

        // Get currency data from json and assign values
        for (int i = 0; i < rates.length(); ++i){
            JSONObject currency = rates.getJSONObject(i);
            dataTableArray[i] = new DataTableCurrency(currency.getString("currency"), currency.getString("code"),
                    currency.getFloat("bid"), currency.getFloat("ask"));
        }

        return dataTableArray;
    }


    // Chart
    // Chart currency getter
    public static DataChart getChartCurrencyData(String code, LocalDate startDate, LocalDate endDate){

        DataChart dataCurrency = new DataChart();

        try {

            // Case period exceeds 92 days
            while (startDate.plusDays(92).isBefore(endDate)) {
                // Creating HttpClient
                HttpClient client = HttpClient.newHttpClient();
                // Establishing connection, creating request
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/" + code +
                        "/" + startDate + "/" + startDate.plusDays(91) + "/?format=json")).build();
                // Getting parsed data
                dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(DataGetter::parseToChartCurrency)
                        .join());
                startDate = startDate.plusDays(92);
            }

            // Creating HttpClient
            HttpClient client = HttpClient.newHttpClient();
            // Establishing connection, creating request
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/" + code +
                    "/" + startDate + "/" + endDate + "/?format=json")).build();
            // Getting parsed data
            dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(DataGetter::parseToChartCurrency)
                    .join());

        } catch (Exception e){

            // Case there is no data
            System.out.println("No data. Choose another date.");

        }

        return dataCurrency;
    }

    // Chart currency parser
    public static DataChart parseToChartCurrency(String responseBody){

        // Get through json structure
        JSONObject json0 =  new JSONObject(responseBody);
        JSONArray rates =  json0.getJSONArray("rates");

        DataChart dataCurrency = new DataChart();

        // Get currency data from json and assign values
        for (int i = 0; i < rates.length(); ++i){
            JSONObject currency = rates.getJSONObject(i);
            dataCurrency.appendToDates(LocalDate.parse(currency.getString("effectiveDate")));
            dataCurrency.appendToValues(currency.getFloat("mid"));
        }
        return dataCurrency;
    }

    // Chart gold getter
    public static DataChart getChartGoldData(LocalDate startDate, LocalDate endDate){

        DataChart dataCurrency = new DataChart();

        try {

            while (startDate.plusDays(92).isBefore(endDate)) {
                // Creating HttpClient
                HttpClient client = HttpClient.newHttpClient();
                // Establishing connection, creating request
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/cenyzlota/"
                        + startDate + "/" + startDate.plusDays(91) + "/?format=json")).build();
                // Getting parsed data
                dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(DataGetter::parseToChartGold)
                        .join());
                startDate = startDate.plusDays(92);
            }

            // Creating HttpClient
            HttpClient client = HttpClient.newHttpClient();
            // Establishing connection, creating request
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/cenyzlota/"
                    + startDate + "/" + endDate + "/?format=json")).build();
            // Getting parsed data
            dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(DataGetter::parseToChartGold)
                    .join());
        } catch (Exception e){

            // Case there is no data
            System.out.println("No data. Choose another date.");

        }

        return dataCurrency;
    }

    // Chart gold parser
    public static DataChart parseToChartGold(String responseBody){

        JSONArray json0 =  new JSONArray(responseBody);
        DataChart dataGold = new DataChart();

        // Get gold data from json and assign values
        for (int i = 0; i < json0.length(); ++i){
            JSONObject gold = json0.getJSONObject(i);
            dataGold.appendToDates(LocalDate.parse(gold.getString("data")));
            dataGold.appendToValues(gold.getFloat("cena"));
        }
        return dataGold;
    }

}