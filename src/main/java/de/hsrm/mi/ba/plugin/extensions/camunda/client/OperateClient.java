package de.hsrm.mi.ba.plugin.extensions.camunda.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrm.mi.ba.plugin.extensions.camunda.client.dto.BpmnDefinitionsDTO;
import de.hsrm.mi.ba.plugin.extensions.camunda.client.dto.ProcessDefinitionsInfoDTO;
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

public class OperateClient implements CamundaClient {

    private List<String> cookies = null;
    private String baseUrl;
    private String errorMessage;

    @Override
    public boolean testConnection(ServerSettings settings) {
        baseUrl = String.format("http%s://%s:%s", settings.isSsl() ? "s" : "", settings.getHost(), settings.getPort());
        String template = "%s/api/login?username=%s&password=%s";
        String authUrl = String.format(template, baseUrl, settings.getUser(), settings.getPassword());
        try {
            URL authUrlObject = new URL(authUrl);
            HttpURLConnection authConnection = (HttpURLConnection) authUrlObject.openConnection();
            authConnection.setRequestMethod("POST");

            int statusCode = authConnection.getResponseCode();
            if (statusCode < 200 || statusCode >= 300) {
                if (statusCode == 401) {
                    errorMessage = "Authorisation failed.";
                } else {
                    errorMessage = String.format("Something failed. Status code: %d", statusCode);
                }
                return false;
            }
            cookies = authConnection.getHeaderFields().get("Set-Cookie");
        } catch (UnknownHostException e) {
            errorMessage = String.format("Make sure, that Operate is running at %s.", settings.getHost());
            return false;
        } catch (ConnectException e) {
            errorMessage = String.format("Make sure, that Operate is running on Port %d.", settings.getPort());
            return false;
        } catch (SSLException e) {
            errorMessage = "Make sure, that SSL ist configured correct";
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ProcessDefinitionsInfoDTO getProcessDefinitions() {
        try {
            String template = "%s/v1/process-definitions/search";
            String apiUrl = String.format(template, baseUrl);
            URL apiUrlObject = new URL(apiUrl);
            HttpURLConnection apiConnection = (HttpURLConnection) apiUrlObject.openConnection();
            apiConnection.setRequestMethod("POST");

            for (String cookie : cookies) {
                apiConnection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
            }

            apiConnection.setRequestProperty("Content-Type", "application/json");

            apiConnection.setDoOutput(true);
            OutputStream os = apiConnection.getOutputStream();
            byte[] input = "{}".getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader apiReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
            StringBuilder responseStringBuilder = new StringBuilder();
            String apiResponseLine;
            while ((apiResponseLine = apiReader.readLine()) != null) {
                responseStringBuilder.append(apiResponseLine);
            }
            apiReader.close();

            String apiResponse = responseStringBuilder.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(apiResponse, ProcessDefinitionsInfoDTO.class);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BpmnDefinitionsDTO getProcess(long key) {
        try {
            String template = "%s/api/processes/%s/xml";
            String processUrl = String.format(template, baseUrl, key);
            URL processUrlObject = new URL(processUrl);
            HttpURLConnection processUrlConnection = (HttpURLConnection) processUrlObject.openConnection();
            processUrlConnection.setRequestMethod("GET");

            for (String cookie : cookies) {
                processUrlConnection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
            }

            processUrlConnection.setRequestProperty("Content-Type", "application/json");

            BufferedReader apiReader = new BufferedReader(new InputStreamReader(processUrlConnection.getInputStream()));
            StringBuilder responseStringBuilder = new StringBuilder();
            String apiResponseLine;
            while ((apiResponseLine = apiReader.readLine()) != null) {
                responseStringBuilder.append(apiResponseLine);
            }
            apiReader.close();

            String xmlString = responseStringBuilder.toString();
            ObjectMapper xmlMapper = new XmlMapper();
            return(xmlMapper.readValue(xmlString, BpmnDefinitionsDTO.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
