package com.personal.aws.dynamodb.repository.impl;

import com.personal.aws.dynamodb.model.Music;
import com.personal.aws.dynamodb.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MusicRepositoryImpl implements MusicRepository {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Override
    public List<Music> getAll() {
        DynamoDbTable<Music> musicTable = getTable();
        return musicTable.scan().items().stream().collect(Collectors.toList());
    }

    public Music save(final Music music) {
        DynamoDbTable<Music> musicTable = getTable();
        musicTable.putItem(music);
        return music;
    }

    public Music getByArtistAndSongTitle(String artist, String songTitle) {
        DynamoDbTable<Music> musicTable = getTable();

        Key key = Key.builder().partitionValue(artist).sortValue(songTitle).build();
        Music music = musicTable.getItem(key);
        return music;
    }

    public String deleteByArtistAndSongTitle(String artist, String songTitle) {
        DynamoDbTable<Music> musicTable = getTable();
        Key key = Key.builder().partitionValue(artist).sortValue(songTitle).build();

        DeleteItemEnhancedRequest deleteRequest = DeleteItemEnhancedRequest
                .builder()
                .key(key)
                .build();
        musicTable.deleteItem(deleteRequest);
        return "Music: " + songTitle + "by Artist : "+ artist +" Deleted!";
    }

    private DynamoDbTable<Music> getTable() {
        DynamoDbTable<Music> musicTable = dynamoDbEnhancedClient.table("Music", TableSchema.fromBean(Music.class));
        return musicTable;
    }

    public List<Music> getAllByArtist(String artist) {
        DynamoDbTable<Music> musicTable = getTable();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(artist)
                .build());

        return musicTable.query(queryConditional).items().stream().collect(Collectors.toList());
    }

    public List<Music> getAllByArtistAndAlbumTitle(final String artist, final String albumTitle) {
        DynamoDbTable<Music> orderTable = getTable();

        AttributeValue attributeValue = AttributeValue.builder()
                .s(String.valueOf(albumTitle))
                .build();

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(attributeValue)
                        .build());

        PageIterable<Music> results = orderTable.query(QueryEnhancedRequest.builder().queryConditional(queryConditional).limit(100).build());
        return null;
    }
}
