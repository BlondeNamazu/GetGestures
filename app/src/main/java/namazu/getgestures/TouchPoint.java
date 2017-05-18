package namazu.getgestures;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by 淳貴 on 2017/01/21.
 */
public class TouchPoint {
    int range;
    float x,y;
    Paint paint = new Paint();
    enum PointType{
        START,
        PO,
        END
    }
    PointType pointType;
    public TouchPoint(float x,float y,PointType pointType){
        range = 5;
        this.x = x;
        this.y = y;
        paint.setColor(Color.BLACK);
        this.pointType = pointType;
    }
    public void onDraw(Canvas canvas){
        canvas.drawCircle(x,y,range,paint);
    }
}
