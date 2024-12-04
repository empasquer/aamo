package com.example.aamo.config;

import com.example.aamo.configs.InitDataExample;
import com.example.aamo.models.Kommune;
import com.example.aamo.models.Region;
import com.example.aamo.repositories.KommuneRepository;
import com.example.aamo.repositories.RegionRepository;
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

    @Autowired private RegionRepository regionRepository;
    @Autowired private KommuneRepository kommuneRepository;
    @Autowired private HttpClient httpClient;
    @Autowired private InitDataExample initData;

    @Test
    void testRunSavesRegionsAndKommuner() throws Exception{
        List<Region> regions = regionRepository.findAll();
        Assertions.assertEquals(5, regions.size());

        List<Kommune> kommuner = kommuneRepository.findAll();
        Assertions.assertEquals(99, kommuner.size());
    }
}
