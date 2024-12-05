package com.example.aamo.configs;

import com.example.aamo.models.DELKommune;
import com.example.aamo.models.DELRegion;
import com.example.aamo.repositories.DELKommuneRepository;
import com.example.aamo.repositories.DELRegionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DELInitDataExample implements CommandLineRunner {

    private DELRegionRepository DELRegionRepository;
    private DELKommuneRepository DELKommuneRepository;
    private HttpClient httpClient;

    public DELInitDataExample(DELRegionRepository DELRegionRepository, DELKommuneRepository DELKommuneRepository, HttpClient httpClient) {
        this.DELRegionRepository = DELRegionRepository;
        this.DELKommuneRepository = DELKommuneRepository;
        this.httpClient = httpClient;
    }

    @Override
    public void run(String... args) throws Exception {
        List<DELRegion> DELRegions = fetchRegions();
        for (DELRegion DELRegion : DELRegions) {
            DELRegionRepository.save(DELRegion);
            System.out.println(DELRegion);
        }

        List<DELKommune> kommuner = fetchKommuner(DELRegions);
        for (DELKommune DELKommune : kommuner) {
            DELKommuneRepository.save(DELKommune);
            System.out.println(DELKommune);
        }

        System.out.println("Hentet " + DELRegions.size() + " regioner og " + kommuner.size() +" kommuner.");
    }

    public List<DELRegion> fetchRegions() throws Exception {
        // 1. TODO: fetch https://api.dataforsyningen.dk/regioner
        // 2. TODO: parse JSON to List<Region>
        JsonNode root = getJsonFrom(URI.create("https://api.dataforsyningen.dk/regioner"));

        List<DELRegion> DELRegions = new ArrayList<>();

        for (JsonNode node : root) {
            String kode = node.get("kode").asText();
            String navn = node.get("navn").asText();
            String href = node.get("href").asText();

            DELRegions.add(new DELRegion(kode, navn, href));
        }

        return DELRegions;

    }


    public List<DELKommune> fetchKommuner(List<DELRegion> DELRegions) throws Exception {
        // 1. TODO: fetch https://api.dataforsyningen.dk/kommuner
        // 2. TODO: parse JSON to List<Kommune>
        JsonNode root = getJsonFrom(URI.create("https://api.dataforsyningen.dk/kommuner"));

        List<DELKommune> kommuner = new ArrayList<>();
        for (JsonNode node : root) {
            String kode = node.get("kode").asText();
            String navn = node.get("navn").asText();
            String href = node.get("href").asText();
            String regionKode = node.get("regionskode").asText();

            Optional<DELRegion> region = DELRegions
                    .stream()
                    .filter(
                    (DELRegion thisRegion) -> thisRegion.getKode().equals(regionKode))
                    .findFirst();

            DELKommune DELKommune = new DELKommune(kode, navn, href, region.get());

            kommuner.add(DELKommune);
        }

        for(DELKommune kom : kommuner) {
            System.out.println(kom.getNavn());

        }
        return kommuner;
    }

    private JsonNode getJsonFrom(URI endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpoint)
                .GET()
                .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(response.body());
    }
}
