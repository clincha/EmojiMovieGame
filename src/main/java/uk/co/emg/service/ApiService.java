package uk.co.emg.service;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class ApiService {

  String makeApiRequest(String requestUrl) {
    int responseCode = -1;
    StringBuilder response = new StringBuilder();

    while (responseCode != 200) {
      try {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        responseCode = connection.getResponseCode();

        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
          response.append(scanner.nextLine());
        }

        if (responseCode != 200) {
          throw new Exception("Bad API Response: " + response);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return response.toString();
  }
}
