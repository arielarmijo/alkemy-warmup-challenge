package tk.monkeycode.warmup.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URLChecker {

	private static final HttpClient httpClient = HttpClient.newBuilder()
													.version(HttpClient.Version.HTTP_1_1)
													.connectTimeout(Duration.ofSeconds(5))
													.build();
	
	public static boolean checkLink(String link) {
		HttpRequest request = HttpRequest.newBuilder()
										 .GET()
										 .uri(URI.create(link))
										 .setHeader("User-Agent", "Java 11 HttpClient Bot")
										 .build();
		try {
			HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
			return response.statusCode() == 200;
		} catch (IOException | InterruptedException e) {
			log.info("Error al recuperar imagen desde {}: {}", link, e.getMessage());
			return false;
		}
	}
	
}
