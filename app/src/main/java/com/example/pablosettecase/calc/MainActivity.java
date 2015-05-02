package com.example.pablosettecase.calc;

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
    private float num1 = 0;
    private float num2 = 0;
    boolean argTwoSet = false;
    private String operation = "";
    private ButtonClickListener btnClick = new ButtonClickListener();
    private int num2Index= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);

        disableSoftInputFromAppearing(input);

        // every button needs a listener
        int btnIds[] = {R.id.btnClear,R.id.btnOther,R.id.btnDel,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnDiv,
                R.id.btn4,R.id.btn5,R.id.btn6,R.id.btnMult,
                R.id.btn7,R.id.btn8,R.id.btn9,R.id.btnMinus,
                R.id.btnPeriod,R.id.btn0,R.id.btnEq,R.id.btnPlus
        };

//        for(int i : btnIds) {
//            Button btn = (Button) findViewById(btnIds[i]);   // DEL
//            btn.setOnClickListener(btnClick);
//        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            num2 = Float.parseFloat(s.substring(num2Index));    // the second arg starts at num2Index
            Log.i("previous op detected", String.valueOf(num2));
            compute();
        } else {
            // this is the first operation on a number, set the proper variables
            num1  = Float.parseFloat(s);
            operation = op;
            num2Index = s.length()+1;
            Log.i("computed num2Index: ", String.valueOf(num2Index));
        }
        //input.setText();
    }

    public void compute() {
        float result = 0;
        // compute
        Log.i("num1: ", String.valueOf(num1));
        Log.i("num2: ", String.valueOf(num2));
        switch(operation) {
            case "/":
                result = num1/num2;
                Log.i("dividing: ", String.valueOf(result));
                break;
            case "*":
                result = num1*num2;
                Log.i("multiplying: ", String.valueOf(result));
                break;
            case "-":
                result = num1-num2;
                Log.i("subtracting: ", String.valueOf(result));
                break;
            case "+":
                result = num1+num2;
                Log.i("adding: ", String.valueOf(result));
                break;
        }

        // set the text and num1 to the result
        // format the result first
        input.setText(format(result));
        num1 = result;
        // set operation to empty string
        operation = "";
        // unset the second argument
        argTwoSet = false;
        // unset the second argument index
        num2Index = 0;
    }

    public void addInput(String n){
        String current = input.getText().toString();

        current += n;

        input.setText(current);
    }

    private class ButtonClickListener implements View.OnClickListener{

        public void onClick(View v) {

            int vId = ((Button)v).getId();
            Log.i("View Id: ", String.valueOf(vId));
            switch (vId) {
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
                    String s = input.getText().toString();
                    num2 = Float.parseFloat(s.substring(num2Index));    // the second arg starts at num2Index
                    compute();
                    break;
                default:
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
