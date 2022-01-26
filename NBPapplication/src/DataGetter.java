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
    public static DataTableCurrency[] getTableData(LocalDate date){

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

    // PLOT
    // Plot data getter
    public static DataPlotCurrency getPlotCurrencyData(String code, LocalDate startDate, LocalDate endDate){

        DataPlotCurrency dataCurrency = new DataPlotCurrency();

        while(startDate.plusDays(92).isBefore(endDate)){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/" + code +
                    "/" + startDate + "/" + startDate.plusDays(91) + "/?format=json")).build();
            dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(DataGetter::parseToPLotCurrency)
                    .join());
            startDate = startDate.plusDays(92);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/" + code +
                "/" + startDate + "/" + endDate + "/?format=json")).build();
        dataCurrency.addList(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(DataGetter::parseToPLotCurrency)
                .join());

        return dataCurrency;
    }

    // Plot data parser
    public static DataPlotCurrency parseToPLotCurrency(String responseBody){

        JSONObject json0 =  new JSONObject(responseBody);
        JSONArray rates =  json0.getJSONArray("rates");

        DataPlotCurrency dataCurrency = new DataPlotCurrency();

        for (int i = 0; i < rates.length(); ++i){
            JSONObject currency = rates.getJSONObject(i);
            dataCurrency.appendToDates(LocalDate.parse(currency.getString("effectiveDate")));
            dataCurrency.appendToValues(currency.getFloat("mid"));
        }
        return dataCurrency;
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
