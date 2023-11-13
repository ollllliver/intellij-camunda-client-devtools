package de.hsrm.mi.ba.plugin.extensions.camunda.client;

import de.hsrm.mi.ba.plugin.extensions.camunda.model.ServerSettings;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OperateClient implements CamundaClient{

    private List<String> cookies = null;

    private String errorMessage;

    @Override
    public boolean testConnection(ServerSettings settings) {
        String template = "http%s://%s:%s/api/login?username=%s&password=%s";
        String authUrl = String.format(template, settings.isSsl() ? "s" : "", settings.getHost(),
                settings.getPort(), settings.getUser(), settings.getPassword());
        try {
            URL authUrlObject = new URL(authUrl);
            HttpURLConnection authConnection = (HttpURLConnection) authUrlObject.openConnection();
            authConnection.setRequestMethod("POST");

            int statusCode = authConnection.getResponseCode();
            if (statusCode < 200 || statusCode >= 300) {
                if (statusCode == 401){
                    errorMessage = "Authorisation failed.";
                } else {
                    errorMessage = String.format("Something failed. Status code: %d", statusCode);
                }
                return false;
            }
            cookies = authConnection.getHeaderFields().get("Set-Cookie");
        }catch (UnknownHostException e) {
            errorMessage = String.format("Make sure, that Operate is running at %s.", settings.getHost());
            return false;
        }catch (ConnectException e) {
            errorMessage = String.format("Make sure, that Operate is running on Port %d.", settings.getPort());
            return false;
        }catch (SSLException e) {
            errorMessage = "Make sure, that SSL ist configured correct";
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        for (String cookie : cookies) {
            System.out.println(cookie);
        }
        return true;
    }

    @Override
    public void getProcessDefinitions(){
        try {
            // Schritt 2: POST-Anfrage mit den erhaltenen Cookies
            String apiUrl = "http://localhost:8081/v1/process-definitions/search";
            URL apiUrlObject = new URL(apiUrl);
            HttpURLConnection apiConnection = (HttpURLConnection) apiUrlObject.openConnection();
            apiConnection.setRequestMethod("POST");

            // Setzen Sie die erhaltenen Cookies im Header
            for (String cookie : cookies) {
                apiConnection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
            }

            // Setzen Sie den Content-Type auf application/json
            apiConnection.setRequestProperty("Content-Type", "application/json");

            // Fügen Sie den Request-Body "{}" hinzu
            apiConnection.setDoOutput(true);
            try (OutputStream os = apiConnection.getOutputStream()) {
                byte[] input = "{}".getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Lesen Sie die Antwort
            BufferedReader apiReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
            String apiResponse;
            while ((apiResponse = apiReader.readLine()) != null) {
                System.out.println(apiResponse);
            }
            apiReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
