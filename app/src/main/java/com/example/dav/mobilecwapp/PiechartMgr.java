package com.example.dav.mobilecwapp;



import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PiechartMgr extends View
    //This class manages the method for drawing the pie chart while PiechartActivity class handles displaying the image
{
    public static final int WAIT = 0;
    public static final int IS_READY_TO_DRAW = 1;// Beginning state for onDraw method
    public static final int IS_DRAW = 2; // Finished return state for onDraw method
    private static final float START_INC = 30;
    private Paint fillPaints = new Paint(); //fill & stroke variables required to draw pie chart
    private Paint strokePaints = new Paint(); // both variables define paint and canvas

    // list of variables which act as parameters for the Pie chart's size and alignment on the canvas
    private int Width; // all used for setGeometry method
    private int Height;
    private int GapTop;
    private int GapBottom;
    private int Gapleft;
    private int Gapright;
    private int State = WAIT; // 0 value for starting draw state within loop
    private float Start;
    private float Sweep;
    private int mMaxConnection;
    private List<PieDetailsItem> mdataArray; // array used collect loop variables for drawing circle

    public PiechartMgr(Context context) {
        super(context);
    }

    public PiechartMgr(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void onDraw(Canvas canvas) { // This method is called within the PiechartActivity class
        super.onDraw(canvas);
        if (State != IS_READY_TO_DRAW) {
            return;
        }
        fillPaints.setStyle(Paint.Style.FILL);
        strokePaints.setStyle(Paint.Style.STROKE);
        RectF rectangle = new RectF(Gapleft, GapTop, Width - Gapright, Height - GapBottom);
        Start = START_INC;
        PieDetailsItem item;
        for (int i = 0; i < mdataArray.size(); i++) { // This loop is used to draw the circle based on the parameters of rectanglular shape
            item = (PieDetailsItem) mdataArray.get(i);
            fillPaints.setColor(item.color);
            Sweep = (float) 360* ((float) item.count / (float) mMaxConnection);
            canvas.drawArc(rectangle, Start, Sweep, true, fillPaints);
            canvas.drawArc(rectangle, Start, Sweep, true, strokePaints);
            Start = Start + Sweep;
        }

        State = IS_DRAW;
    }

    public void setGeometry(int width, int height, int gapleft, int gapright, // called within the PiechartActivity class
                            int gaptop, int gapbottom) {

        Width = width;
        Height = height;
        Gapleft = gapleft;
        Gapright = gapright;
        GapBottom = gapbottom;
        GapTop = gaptop;

    }

    public void setData(List<PieDetailsItem> data, int maxconnection) { // called within the PiechartActivity class
        mdataArray = data;
        mMaxConnection = maxconnection;
        Log.w(" Max Connection  ", maxconnection + " " + "  Adataarray :"
                + data.toString());
        State = IS_READY_TO_DRAW;
    }




}

