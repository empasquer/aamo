package com.example.aamo.config;

import com.example.aamo.configs.DELInitDataExample;
import com.example.aamo.models.DELKommune;
import com.example.aamo.models.DELRegion;
import com.example.aamo.repositories.DELKommuneRepository;
import com.example.aamo.repositories.DELRegionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

public class InitDataTest {
    @Mock private DELRegionRepository DELRegionRepository;
    @Mock private DELKommuneRepository DELKommuneRepository;
    @Mock private HttpClient httpClient;

    public InitDataTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private List<DELRegion> fetchRegionsMockSetup() throws Exception {
        Path regionsPath = Path.of("data/regioner.json");
        String regionsData = Files.readString(regionsPath);

        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn(regionsData);
        when(mockResponse.statusCode()).thenReturn(200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        DELInitDataExample initData = new DELInitDataExample(DELRegionRepository, DELKommuneRepository, httpClient);
        return initData.fetchRegions();
    }

    @Test
    public void testFetchRegions() throws Exception {
        List<DELRegion> DELRegions = fetchRegionsMockSetup();
        Assertions.assertEquals(5, DELRegions.size());
    }

    @Test
    public void testFetchKommuner() throws Exception {
        List<DELRegion> DELRegions = fetchRegionsMockSetup();

        Path kommunesPath = Path.of("data/kommuner.json");
        String kommunesData = Files.readString(kommunesPath);

        HttpResponse<String> mockResponseKommune = mock(HttpResponse.class);
        when(mockResponseKommune.body()).thenReturn(kommunesData);
        when(mockResponseKommune.statusCode()).thenReturn(200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseKommune);

        DELInitDataExample initData = new DELInitDataExample(DELRegionRepository, DELKommuneRepository, httpClient);
        List<DELKommune> kommuner = initData.fetchKommuner(DELRegions);

        Assertions.assertEquals(99, kommuner.size());
    }
}