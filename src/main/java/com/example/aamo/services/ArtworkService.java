package com.example.aamo.services;

import com.example.aamo.models.*;
import com.example.aamo.repositories.ArtWorkRepository;
import com.example.aamo.repositories.ArtWorkTagRepository;
import com.example.aamo.repositories.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtworkService {
    private ArtWorkRepository artWorkRepository;
    private MediaRepository mediaRepository;
    private ArtWorkTagRepository artWorkTagRepository;

    public ArtworkService(ArtWorkTagRepository artWorkTagRepository, ArtWorkRepository artWorkRepository, MediaRepository mediaRepository) {
        this.artWorkRepository = artWorkRepository;
        this.artWorkTagRepository = artWorkTagRepository;
        this.mediaRepository = mediaRepository;
    }

    private Media saveArtworkPicture(ArtworkDTO artworkDTO) {
        Media media = new Media(artworkDTO.getMediaUrl());
        mediaRepository.save(media);
        return media;
    }
    private ArtWork createArtwork(ArtworkDTO artworkDTO) {
        Media media = saveArtworkPicture(artworkDTO);
        ArtWork artWork = new ArtWork();
        artWork.setTitle(artworkDTO.getTitle());
        artWork.setDescription(artworkDTO.getDescription());
        artWork.setSold(artworkDTO.isSold());
        artWork.setType(artworkDTO.isType());
        artWork.setPrice(artworkDTO.getPrice());
        artWork.setMedia(media);
        artWorkRepository.save(artWork);
        return artWork;
    }

    private void findOrSaveTags(ArtWork artWork, List<ArtWorkTag> tagsDTO){

        List<ArtWorkTag> tagsToSave  = tagsDTO.stream()
                .map(tagRequest -> {
                    ArtWorkTag tag = artWorkTagRepository.findByTagTypeAndTagValue(
                            tagRequest.getTagType(),
                            tagRequest.getTagValue()
                    ).orElseGet(() -> new ArtWorkTag(tagRequest.getTagType(), tagRequest.getTagValue()));

                    // Set the association with the artwork
                    //tag.setArtWork(artWork);
                    return tag;
                })
                .collect(Collectors.toList());

        // Add tags to the artwork
        artWork.getTags().addAll(tagsToSave);

        // Save the artwork (cascading saves the tags)
        artWorkRepository.save(artWork);
    }


    public void saveArtworkWithTagsAndPicture(ArtworkDTO artworkDTO) {
        ArtWork artWork = createArtwork(artworkDTO);
        System.out.println(artworkDTO.getTags().getFirst().getTagValue());
        findOrSaveTags(artWork, artworkDTO.getTags());

    }

}
