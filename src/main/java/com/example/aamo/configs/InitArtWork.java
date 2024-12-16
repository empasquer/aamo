package com.example.aamo.configs;

import com.example.aamo.models.ArtWork;
import com.example.aamo.models.ArtWorkTag;
import com.example.aamo.models.Media;
import com.example.aamo.models.TagType;
import com.example.aamo.repositories.ArtWorkRepository;
import com.example.aamo.repositories.ArtWorkTagRepository;
import com.example.aamo.repositories.MediaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Component
public class InitArtWork implements CommandLineRunner {

    private ArtWorkRepository artWorkRepository;
    private MediaRepository mediaRepository;
    private ArtWorkTagRepository artWorkTagRepository;

    public InitArtWork(ArtWorkRepository artWorkRepository,
                       MediaRepository mediaRepository,
                       ArtWorkTagRepository artWorkTagRepository) {
        this.artWorkRepository = artWorkRepository;
        this.mediaRepository = mediaRepository;
        this.artWorkTagRepository = artWorkTagRepository;
    }


    @Override
    public void run(String... args) throws Exception {


        //Initiliserer og geemmer tags, så de kun er der 1 gang.
        Map<String, ArtWorkTag> tagMap = initializeTags();


        //Lavet en metode som kan oprette nyt maler.
        createAndSaveInitArtWork( "Havfrue",
                "Akryl på lærred.\nHerskerinden over alle havene ..",
                20_000,
                "/uploads/200x200/havfrue.jpg",
                List.of(
                        tagMap.get("200x200"),
                        tagMap.get("Unaturlig"),
                        tagMap.get("Blå")
                ),
                true
        );

        createAndSaveInitArtWork( "Rød storm",
                "Akryl på lærred.\n Skyer som er røde og vilde. Man ved aldrig hvor man har dem. ...",
                5000,
                "/uploads/50x50/IMG_3435.JPG",
                List.of(
                        tagMap.get("50x50"),
                        tagMap.get("Himmel"),
                        tagMap.get("Rød")
                ),
                true
        );

        createAndSaveInitArtWork( "Østens Kvinde",
                "Akryl på lærred.\n Kender du det når du kan fornemme at nogen kigger på dig? " +
                        "Det er sådan at være omkring dette maleri. der er altid en i busken som sidder på lur. Kratlusker er der nogen som kalder dem...",
                7000,
                "/uploads/100x100/IMG_3421.jpg",
                List.of(
                        tagMap.get("100x100"),
                        tagMap.get("Mystik"),
                        tagMap.get("Blå")
                ),
                true
        );

        createAndSaveInitArtWork( "Fiona",
                "Akryl på lærred",
                7000,
                "/uploads/90x40/PmD5we8m.jpg",
                List.of(
                        tagMap.get("90x40"),
                        tagMap.get("Damer"),
                        tagMap.get("Pink")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                3000,
                "/uploads/60x60/1x52.jpg",
                List.of(
                        tagMap.get("60x60"),
                        tagMap.get("Natur"),
                        tagMap.get("Grøn")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                5000,
                "/uploads/60x80/IMG_3437.JPG",
                List.of(
                        tagMap.get("60x80"),
                        tagMap.get("Natur"),
                        tagMap.get("Gul")
                ),
                true
        );

        createAndSaveInitArtWork( "Egyptisk Dame",
                "Akryl på lærred",
                7000,
                "/uploads/100x90/egyptiskDame.jpg",
                List.of(
                        tagMap.get("100x90"),
                        tagMap.get("Mystik"),
                        tagMap.get("Blå")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                5000,
                "/uploads/50x50/IMG_1283.jpeg",
                List.of(
                        tagMap.get("50x50"),
                        tagMap.get("Mystik"),
                        tagMap.get("Lyserød")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                6000,
                "/uploads/50x50/IMG_1284.jpeg",
                List.of(
                        tagMap.get("50x50"),
                        tagMap.get("Damer"),
                        tagMap.get("Sort")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                7000,
                "/uploads/90x40/10ZZIoOm.jpg",
                List.of(
                        tagMap.get("90x40"),
                        tagMap.get("Damer"),
                        tagMap.get("Rød")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                6000,
                "/uploads/80x60/IMG_3429.jpg",
                List.of(
                        tagMap.get("80x60"),
                        tagMap.get("Natur"),
                        tagMap.get("Blå")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                5000,
                "/uploads/50x50/IMG_3441.JPG",
                List.of(
                        tagMap.get("50x50"),
                        tagMap.get("Damer"),
                        tagMap.get("Rød")
                ),
                true
        );
        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                500,
                "/uploads/20x20/IMG_3452.jpg",
                List.of(
                        tagMap.get("20x20"),
                        tagMap.get("20x20"),
                        tagMap.get("Blå")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                3000,
                "/uploads/OddSize/116x52.jpg",
                List.of(
                        tagMap.get("116x52"),
                        tagMap.get("Natur"),
                        tagMap.get("Hvid")
                ),
                true
        );

        createAndSaveInitArtWork( "",
                "Akryl på lærred",
                4000,
                "/uploads/60x80/IMG_1347.jpeg",
                List.of(
                        tagMap.get("60x80"),
                        tagMap.get("Natur"),
                        tagMap.get("Grøn")
                ),
                true
        );

        //TODO HER KOMMER ØVRIGE VÆRKER:

        createAndSaveInitArtWork( "",
                "Malet på papir",
                1500,
                "/uploads/oevrige/IMG_0936.jpeg",
                List.of(
                        tagMap.get("75x55"),
                        tagMap.get("Natur"),
                        tagMap.get("Rød")
                ),
                false
        );

        createAndSaveInitArtWork( "",
                "Malet på papir",
                1500,
                "/uploads/oevrige/IMG_0937.jpeg",
                List.of(
                        tagMap.get("75x55"),
                        tagMap.get("Natur"),
                        tagMap.get("Gul")
                ),
                false
        );


        createAndSaveInitArtWork( "",
                "Malet på papir",
                1500,
                "/uploads/oevrige/IMG_0995.jpeg",
                List.of(
                        tagMap.get("75x55"),
                        tagMap.get("Abstrakt"),
                        tagMap.get("Orange"),
                        tagMap.get("Sort")
                ),
                false
        );

        createAndSaveInitArtWork( "",
                "Malet på papir",
                1500,
                "/uploads/oevrige/IMG_0926.jpeg",
                List.of(
                        tagMap.get("75x55"),
                        tagMap.get("Personer"),
                        tagMap.get("Hvid")
                ),
                false
        );
        createAndSaveInitArtWork( "",
                "Malet på papir",
                1500,
                "/uploads/oevrige/IMG_0927.jpeg",
                List.of(
                        tagMap.get("75x55"),
                        tagMap.get("Personer"),
                        tagMap.get("Hvid")
                        ),
                false
        );
        createAndSaveInitArtWork( "Fødselsdagsflag",
                "Flag lavet af ler.",
                200,
                "/uploads/oevrige/IMG_0222.jpg",
                List.of(
                        tagMap.get("6cm-25cm"),
                        tagMap.get("Skulpture")
                        //tagMap.get("Rød")
                ),
                false
        );
        createAndSaveInitArtWork( "Medusa",
                "Medusa hovede lavet i ler.",
                2000,
                "/uploads/oevrige/IMG_1635.jpg",
                List.of(
                        tagMap.get("30cm"),
                        tagMap.get("Skulpture"),
                        tagMap.get("Grå")
                ),
                false
        );
        createAndSaveInitArtWork( "Hav giner",
                "Malede giner.",
                1000,
                "/uploads/oevrige/IMG_0059.jpg",
                List.of(
                        tagMap.get("180cm"),
                        tagMap.get("Skulpture"),
                        tagMap.get("Blå")
                ),
                false
        );

    }


    private Map<String, ArtWorkTag> initializeTags() {

       // List<ArtWorkTag> existingTags = artWorkTagRepository.findAll();
        Map<String, ArtWorkTag> tagMap = new HashMap<>();

        List<ArtWorkTag> tags = List.of(
                new ArtWorkTag(TagType.SIZE, "50x50"),
                new ArtWorkTag(TagType.SIZE, "100x100"),
                new ArtWorkTag(TagType.SIZE, "200x200"),
                new ArtWorkTag(TagType.SIZE, "90x40"),
                new ArtWorkTag(TagType.SIZE, "60x80"),
                new ArtWorkTag(TagType.SIZE, "100x90"),
                new ArtWorkTag(TagType.SIZE, "116x52"),
                new ArtWorkTag(TagType.SIZE, "80x60"),
                new ArtWorkTag(TagType.SIZE, "60x60"),
                new ArtWorkTag(TagType.SIZE, "75x55"),
                new ArtWorkTag(TagType.SIZE, "6cm-25cm"),
                new ArtWorkTag(TagType.SIZE, "30cm"),
                new ArtWorkTag(TagType.SIZE, "180cm"),
                new ArtWorkTag(TagType.THEME, "Fisk"),
                new ArtWorkTag(TagType.THEME, "Damer"),
                new ArtWorkTag(TagType.THEME, "Personer"),
                new ArtWorkTag(TagType.THEME, "Unaturlig"),
                new ArtWorkTag(TagType.THEME, "Himmel"),
                new ArtWorkTag(TagType.THEME, "Mystik"),
                new ArtWorkTag(TagType.THEME, "Natur"),
                new ArtWorkTag(TagType.THEME, "Abstrakt"),
                new ArtWorkTag(TagType.THEME, "20x20"),
                new ArtWorkTag(TagType.THEME, "Skulpture"),
                new ArtWorkTag(TagType.COLOR, "Blå"),
                new ArtWorkTag(TagType.COLOR, "Gul"),
                new ArtWorkTag(TagType.COLOR, "Grøn"),
                new ArtWorkTag(TagType.COLOR, "Rød"),
                new ArtWorkTag(TagType.COLOR, "Lyserød"),
                new ArtWorkTag(TagType.COLOR, "Sort"),
                new ArtWorkTag(TagType.COLOR, "Pink"),
                new ArtWorkTag(TagType.COLOR, "Hvid"),
                new ArtWorkTag(TagType.COLOR, "Grå"),
                new ArtWorkTag(TagType.COLOR, "Orange")
        );

        for (ArtWorkTag tag : tags){
            artWorkTagRepository.save(tag);

            tagMap.put(tag.getTagValue(), tag);
        }
        return tagMap;
    }

    private void createAndSaveInitArtWork (String title,
                                           String description,
                                           int price,
                                           String mediaUrl,
                                           List<ArtWorkTag> tags,
                                           boolean type) throws IOException, URISyntaxException {
        //bruges til path
        Path path = Paths.get(mediaUrl);
        Media media = new Media(path.toString());
        //Bruges til Url.
       // Media media = new Media(mediaUrl);

        mediaRepository.save(media);

        ArtWork artWork = new ArtWork();
        artWork.setTitle(title);
        artWork.setDescription(description);
        artWork.setPrice(price);
        artWork.setSold(false);
        artWork.setType(type);
        artWork.setMedia(media);

        List<ArtWorkTag> existingTags = artWorkTagRepository.findAllById(
                tags.stream().map(ArtWorkTag::getTagId).collect(Collectors.toList())
        );

        // Tilknyt de eksisterende tags til artWork (merge dem)
        artWork.setTags(existingTags);

        // Gem artWork i databasen
        artWorkRepository.save(artWork);

    }

}
