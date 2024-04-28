package com.pepop99.emaildispatcher.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.pepop99.emaildispatcher.metadata.AppMeta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EmailHandler extends BaseHandler {
    @Override
    public void process(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        final String uri = httpServletRequest.getRequestURI();
        switch (uri) {
            case (APIHandlerConstants.URI_SEND_EMAIL) -> {
                final LocalDate currentDate = LocalDate.now();
                final LocalTime currentTime = LocalTime.now();
                final String body = readAsString(httpServletRequest);
                JsonElement jsonElement = JsonParser.parseString(body);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonObject fd = jsonObject.getAsJsonObject(APIHandlerConstants.FOUNDATION);
                final String foundationEmail = getValue(fd, "email");
                for (JsonElement element : jsonObject.get(APIHandlerConstants.NONPROFITS).getAsJsonArray()) {
                    final String nonprofitEmail = getValue(element.getAsJsonObject(), "email");
                    final String nonprofitName = getValue(element.getAsJsonObject(), "name");
                    final String nonprofitAddress = getValue(element.getAsJsonObject(), "address");
                    String emailMessage = String.format("Email from Foundation: %s. Sending money to nonprofit %s(%s) at address %s on %s %s.%n", foundationEmail, nonprofitName, nonprofitEmail, nonprofitAddress, currentDate, currentTime);
                    AppMeta.instance.emailMap.computeIfAbsent(nonprofitEmail, s1 -> new ArrayList<>()).add(emailMessage);
                    // use some external api to send email (Sendgrid).
                    System.out.printf(emailMessage);
                }
                respond("Email sent", httpServletResponse, 200);
            }
            case (APIHandlerConstants.URI_VIEW_EMAIL) -> {
                final String body = readAsString(httpServletRequest);
                JsonElement jsonElement = JsonParser.parseString(body);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                ArrayList<String> emails = new ArrayList<>();
                for (JsonElement element : jsonObject.get(APIHandlerConstants.NONPROFITS).getAsJsonArray()) {
                    final String nonprofitEmail = getValue(element.getAsJsonObject(), "email");
                    if (AppMeta.instance.emailMap.get(nonprofitEmail) != null) {
                        emails.addAll(AppMeta.instance.emailMap.get(nonprofitEmail));
                    }
                }
                final JsonArray jsonArray = new JsonArray();
                for (String email : emails) {
                    jsonArray.add(new JsonPrimitive(email));
                }
                respond(jsonArray.toString(), httpServletResponse, 200);
            }
            default -> respond("Invalid request", httpServletResponse, 404);
        }
    }

    private static String readAsString(HttpServletRequest httpServletRequest) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static String getValue(JsonObject fd, String value) {
        return fd.getAsJsonObject("value").get(value).getAsString();
    }
}
