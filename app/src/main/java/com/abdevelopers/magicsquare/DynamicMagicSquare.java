package com.abdevelopers.magicsquare;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.widget.GridLayout.spec;

/**
 * Created by root on 20/1/17.
 */
public class DynamicMagicSquare extends AppCompatActivity {

    int SQUARE_SIZE = 0;
    int magicSquareMatrix[][] = new int[][]{};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(create(DynamicMagicSquare.this));
    }

    public View create(Context context) {

        //Row & Column For Grid
        Bundle bundle = getIntent().getExtras();

        //Getting Value from Acitivty
        if(bundle.getString("square_size")!= null)
        {
              SQUARE_SIZE = Integer.parseInt(bundle.getString("square_size").toString());
              magicSquareMatrix = new int[SQUARE_SIZE][SQUARE_SIZE];
        }


        float divideCell = (float) 1/SQUARE_SIZE;

        //Getting point on Screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        //Getting Screen Width
        int screenWidth = size.x;
        int halfScreenWidth = (int)(screenWidth *divideCell);
        int quarterScreenWidth = (int)(halfScreenWidth * 1);


        //Adding ScrollView to GridLayout

        ScrollView sv = new ScrollView(this);

        //Initialising GridLayout
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

        //Calculating magic square and storing in matrix
        calculateMagicSqaure();

        //Adding grid views and values
        for(int i=0;i<SQUARE_SIZE;i++)
        {
            for(int j=0;j<SQUARE_SIZE;j++)
            {
                AddView(spec(i),spec(j),context,halfScreenWidth,quarterScreenWidth,gridLayout,magicSquareMatrix[i][j]);
            }
        }

        //Adding GridView to ScrollView
        sv.addView(gridLayout);
        return sv;
    }

    private void calculateMagicSqaure() {

        int initial_value=1;
        int row = SQUARE_SIZE-1;
        int column = ((SQUARE_SIZE-1)/2);

        //initializing first value
        magicSquareMatrix[row][column]=initial_value;
        initial_value++;
        for (int num=initial_value; num <= SQUARE_SIZE*SQUARE_SIZE; num++){

            int last_row = row;
            int last_column = column;

            //incrementing row and column diagonally right
            if(row+1 < SQUARE_SIZE){
                row = row+1;
            }else{
                row = 0;
            }

            if(column+1 < SQUARE_SIZE){
                column = column+1;
            }else{
                column = 0;
            }

            //checking if it is already filled
            if(magicSquareMatrix[row][column]!=0){
                row = last_row;
                column = last_column;

                if((row-1)!=-1){
                    row = row-1;
                }else{
                    row = SQUARE_SIZE-1;
                }
            }
            magicSquareMatrix[row][column]=initial_value;
            initial_value++;
        }

    }


    public void AddView(GridLayout.Spec row, GridLayout.Spec col, Context context, int halfScreenWidth, int quarterScreenWidth, GridLayout gridLayout, int value){
        {
            //Preparing Grid Cell
            GridLayout.LayoutParams second = new GridLayout.LayoutParams(row, col);
            second.setMargins(5,5,0,0);
            second.width = halfScreenWidth;
            second.height = quarterScreenWidth;

            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(this);
            tv.setLayoutParams(lparams);
            tv.setGravity(Gravity.CENTER);
            tv.setText(value+"");

            //Adding view to Grid Cell
            gridLayout.addView(tv, second);

        }

    }


}

