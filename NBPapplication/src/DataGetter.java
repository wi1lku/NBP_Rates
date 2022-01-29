import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class DataGetter {

    // TABLE
    // Table data getter
    public static DataTableCurrency[] getTableCurrencyData(LocalDate date){

        DataTableCurrency[] dataTableArray;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/tables/c/" +
                date.toString() + "/?format=json")).build();
        dataTableArray = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(DataGetter::parseToTableCurrency)
                .join();

        return dataTableArray;
    }

    // Table data parser
    public static DataTableCurrency[] parseToTableCurrency(String responseBody){

        JSONArray jsonBody = new JSONArray(responseBody);
        JSONObject json0 = jsonBody.getJSONObject(0);
        JSONArray rates =  json0.getJSONArray("rates");

        DataTableCurrency[] dataTableArray = new DataTableCurrency[rates.length()];

        for (int i = 0; i < rates.length(); ++i){
            JSONObject currency = rates.getJSONObject(i);
            dataTableArray[i] = new DataTableCurrency(currency.getString("currency"), currency.getString("code"),
                                                      currency.getFloat("bid"), currency.getFloat("ask"));
        }
        return dataTableArray;
    }

    // Table gold getter and parser
    public static DataTableGold getTableGoldData(LocalDate date){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/cenyzlota/" +
                date.toString() + "/?format=json")).build();
        String responseBodyGold = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        JSONArray jsonBodyGold = new JSONArray(responseBodyGold);
        JSONObject jsonObjectGold = jsonBodyGold.getJSONObject(0);
        return new DataTableGold(date, jsonObjectGold.getFloat("cena"));

    }

    // PLOT
    // Plot currency getter
    public static DataPlot getPlotCurrencyData(String code, LocalDate startDate, LocalDate endDate){

        DataPlot dataCurrency = new DataPlot();

        while(startDate.plusDays(92).isBefore(endDate)){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/" + code +
                    "/" + startDate + "/" + startDate.plusDays(91) + "/?format=json")).build();
            dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(DataGetter::parseToPlotCurrency)
                    .join());
            startDate = startDate.plusDays(92);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/" + code +
                "/" + startDate + "/" + endDate + "/?format=json")).build();
        dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(DataGetter::parseToPlotCurrency)
                .join());

        return dataCurrency;
    }

    // Plot currency parser
    public static DataPlot parseToPlotCurrency(String responseBody){

        JSONObject json0 =  new JSONObject(responseBody);
        JSONArray rates =  json0.getJSONArray("rates");

        DataPlot dataCurrency = new DataPlot();

        for (int i = 0; i < rates.length(); ++i){
            JSONObject currency = rates.getJSONObject(i);
            dataCurrency.appendToDates(LocalDate.parse(currency.getString("effectiveDate")));
            dataCurrency.appendToValues(currency.getFloat("mid"));
        }
        return dataCurrency;
    }

    // Plot gold getter
    public static DataPlot getPlotGoldData(LocalDate startDate, LocalDate endDate){

        DataPlot dataCurrency = new DataPlot();

        while(startDate.plusDays(92).isBefore(endDate)){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/cenyzlota/"
                    + startDate + "/" + startDate.plusDays(91) + "/?format=json")).build();
            dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(DataGetter::parseToPlotGold)
                    .join());
            startDate = startDate.plusDays(92);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/cenyzlota/"
                  + startDate + "/" + endDate + "/?format=json")).build();
        dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(DataGetter::parseToPlotGold)
                .join());

        return dataCurrency;
    }

    // Plot gold parser
    public static DataPlot parseToPlotGold(String responseBody){

        JSONArray json0 =  new JSONArray(responseBody);

        DataPlot dataGold = new DataPlot();

        for (int i = 0; i < json0.length(); ++i){
            JSONObject gold = json0.getJSONObject(i);
            dataGold.appendToDates(LocalDate.parse(gold.getString("data")));
            dataGold.appendToValues(gold.getFloat("cena"));
        }
        return dataGold;
    }


    // CALCULATOR
    // Calculator data getter
    public static DataCalculator getCalculatorData(String codeSell, String codeBuy, LocalDate date){

        DataCalculator dataCalculatorCurrencies = new DataCalculator();
        dataCalculatorCurrencies.setDate(date);
        dataCalculatorCurrencies.setCodeSell(codeSell);
        dataCalculatorCurrencies.setCodeBuy(codeBuy);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/c/" +
                codeSell + "/" + date.toString() + "/?format=json")).build();
        dataCalculatorCurrencies.addSell(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(DataGetter::parseToCalculatorCurrency)
                .join());

        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/c/" +
                codeBuy + "/" + date.toString() + "/?format=json")).build();
        dataCalculatorCurrencies.addBuy(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(DataGetter::parseToCalculatorCurrency)
                .join());

        return dataCalculatorCurrencies;
    }

    // Calculator data parser
    public static DataCalculator parseToCalculatorCurrency(String responseBody){

        JSONObject json0 =  new JSONObject(responseBody);
        JSONArray rates =  json0.getJSONArray("rates");

        DataCalculator dataCalculatorCurrencies = new DataCalculator();

        JSONObject currency = rates.getJSONObject(0);

        dataCalculatorCurrencies.setNameSell(json0.getString("currency"));
        dataCalculatorCurrencies.setValSell(currency.getFloat("bid"));
        dataCalculatorCurrencies.setNameBuy(json0.getString("currency"));
        dataCalculatorCurrencies.setValBuy(currency.getFloat("ask"));

        return dataCalculatorCurrencies;
    }

}
