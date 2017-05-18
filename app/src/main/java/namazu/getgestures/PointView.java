package namazu.getgestures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 淳貴 on 2017/01/21.
 */
public class PointView extends View {
    public static ArrayList<TouchPoint> pointList;
    public static ArrayList<Path> paths;
    public static ArrayList<Paint> paints;
    private Paint paint;
    public PointView(Context context){
        super(context);
        pointList = new ArrayList<TouchPoint>();
        paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setColor(Color.YELLOW);

        paths = new ArrayList<Path>();
        paints = new ArrayList<Paint>();
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(paths.size() != 0) {
            for (int i = 0; i < paths.size(); i++) {
                canvas.drawPath(paths.get(i), paints.get(i));
            }
        }
        if(pointList.size() != 0) {
            TouchPoint prev = pointList.get(0);
            for (TouchPoint i : pointList) {
                //i.onDraw(canvas);
                if (prev.pointType != TouchPoint.PointType.END && i.pointType != TouchPoint.PointType.START)
                    canvas.drawLine(prev.x, prev.y, i.x, i.y, paint);
                prev = i;
            }
        }


    }
}
