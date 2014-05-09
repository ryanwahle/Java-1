/*
Ryan Wahle
Full Sail University
Java 1 1405
Linear Layout Assignment
 */

/*
Scratch Pad:

x  int or float variable
x  boolean variable
x  String variable
x  loop (any type)
x  condition
x  function
x  click event handler

The application must also utilize, at minimum, 1 of each of the following Android components:

x  Android resource value
x  TextView
x  EditText
x  Button
x  LinearLayout
 */

package com.ryanwahle.linearlayout.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        final TextView textViewResults = (TextView) findViewById(R.id.textViewResults);
        final EditText editTextCurrentAge = (EditText) findViewById(R.id.editTextCurrentAge);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int retirementAge = 72;

                String currentAgeString = editTextCurrentAge.getText().toString();
                int currentAgeInt = Integer.parseInt(currentAgeString);

                boolean isRetired = calculateIsRetired(retirementAge, currentAgeInt);
                if (isRetired) {
                    textViewResults.setText("You are retired! Congratulations!");
                } else {
                    textViewResults.setText("You are not retired!");

                    int yearsToRetirement = 0;
                    int currentAgeIndex = currentAgeInt;

                    while (currentAgeIndex < retirementAge) {
                        yearsToRetirement++;
                        currentAgeIndex++;
                    }

                    textViewResults.setText(Integer.toString(yearsToRetirement) + " more years until your retired!");
                }
            }
        });


    }

    public boolean calculateIsRetired (int retirementAge, int currentAge) {
        if (currentAge <= retirementAge) {
            return false;
        } else {
            return true;
        }
    }
}
