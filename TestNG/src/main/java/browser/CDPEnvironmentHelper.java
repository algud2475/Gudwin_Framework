package browser;

import org.openqa.selenium.devtools.*;
import org.openqa.selenium.devtools.noop.NoOpCdpInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

public class CDPEnvironmentHelper {
    /*
    private static HttpClient.Factory getFactory() {
        return HttpClient.Factory.createDefault();
    }

    private static Optional<URI> getCdpEndPoint() throws URISyntaxException {
        RemoteWebDriver webDriver = (RemoteWebDriver) DriverUtil.getDriver();
        return Optional.of(new URI(webDriver.getCapabilities().getCapability("se:cdp").toString()));
    }

    private static ClientConfig getClientConfig(URI uri) {
        return ClientConfig.defaultConfig().baseUri(uri);
    }

    private static Optional<Connection> getChromeConnector(HttpClient.Factory clientFactory) {
        Optional<URI> uri;
        try {
            uri = getCdpEndPoint();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return uri.map(value -> new Connection(
                clientFactory.createClient(getClientConfig(value)),
                value.toString())
        );

    }

    public static DevTools getNewDevTools() {
        HttpClient.Factory factory = getFactory();
        Optional<Connection> connection = getChromeConnector(factory);
        CdpInfo cdpInfo = (new CdpVersionFinder()).match("129.0")
                .orElseGet(NoOpCdpInfo::new);

        Optional<DevTools> devTools = connection.map((conn) -> {
            Objects.requireNonNull(cdpInfo);
            return new DevTools(cdpInfo::getDomains, conn);
        });

        if (devTools.isPresent()) {
            return devTools.get();
        } else {
            throw new DevToolsException("Не удалось создать DevTools");
        }
    }

     */
}
