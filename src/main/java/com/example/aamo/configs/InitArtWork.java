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
                "Herskerinden over alle havene ..",
                20_000,
                "/uploads/200x200/havfrue.jpg",
                List.of(
                        tagMap.get("200x200"),
                        tagMap.get("Unaturlig"),
                        tagMap.get("Blå")
                )
        );

        createAndSaveInitArtWork( "Rød storm",
                "Skyer som er røde og vilde. Man ved aldrig hvor man har dem. ...",
                5000,
                "/uploads/50x50/IMG_3435.JPG",
                List.of(
                        tagMap.get("50x50"),
                        tagMap.get("Himmel"),
                        tagMap.get("Rød")
                )
        );

        createAndSaveInitArtWork( "Østens Kvinde",
                "Kender du det når du kan fornemme at nogen kigger på dig? " +
                        "Det er sådan at være omkring dette maleri. der er altid en i busken som sidder på lur. Kratlusker er der nogen som kalder dem...",
                7000,
                "/uploads/100x100/IMG_3421.jpg",
                List.of(
                        tagMap.get("100x100"),
                        tagMap.get("Mystik"),
                        tagMap.get("Blå")
                )
        );

        createAndSaveInitArtWork( "Fiona",
                "...",
                7000,
                "/uploads/90x40/PmD5we8m.jpg",
                List.of(
                        tagMap.get("90x40"),
                        tagMap.get("Damer"),
                        tagMap.get("Pink")
                )
        );


        createAndSaveInitArtWork( "",
                "...",
                5000,
                "/uploads/60x80/IMG_3437.JPG",
                List.of(
                        tagMap.get("60x80"),
                        tagMap.get("Natur"),
                        tagMap.get("Gul")
                )
        );

        //TODO nye malerier
        createAndSaveInitArtWork( "Egyptisk Dame",
                "...",
                7000,
                "/uploads/100x90/egyptiskDame.jpg",
                List.of(
                        tagMap.get("100x90"),
                        tagMap.get("Mystik"),
                        tagMap.get("Blå")
                )
        );

        createAndSaveInitArtWork( "",
                "...",
                5000,
                "/uploads/50x50/IMG_1283.jpeg",
                List.of(
                        tagMap.get("50x50"),
                        tagMap.get("Mystik"),
                        tagMap.get("Lyserød")
                )
        );

        createAndSaveInitArtWork( "",
                "...",
                6000,
                "/uploads/50x50/IMG_1284.jpeg",
                List.of(
                        tagMap.get("50x50"),
                        tagMap.get("Damer"),
                        tagMap.get("Sort")
                )
        );

        createAndSaveInitArtWork( "",
                "...",
                7000,
                "/uploads/90x40/10ZZIoOm.jpg",
                List.of(
                        tagMap.get("90x40"),
                        tagMap.get("Damer"),
                        tagMap.get("Rød")
                )
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
                new ArtWorkTag(TagType.THEME, "Fisk"),
                new ArtWorkTag(TagType.THEME, "Damer"),
                new ArtWorkTag(TagType.THEME, "Unaturlig"),
                new ArtWorkTag(TagType.THEME, "Himmel"),
                new ArtWorkTag(TagType.THEME, "Mystik"),
                new ArtWorkTag(TagType.THEME, "Natur"),
                new ArtWorkTag(TagType.COLOR, "Blå"),
                new ArtWorkTag(TagType.COLOR, "Gul"),
                new ArtWorkTag(TagType.COLOR, "Grøn"),
                new ArtWorkTag(TagType.COLOR, "Rød"),
                new ArtWorkTag(TagType.COLOR, "Lyserød"),
                new ArtWorkTag(TagType.COLOR, "Sort"),
                new ArtWorkTag(TagType.COLOR, "Pink")
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
                                           List<ArtWorkTag> tags) throws IOException, URISyntaxException {
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
        artWork.setType(true);
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
