package com.algonquincollege.lalo0417.hilogame;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int attempts = 0;
    private int theNumber = 1;
    private static final String ABOUT_DIALOG_TAG;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    private int randomNumberGen(){
        Random randomNum = new Random();
        theNumber = randomNum.nextInt(1000);
        attempts = 0;
        return theNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button guessButton = (Button) findViewById(R.id.guessButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);

        final TextView displayAttempts = (TextView) findViewById(R.id.attemptsDisplay);
        final TextView displayNumber = (TextView) findViewById(R.id.displayNum);

        randomNumberGen();

        displayNumber.setTextSize(72);




        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userGuessText = (EditText) findViewById(R.id.guessInput);
                String userGuessString = userGuessText.getText().toString();
                int userGuessInt = Integer.parseInt(userGuessString);

                if (userGuessString.isEmpty()) {
                    userGuessText.setError("Enter a number between 1 - 1000");
                    userGuessText.requestFocus();
                    userGuessText.setText("");
                    return;
                }

                if (userGuessInt > 1000) {
                    userGuessText.setError("That number is too big!");
                    userGuessText.requestFocus();
                    userGuessText.setText("");
                    return;
                }

                if (userGuessInt < 1) {
                    userGuessText.setError("That number is too small!");
                    userGuessText.requestFocus();
                    userGuessText.setText("");
                } else {
                    userGuessText.setText("");
                    attempts++;
                    displayAttempts.setText("Attempts: " + attempts);

                    if (userGuessInt > theNumber){
                        displayNumber.setText(userGuessString + "\u2B07");
                        Toast.makeText(getApplicationContext(),"Lower", Toast.LENGTH_SHORT).show();
                    }
                    if (userGuessInt < theNumber){
                        displayNumber.setText(userGuessString + "\u2B06");
                        Toast.makeText(getApplicationContext(),"Higher", Toast.LENGTH_SHORT).show();
                    }
                    if (attempts == 10){
                        guessButton.setEnabled(false);
                        displayAttempts.setText("");
                        displayNumber.setTextSize(36);
                        displayNumber.setText("Play again!\n Press Reset");
                        Toast.makeText(getApplicationContext(),"Too many attempts ", Toast.LENGTH_SHORT).show();
                    }
                    else if(userGuessInt == theNumber){
                        guessButton.setEnabled(false);
                        displayAttempts.setText("");
                        displayNumber.setTextSize(36);
                        displayNumber.setText("Play again!\n Press Reset");
                        if(attempts <= 5){
                            Toast.makeText(getApplicationContext(),"Superior Victory!", Toast.LENGTH_SHORT).show();
                        }
                        if (attempts > 5 && attempts <= 10){
                            Toast.makeText(getApplicationContext(),"Excellent Victory", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomNumberGen();
                attempts = 0;
                displayAttempts.setText("Attempts: " + attempts);
                displayNumber.setText("HiLo");

                displayNumber.setTextSize(72);
                guessButton.setEnabled(false);

            }
        });
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(),Integer.toString(theNumber), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
