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


public class Calc2 extends ActionBarActivity {
    private EditText input;
    private double numOne = 0;
    private double numTwo = 0;
    boolean argOneSet = false;
    boolean argTwoSet = false;
    private boolean power;
    private double powA;
    private String operation;
    private String currentInput;
    private ButtonClickListener btnClick = new ButtonClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc2);
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
        int btnIds[] = {R.id.btnClear,R.id.btnSwitch,R.id.btnDel,R.id.btnSin,R.id.btnCos,R.id.btnTan,
                R.id.btnLn,R.id.btnLog,R.id.btnPi,R.id.btnE,
                R.id.btnPercent,R.id.btnFact,R.id.btnSqrt,R.id.btnPow,
                R.id.btnOpenParen,R.id.btnCloseParen
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
        AlertDialog alertDialog = new AlertDialog.Builder(Calc2.this).create();
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

        Intent intent = new Intent(Calc2.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("currentInput", input.getText().toString());
        intent.putExtra("numOne", numOne);
        intent.putExtra("numTwo", numTwo);
        intent.putExtra("argOneSet", argOneSet);
        intent.putExtra("argTwo",argTwoSet);
        intent.putExtra("power", power);
        intent.putExtra("powA", powA);

        startActivity(intent);
    }

    public void pow(){
        powA = Double.parseDouble(input.getText().toString());
        input.setText("");
        power = true;
    }

    public double fact(int n) {
        double result = (double)n;
        for(int i = n-1; i > 0; i-- ) {
            result *= i;
        }
        return result;
    }

    public void compute(int command) {

        double result = 0;
        double temp = Double.parseDouble(input.getText().toString());
        // compute
        Log.i("temp: ", String.valueOf(temp));
        Log.i("numOne: ", String.valueOf(numOne));
        Log.i("numTwo: ", String.valueOf(numTwo));
        switch(command) {
            case 0:
                try{
                    result = Math.sin(temp);
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("sin: ", String.valueOf(result));
                break;
            case 1:
                try{
                    result = Math.cos(temp);
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("cos: ", String.valueOf(result));
                break;
            case 2:
                try{
                    result = Math.tan(temp);
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("tangent: ", String.valueOf(result));
                break;
            case 3:
                try{
                    result = Math.log(temp);
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("nat log: ", String.valueOf(result));
                break;
            case 4:
                try{
                    result = Math.log10(temp);
                } catch(Exception e) {
                    input.setText("error");
                }
                Log.i("log base 10: ", String.valueOf(result));
                break;
            case 5:         // PERCENT
                if(temp < 1) {
                    result = temp*10;
                }
                break;
            case 6:         // FACTORIAL
                if(temp > 2) {
                    result = fact((int)temp);
                } else {
                    result = temp;
                }
                break;
            case 7:         // SQRT
                if(temp > 0) {
                    try{
                        result = Math.sqrt(temp);
                    } catch (Exception e) {
                        input.setText("error");
                    }
                }
                break;
        }

        // format the result first
        input.setText(format(result));

        if(argOneSet) {
            numTwo = result;
            //argTwoSet = true;
        }

        numOne = result;
        // set operation to empty string
        operation = "";
        // unset the second argument
        argTwoSet = false;
        // unset the second argument index
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
                    input.setText("");
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
                case R.id.btnSin:
                    Log.i("btnSin pressed: ", String.valueOf(vId));
                    compute(0);
                    break;
                case R.id.btnCos:
                    Log.i("btnCos pressed: ", String.valueOf(vId));
                    compute(1);
                    break;
                case R.id.btnTan:
                    Log.i("btnTan pressed: ", String.valueOf(vId));
                    compute(2);
                    break;
                case R.id.btnLn:
                    Log.i("btnLn pressed: ", String.valueOf(vId));
                    compute(3);
                    break;
                case R.id.btnLog:
                    Log.i("btnLog pressed: ", String.valueOf(vId));
                    compute(4);
                    break;
                case R.id.btnPi:
                    Log.i("btnPi pressed: ", String.valueOf(vId));
                    input.setText(String.valueOf(Math.PI));
                    break;
                case R.id.btnE:
                    Log.i("btnLn pressed: ", String.valueOf(vId));
                    input.setText(String.valueOf(Math.E));
                    break;
                case R.id.btnPercent:
                    Log.i("btnPercent pressed: ", String.valueOf(vId));
                    compute(5);
                    break;
                case R.id.btnFact:
                    Log.i("btnFact pressed: ", String.valueOf(vId));
                    compute(6);
                    break;
                case R.id.btnSqrt:
                    Log.i("btnSqrt pressed: ", String.valueOf(vId));
                    compute(7);
                    break;
                case R.id.btnPow:
                    Log.i("btnPow pressed: ", String.valueOf(vId));
                    if(input.getText().toString().length() >= 0) {
                        pow();
                    }
                    break;
                // did not finish parenthesis
                case R.id.btnOpenParen:
                    break;
                case R.id.btnCloseParen:
                    break;
                default:
                    input.setText("");
                    String in = ((Button)v).getText().toString();
                    addInput(in);
                    if(operation.length() > 0){
                        argTwoSet = true;
                    }
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
