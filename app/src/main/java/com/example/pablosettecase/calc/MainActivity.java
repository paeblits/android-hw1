package com.example.pablosettecase.calc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    private EditText input;
    private float numOne = 0;
    private float numTwo = 0;
    boolean argTwoSet = false;
    boolean argOneSet = false;
    private boolean power;
    private double powA;
    private String operation;
    private String currentInput;
    private ButtonClickListener btnClick = new ButtonClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operation = "";
        currentInput = "";

        // get the input from the other intent if there is one
        Intent intent = getIntent();
        currentInput = intent.getStringExtra("currentInput");
        numOne = intent.getFloatExtra("numOne", 0);
        numTwo = intent.getFloatExtra("numTwo", 0);
        argOneSet = intent.getBooleanExtra("argOneSet", false);
        argTwoSet = intent.getBooleanExtra("argTwoSet", false);
        power = intent.getBooleanExtra("power", false);
        powA = intent.getDoubleExtra("powA", -1);

        // set the input text
        input = (EditText) findViewById(R.id.input);
        input.setText(currentInput);
        disableSoftInputFromAppearing(input);

        // every button needs a listener
        int btnIds[] = {R.id.btnClear,R.id.btnSwitch,R.id.btnDel,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnDiv,
                R.id.btn4,R.id.btn5,R.id.btn6,R.id.btnMult,
                R.id.btn7,R.id.btn8,R.id.btn9,R.id.btnMinus,
                R.id.btnPeriod,R.id.btn0,R.id.btnEq,R.id.btnPlus
        };

//  * * * * * * * * * * * * * * * * * * * * * * * * * * *
//        for(int i : btnIds) {
//            Button btn = (Button) findViewById(btnIds[i]);   // DEL
//            btn.setOnClickListener(btnClick);
//        }
//  * * * * * * * * * * * * * * * * * * * * * * * * * * *
// for some annoying reason my for loops kept giving me arrayIndexOutOfBounds no matter what I tried!

        ((Button) findViewById(btnIds[0])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[1])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[2])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[3])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[4])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[5])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[6])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[7])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[8])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[9])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[10])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[11])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[12])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[13])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[14])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[15])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[16])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[17])).setOnClickListener(btnClick);
        ((Button) findViewById(btnIds[18])).setOnClickListener(btnClick);

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

        switch(id) {
            case R.id.action_keyboard:
                switchKeyboard();
                break;
            case R.id.action_author:
                alertAuthor();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    public void alertAuthor() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Assignment 1");
        alertDialog.setMessage("Author: Pablo Settecase\nCS454 Mark Sargent\n5-3-2015");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }

    public void switchKeyboard(){

        Intent intent = new Intent(MainActivity.this, Calc2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("currentInput", input.getText().toString());
        intent.putExtra("numOne", numOne);
        intent.putExtra("numTwo", numTwo);
        intent.putExtra("argOneSet",argOneSet);
        intent.putExtra("argTwo",argTwoSet);
        intent.putExtra("power", power);
        intent.putExtra("powA", powA);

        startActivity(intent);
    }

    public void operation(String op){

        // get everything in the input field
        String s = input.getText().toString();
        String current = s;
        Log.i("operation: ", op);
        current += op;
        Log.i("current input: ", current);
        // update the input field with the new symbol ("+", "-", etc.)
        input.setText(current);

        // check if we should compute something or not
        if(operation.length() > 0) {
            // there has already been a previous operation entered, compute it first
            numTwo = Float.parseFloat(s);
            argTwoSet = true;
            Log.i("previous op detected: ", String.valueOf(numTwo));
            compute();
        } else {
            // this is the first operation on a number
            numOne = Float.parseFloat(s);
            argOneSet = true;
            input.setText("");
        }
        operation = op;
    }

    public void pow() {
        // finish calculating a power if necessary
        if(input.getText().toString().length() > 0) {
            double b = Double.parseDouble(input.getText().toString());
            Log.i("pow ", " . . pow . .");
            Log.i("a: ", String.valueOf(powA));
            Log.i("b: ", String.valueOf(b));

            try {
                double p = Math.pow(powA,b);
                input.setText(String.valueOf(format(p)));
            } catch(Exception e) {
                input.setText("error pow");
            }
        }
    }

    public void compute() {
        float result = 0;
        // compute
        Log.i("numOne: ", String.valueOf(numOne));
        Log.i("numTwo: ", String.valueOf(numTwo));

        switch(operation) {
            case "/":
                try{
                    result = numOne / numTwo;
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("dividing: ", String.valueOf(result));
                break;
            case "*":
                try{
                    result = numOne * numTwo;
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("multiplying: ", String.valueOf(result));
                break;
            case "-":
                try{
                    result = numOne - numTwo;
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("subtracting: ", String.valueOf(result));
                break;
            case "+":
                try{
                    result = numOne + numTwo;
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("adding: ", String.valueOf(result));
                break;
        }

        // set the text and numOne to the result
        // format the result first
        input.setText(format(result));
        numOne = result;
        // unset the second argument
        argTwoSet = false;
        power = false;
        powA = -1;
        operation = "";
    }

    public void addInput(String n){
        String current = input.getText().toString();

        current += n;

        input.setText(current);
    }

    public void clear() {
        input.setText("");
        numOne = 0;
        numTwo = 0;
        operation = "";
        argOneSet = false;
        argTwoSet = false;
    }

    private class ButtonClickListener implements View.OnClickListener{

        public void onClick(View v) {

            int vId = ((Button)v).getId();
            Log.i("View Id: ", String.valueOf(vId));

            switch (vId) {
                case R.id.btnSwitch:
                    switchKeyboard();
                    break;
                case R.id.btnClear:
                    clear();
                    break;
//                case R.id.btnPeriod:
//                TODO: for now I assume the user uses period/decimal correctly
//                    break;
                case R.id.btnDel:
                    if(input.getText().length() > 0) {
                        // remove the last element from input
                        String s = input.getText().toString();
                        s = s.substring(0,s.length()-1);
                        input.setText(s);
                    }
                    break;
                case R.id.btnDiv:       // divide
                    Log.i("btnDiv pressed: ", String.valueOf(vId));
                    operation("/");
                    break;
                case R.id.btnMult:
                    Log.i("btnMult pressed: ", String.valueOf(vId));
                    operation("*");
                    break;
                case R.id.btnMinus:
                    Log.i("btnMinus pressed: ", String.valueOf(vId));
                    operation("-");
                    break;
                case R.id.btnPlus:
                    Log.i("btnPlus pressed: ", String.valueOf(vId));
                    operation("+");
                    break;
                case R.id.btnEq:
                    // only compute if the second argument has been set
                    Log.i("btnEq pressed: ", String.valueOf(vId));
                    if(argOneSet) {
                        numTwo = Float.parseFloat(input.getText().toString());    // the second arg starts at num2Index
                        compute();
                    } else if(power) {
                        pow();
                    }
                    break;
                default:
                    String in = ((Button)v).getText().toString();
                    addInput(in);
                    break;
            }
        }

    }

    public static String format(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
