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
import java.util.List;

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
       //Lavet en metode som kan oprette nyt maler.
        createAndSaveInitArtWork( "Havfrue",
                "Herskerinden over alle havene ..",
                20_000,
                "/uploads/200x200/havfrue.jpg",
                List.of(
                        new ArtWorkTag(TagType.SIZE, "200x200"),
                        new ArtWorkTag(TagType.THEME, "Unaturlig"),
                        new ArtWorkTag(TagType.COLOR, "Blå")
                )
        );

        createAndSaveInitArtWork( "Rød storm",
                "Skyer som er røde og vilde. Man ved aldrig hvor man har dem. ...",
                5000,
                "/uploads/50x50/IMG_3435.JPG",
                List.of(
                        new ArtWorkTag(TagType.SIZE, "50x50"),
                        new ArtWorkTag(TagType.THEME, "Himmel"),
                        new ArtWorkTag(TagType.COLOR, "Rød")
                )
        );
        createAndSaveInitArtWork( "",
                "Bla blasåå fint, bør i skov...",
                4000,
                "/uploads/50x50/IMG_3440.jpg",
                List.of(
                        new ArtWorkTag(TagType.SIZE, "50x50"),
                        new ArtWorkTag(TagType.THEME, "Abstrakt"),
                        new ArtWorkTag(TagType.COLOR, "Blå")
                )
        );

        createAndSaveInitArtWork( "Østens Kvinde",
                "Kender du det når du kan fornemme at nogen kigger på dig? " +
                        "Det er sådan at være omkring dette maleri. der er altid en i busken som sidder på lur. Kratlusker er der nogen som kalder dem...",
                7000,
                "/uploads/100x100/IMG_3421.jpg",
                List.of(
                        new ArtWorkTag(TagType.SIZE, "100x100"),
                        new ArtWorkTag(TagType.THEME, "Mystik"),
                        new ArtWorkTag(TagType.COLOR, "Blå")
                )
        );

        createAndSaveInitArtWork( "Fiona",
                "...",
                6000,
                "/uploads/90x40/PmD5we8m.jpg",
                List.of(
                        new ArtWorkTag(TagType.SIZE, "90x40"),
                        new ArtWorkTag(TagType.THEME, "Damer"),
                        new ArtWorkTag(TagType.COLOR, "Pink")
                )
        );

        createAndSaveInitArtWork( "",
                "...",
                500,
                "/uploads/20x20/IMG_3459.jpg",
                List.of(
                        new ArtWorkTag(TagType.SIZE, "20x20"),
                        new ArtWorkTag(TagType.THEME, "Natur"),
                        new ArtWorkTag(TagType.COLOR, "Gul")
                )
        );
        createAndSaveInitArtWork( "",
                "...",
                8000,
                "/uploads/60x80/IMG_3437.JPG",
                List.of(
                        new ArtWorkTag(TagType.SIZE, "60x80"),
                        new ArtWorkTag(TagType.THEME, "Natur"),
                        new ArtWorkTag(TagType.COLOR, "Gul")
                )
        );



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

        tags.forEach( tag -> tag.setArtWork(artWork));
        artWork.setTags(tags);
        artWorkRepository.save(artWork);
        artWorkTagRepository.saveAll(tags);
    }

}
