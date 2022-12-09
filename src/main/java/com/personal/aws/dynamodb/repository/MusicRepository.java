package com.personal.aws.dynamodb.repository;

import com.personal.aws.dynamodb.model.Music;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;

public interface MusicRepository {
    public List<Music> getAll();
    public Music save(Music music);
    public List<Music> getAllByArtist(String artist);
    public Music getByArtistAndSongTitle(String artist, String songTitle);
    public List<Music> getAllByArtistAndAlbumTitle(String artist, String songTitle);
    public String deleteByArtistAndSongTitle(String artist, String songTitle);
}
