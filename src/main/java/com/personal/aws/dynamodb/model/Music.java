package com.personal.aws.dynamodb.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Music {

    private String artist;
    private String songTitle;
    private String albumTitle;
    private Integer awards;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Artist")
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    @DynamoDbSortKey
    @DynamoDbAttribute("SongTitle")
    public String getSongTitle() {
        return songTitle;
    }
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
    @DynamoDbAttribute("AlbumTitle")
    public String getAlbumTitle() {
        return albumTitle;
    }
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
    @DynamoDbAttribute("Awards")
    public Integer getAwards() {
        return awards;
    }
    public void setAwards(Integer awards) {
        this.awards = awards;
    }
}
