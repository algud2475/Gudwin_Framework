package utils;

import helpers.exeptions.DataException;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.fetch.Fetch;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.RequestId;
import org.openqa.selenium.devtools.v129.network.model.RequestWillBeSent;
import org.openqa.selenium.devtools.v129.network.model.ResponseReceived;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static browser.CDPEnvironmentHelper.getNewDevTools;

public class DevToolsUtil {
    /*
    private List<ResponseReceived> responseList;
    private List<RequestWillBeSent> requestList;
    private static DevTools devTools = null;

    public static DevTools getDevTools() {
        if (devTools == null) {
            return getNewDevTools();
        }
        return devTools;
    }

    public List<ResponseReceived> getResponseList() {
        if (this.responseList == null) {
            this.responseList = new ArrayList<>();
        }
        return responseList;
    }

    public List<RequestWillBeSent> getRequestListList() {
        if (this.requestList == null) {
            this.requestList = new ArrayList<>();
        }
        return requestList;
    }

     */

    /**
     * Подключение слушателя
     */
    /*
    public void setNetworkListener() {
        devTools = getDevTools();
        devTools.createSession();;
        devTools.send(Network.enable(
                Optional.of(100000000),
                Optional.empty(),
                Optional.of(100000000)
        ));
        responseList = new ArrayList<>();
        requestList = new ArrayList<>();
        devTools.addListener(Network.responseReceived(), responseReceived ->
                responseList.add(responseReceived));
        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent ->
                requestList.add(requestWillBeSent));
    }

     */

    /**
     * Закрытие DevTools
     */
    /*
    public void clearNetwork() {
        if (devTools != null) {
            try {
                devTools.clearListeners();
                devTools.send(Network.disable());
                devTools.send(Fetch.disable());
                devTools.disconnectSession();
            } catch (TimeoutException ignored) {
            }
        }
        devTools = null;
        responseList = null;
    }

     */

    /**
     * Поиск API-запроса по части url и получение его ответа
     */
    /*
    @Step("Получение тела network ответа с url '{url}'")
    public String getResponseByUrl(String url) {
        //import org.awaitility.Awaitility;
        Awaitility.await()
                .atMost(Duration.ofSeconds(30L))
                .pollInterval(Duration.ofSeconds(2L))
                .pollInSameThread()
                .until(() -> responseList.size() > 0
                        && responseList.stream().anyMatch(resp -> resp.getResponse().getUrl().contains(url)));
        ResponseReceived response = responseList.stream()
                .filter(resp -> resp.getResponse().getUrl().contains(url)).findFirst()
                .orElseThrow(() -> new DataException("Запись с url " + url + " не найдена"));
        RequestId id = response.getRequestId();
        return devTools.send(Network.getResponseBody(id)).getBody();

    }
    */
}
