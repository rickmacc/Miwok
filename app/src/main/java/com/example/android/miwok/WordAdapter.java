package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by rick on 12/23/16.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int backgroundColorId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param word A List of Word objects to display in a list
     */
    public WordAdapter(Activity context, ArrayList<Word> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
    }

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param word A List of Word objects to display in a list
     * @param color Background color for this category
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int backgroundColorId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        this.backgroundColorId = backgroundColorId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //return super.getView(position, convertView, parent);
        // Check if the existing view is being reused, otherwise inflate the view

        // Get the {@link Word} object located at this position in the list
        final Word currentWord = getItem(position);

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        if (currentWord.getImageResourceId() > 0) {
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
            iconView.setImageResource(currentWord.getImageResourceId());
        } else {
            iconView.setVisibility(View.GONE);
        }

        // Set the background color for the text container
        LinearLayout textContainer = (LinearLayout) listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), backgroundColorId);
        textContainer.setBackgroundColor(color);

        // Find the TextView in the list_item.xml layout with the ID miwok_word
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.miwok_word);
        // Get the miwok word from the current Word object and
        // set this text on the word TextView
        nameTextView.setText(currentWord.getMiwokWord());

        // Find the TextView in the list_item.xml layout with the ID default_word
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.default_word);
            // Get the default word from the current AndroidFlavor object and
            // set this text on the word TextView
        numberTextView.setText(currentWord.getDefaultWord());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }


}
