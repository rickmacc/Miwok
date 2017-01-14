package com.example.android.miwok;

/**
 * Created by rick on 12/22/16.
 */

public class Word {

    private String defaultWord;
    private String miwokWord;
    private int imageId;
    private int audioFileId;

    public Word(String defaultWord, String miwokWord) {
        this.defaultWord = defaultWord;
        this.miwokWord = miwokWord;
    }

    public Word(String defaultWord, String miwokWord, int imageId) {
        this.defaultWord = defaultWord;
        this.miwokWord = miwokWord;
        this.imageId = imageId;
    }

    public Word(String defaultWord, String miwokWord, int imageId, int audioFileId) {
        this.defaultWord = defaultWord;
        this.miwokWord = miwokWord;
        this.imageId = imageId;
        this.audioFileId = audioFileId;
    }

    public String getDefaultWord() {
        return defaultWord;
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public int getImageResourceId() { return imageId; }

    public int getAudioFileId() { return audioFileId; }
}
