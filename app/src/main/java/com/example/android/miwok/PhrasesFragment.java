package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesFragment extends Fragment {

    private MediaPlayer mediaPlayer = null;

    public PhrasesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "minto wuksus", 0, R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "wtinnә oyaase'nә", 0, R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is ...", "oyaaset...", 0, R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", 0, R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit", 0, R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", 0, R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming.", "hәә’ әәnәm", 0, R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.", "әәnәm", 0, R.raw.phrase_im_coming));
        words.add(new Word("Let's go.", "yoowutis", 0, R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", 0, R.raw.phrase_come_here));

        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_phrases);
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(wordAdapter);
        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioFileId());

                // Start the audio file
                AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                int request = am.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int focusChange) {
                        Log.v(this.getClass().getSimpleName(), "onAudioFocusChange(" + focusChange + ")");
                        Log.v(this.getClass().getSimpleName(), "GAIN: " + AudioManager.AUDIOFOCUS_GAIN);
                        switch (focusChange) {
                            case AudioManager.AUDIOFOCUS_LOSS:
                                mediaPlayer.stop();
                        }
                    }
                }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) mediaPlayer.start();

                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                mediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        Log.v(this.getClass().getSimpleName(), "Releasing MediaPlayer resources");
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                }
            });
        }
    }

    /**
     * +     * This listener gets triggered when the {@link MediaPlayer} has completed
     * +     * playing the audio file.
     * +
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
}
