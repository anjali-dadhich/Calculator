
/*Created by: Anjali Dadhich
 *Java file -- CalculatorActivity
 * Xml file -- activity_calculator_constraint
 * Version -- 1.0
 * Date -- 19/1/2021
 * Last Update -- 25/1/2021
 * This One is Main Activity
 */

package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/* txt_userInput = number entered by user to be calculate
    txtShowUserInput = used to show number which entered by user
 *  txt_answer =  number which show answer after calculation*/

//Variables required for performing calculation
/* strOperator = store user entered operator sign
 * doubleFirstInput = store number entered before any operator
 * doubleAnswer = store answer after calculation and use for further calculations*/

public class CalculatorActivity extends AppCompatActivity
{
    private static final String TAG = "CalculatorActivity";

            TextView txtUserInput;
            TextView txtShowUserInput;
            TextView txtAnswer;

            ImageButton buttonBackSpace;
            Button buttonPercent;
            Button buttonAllClear;
            Button buttonDivision;
            Button button7;
            Button button8;
            Button button9;
            Button buttonMultiply;
            Button button4;
            Button button5;
            Button button6;
            Button buttonSubtract;
            Button button1;
            Button button2;
            Button button3;
            Button buttonAdd;
            Button button0;
            Button buttonDecimal;
            Button buttonEqual;


    private String strOperator = "";
    private Double doubleFirstInput = 0.0;
    private Double doubleAnswer = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_constraint);

        //initialise all Ids
        init();


        /*event which call BACKSPACE method on click of backspace button*/
        buttonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backSpace();
            }
        });

        /*event which call All Clear method on click of all clear button to clean all*/
        buttonAllClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allClear();
            }
        });

        /* onClick of equal button reset all values
           and show answer on txtAnswer  */
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtAnswer.setText(String.format("=%s", doubleAnswer));
                txtUserInput.setText("0");
                txtShowUserInput.setText("");
                strOperator = "";
                doubleFirstInput = 0.0;

                doubleAnswer = 0.0;
                txtAnswer.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite));
            }
        });
    }

    /* reset all variable so that user can perform new calculation*/
    private void allClear() {
        txtUserInput.setText("0");
        txtShowUserInput.setText("");
        txtAnswer.setText("");
        strOperator = "";
        doubleFirstInput = 0.0;
        doubleAnswer = 0.0;
    }

    /*  this init() method initialise  all xml views Ids */
    private void init() {

        txtUserInput = findViewById(R.id.txt_userInput);
        txtAnswer = findViewById(R.id.txt_answer);
        buttonAllClear = findViewById(R.id.button_AC);
        buttonBackSpace = findViewById(R.id.button_backSpace);
        buttonPercent = findViewById(R.id.button_percent);
        buttonDivision = findViewById(R.id.button_division);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonMultiply = findViewById(R.id.button_multiply);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        buttonSubtract = findViewById(R.id.button_subtract);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        buttonAdd = findViewById(R.id.button_add);
        button0 = findViewById(R.id.button_0);
        buttonDecimal = findViewById(R.id.button_decimal);
        buttonEqual = findViewById(R.id.button_equal);

        txtShowUserInput = findViewById(R.id.txt_showUserInput);

    }


    /*this method called through Xml tag at time user click on numbers 0 to 9
    this method perform append together
    and call performOperation() Method*/
    public void showNumberOnClick(View btnNumberView) {

        Button btnNumber = (Button)btnNumberView;
        txtUserInput.setText(String.format("%s%s", txtUserInput.getText().toString(), btnNumber.getText().toString()));
        txtShowUserInput.setText(String.format("%s%s", txtShowUserInput.getText().toString(), btnNumber.getText().toString()));
        doubleFirstInput = Double.valueOf(txtUserInput.getText().toString());

        performOperation(strOperator);
    }

    /*this method called through Xml tag at time  user click on operator button
   this method perform append operator sign with userInput number
   or if last pressed button is already operator then replace with current pressed operator
   and call performOperation() Method*/
    public void showOperatorOnClick(View btnOperatorView){

        Button btnOperator = (Button)btnOperatorView;
        strOperator = btnOperator.getText().toString();

        String strUserInput = txtShowUserInput.getText().toString();  // store user input in string to find last character
        String strLastCharUserInput = String.valueOf(strUserInput.charAt(strUserInput.length()-1));

        /*if user already clicked any operator replace with current pressed operator*/
        if (strLastCharUserInput.equals(getString(R.string.addition)) || strLastCharUserInput.equals(getString(R.string.subtract))
                || strLastCharUserInput.equals(getString(R.string.multiply)) || strLastCharUserInput.equals(getString(R.string.division))
                || strLastCharUserInput.equals(getString(R.string.percent)))
        {
            backSpace();
        }

        txtShowUserInput.setText(String.format("%s%s", txtShowUserInput.getText().toString(),strOperator));

        performOperation(strOperator);

        txtUserInput.setText("");
    }

    /*BackSpace Method  delete last character
     * and set stringBuilder_userInput on txtUserInput
     * and call performOperation() method*/

    private void backSpace(){
        if (txtUserInput.getText().toString().length() > 0) {
            //backspace for txtUserInput
            StringBuilder stringBuilderUserInput = new StringBuilder(txtUserInput.getText().
                                                   toString());
            //Delete character at last index of stringBuilder_userInput
            stringBuilderUserInput.deleteCharAt(stringBuilderUserInput.length() - 1);
            txtUserInput.setText(stringBuilderUserInput.toString());

            //backspace for txtShowUserInput
            StringBuilder stringBuilderShowUserInput = new StringBuilder(txtShowUserInput.getText().
                                                       toString());
            //Delete character at last index of stringBuilderShowUserInput
            stringBuilderShowUserInput.deleteCharAt(stringBuilderShowUserInput.length() - 1);
            txtShowUserInput.setText(stringBuilderShowUserInput.toString());
        }
        else
        {
            allClear();
        }

        performOperation(strOperator);
    }


    /*performOperation() method Required operator sign through strOperator as a parameter
    * and perform calculation according them */
    private void performOperation(String strOperator){

            if (strOperator.equals(getString(R.string.addition))) {
                doubleAnswer = doubleFirstInput +doubleAnswer ;

                txtAnswer.setText(String.format("=%s", doubleAnswer));
                Log.d(TAG, "performOperation: DONE +++++++");

                doubleFirstInput = 0.0;
            }
            else if (strOperator.equals(getString(R.string.subtract))){
                doubleAnswer = doubleAnswer - doubleFirstInput ;

                txtAnswer.setText(String.format("=%s", doubleAnswer));
                Log.d(TAG, "performOperation: DONE ----");

                doubleFirstInput = 0.0;
            }
            else if (strOperator.equals(getString(R.string.multiply))){

                if (doubleAnswer == 0 && doubleFirstInput != 0) {
                    doubleAnswer = doubleFirstInput;
                    doubleFirstInput = 1.0;
                }
                else if(doubleFirstInput == 0){
                    doubleFirstInput = 1.0;
                }
                doubleAnswer = doubleFirstInput * doubleAnswer ;

                txtAnswer.setText(String.format("=%s", doubleAnswer));
                Log.d(TAG, "performOperation: DONE ***** ");

                doubleFirstInput = 0.0;
            }
            else if (strOperator.equals(getString(R.string.division))){

                if (doubleAnswer == 0 && doubleFirstInput != 0) {
                    doubleAnswer = doubleFirstInput;
                    doubleFirstInput = 1.0;
                }
                else if (doubleFirstInput == 0){
                    doubleFirstInput = 1.0;
                }
                    Double doubleTemp = doubleAnswer;
                    doubleAnswer = doubleFirstInput;
                    doubleFirstInput = doubleTemp;

                    doubleAnswer = doubleFirstInput / doubleAnswer ;
                    txtAnswer.setText(String.format("=%s", doubleAnswer));
                    Log.d(TAG, "performOperation: DONE ////// ");

                    doubleFirstInput = 0.0;

            }
            else if (strOperator.equals(getString(R.string.percent)))
            {
                doubleAnswer = doubleFirstInput / 100 ;
                txtAnswer.setText(String.format("=%s", doubleAnswer));
                Log.d(TAG, "performOperation: " +strOperator);
            }

            else  {
            txtAnswer.setText(String.format("=%s", txtUserInput.getText().toString()));
        }
    }

}
