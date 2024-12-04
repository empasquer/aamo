package com.example.aamo.config;

import com.example.aamo.configs.DELInitDataExample;
import com.example.aamo.models.DELKommune;
import com.example.aamo.models.DELRegion;
import com.example.aamo.repositories.DELKommuneRepository;
import com.example.aamo.repositories.DELRegionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.net.http.HttpClient;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InitDataIntegrationTest {

    @Autowired private DELRegionRepository DELRegionRepository;
    @Autowired private DELKommuneRepository DELKommuneRepository;
    @Autowired private HttpClient httpClient;
    @Autowired private DELInitDataExample initData;

    @Test
    void testRunSavesRegionsAndKommuner() throws Exception{
        List<DELRegion> DELRegions = DELRegionRepository.findAll();
        Assertions.assertEquals(5, DELRegions.size());

        List<DELKommune> kommuner = DELKommuneRepository.findAll();
        Assertions.assertEquals(99, kommuner.size());
    }
}
