package com.abdevelopers.magicsquare;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.GridLayout.spec;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    private EditText square_size;
    private int halfScreenWidth;
    private int quarterScreenWidth;
    private GridLayout gridMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(create(this));
    }


    @Override
    public void onClick(View view) {
        String square_size_value = square_size.getText().toString();

        //Passing Intent to Magic grid with odd N
        if(!square_size_value.equals("") && !square_size_value.equals("0") && checkOdd(square_size_value)) {
            Intent i = new Intent(MainActivity.this, DynamicMagicSquare.class);
            i.putExtra("square_size", square_size_value);
            //i.putExtra("column", columnValue);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,getString(R.string.toastvalue), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkOdd(String square_size_value) {
        if(Integer.parseInt(square_size_value)%2!=0){
            return true;
        }else{
            return false;
        }
    }

    public View create(Context context) {
            //Getting point on Screen
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);

            //Getting Screen Width
            int screenWidth = size.x;
             halfScreenWidth = (int)(screenWidth *0.5);
             quarterScreenWidth = (int)(halfScreenWidth * 1);

            //Adding ScrollView to GridLayout

            ScrollView sv = new ScrollView(this);

            //Initializing Grid
             gridMain = initializeGrid(context);

            //Adding View for Particular Row and Column
            AddFirstView(context,gridMain,halfScreenWidth,quarterScreenWidth);

            //Adding GridView to ScrollView
            sv.addView(gridMain);
            return sv;
        }


    private GridLayout initializeGrid(Context context) {
        //Initializing GridLayout
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setUseDefaultMargins(true);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);

        //Handling Configuration Changes
        Configuration configuration = context.getResources().getConfiguration();
        if ((configuration.orientation == Configuration.ORIENTATION_PORTRAIT)) {
            gridLayout.setColumnOrderPreserved(false);
        } else {
            gridLayout.setRowOrderPreserved(false);
        }

        return  gridLayout;
    }

    private void AddFirstView(Context context, GridLayout gridLayout, int halfScreenWidth, int quarterScreenWidth) {
        {
            GridLayout.LayoutParams first = new GridLayout.LayoutParams(spec(0, GridLayout.CENTER), spec(0, GridLayout.CENTER));
            first.width = halfScreenWidth;
            first.setMargins(5,5,0,0);
            TextView v = new TextView(context);
            v.setGravity(Gravity.CENTER_HORIZONTAL);
            v.setText(getString(R.string.row));
            gridLayout.addView(v,first);
        }
        {
            GridLayout.LayoutParams first = new GridLayout.LayoutParams(spec(0), spec(1));
            first.width = halfScreenWidth;
            first.setMargins(5,5,0,0);
            square_size = new EditText(context);
            square_size.setInputType(InputType.TYPE_CLASS_NUMBER);
            gridLayout.addView(square_size,first);
        }
        {
            GridLayout.Spec centerInAllColumns    = spec(0, 4, GridLayout.CENTER);
            GridLayout.LayoutParams first = new GridLayout.LayoutParams(spec(2), centerInAllColumns);
            first.setMargins(5,20,0,0);
            first.width = halfScreenWidth;
            Button button = new Button(context);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            button.setText(getString(R.string.buttontext));
            button.setTextColor(getResources().getColor(R.color.white));
            gridLayout.addView(button,first);
            button.setOnClickListener(this);
        }


    }



}
