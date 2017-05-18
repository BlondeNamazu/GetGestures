package namazu.getgestures;

        import android.app.Activity;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Path;
        import android.os.Bundle;
        import android.text.method.Touch;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.TextView;

        import java.util.ArrayList;

public class MainActivity extends Activity
        implements      GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private GestureDetector ges;
    PointView pointView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointView = new PointView(this);
        setContentView(pointView);
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ges = new GestureDetector(this, this);
    }


    // タッチイベント時に呼ばれる
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ges.onTouchEvent(event);
        return false;
    }

    // 押下時に呼ばれる
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("sample", "onDown() X:"+e.getX()+" Y:"+e.getY());
        pointView.pointList.add(new TouchPoint(e.getX(), e.getY(), TouchPoint.PointType.START));

        return false;
    }

    // プレス時に呼ばれる(onDownが先に呼ばれ、意味が異なる)
    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("sample", "onShowPress()");
    }

    // 長押し時に呼ばれる
    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("sample", "onLongPress()");
    }

    // フリック時に呼ばれる
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float x, float y) {
        Log.d("sample", "onFling()");
        pointView.pointList.add(new TouchPoint(e2.getX(), e2.getY(), TouchPoint.PointType.END));
        createPath();
        setContentView(pointView);
        return false;
    }

    // スクロール時に呼ばれる
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        /*
        Log.d("sample", "e2 X:" + e2.getX() + " Y:" + e2.getY() + " Size:" + pointView.pointList.size()
        );
        */
        pointView.pointList.add(new TouchPoint(e2.getX(), e2.getY(), TouchPoint.PointType.PO));
        setContentView(pointView);
        return false;
    }

    // シングルアップ時に呼ばれる（ダブルタップ時も呼ばれる）
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("sample", "onSingleTapUp()");
        return false;
    }

    // シングルタップ時に呼ばれる（ダブルタップ時は呼ばれない）
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("sample", "onSingleTapConfirmed()");
        createPath();
        return false;
    }

    // ダブルタップ時に呼ばれる
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("sample", "onDoubleTap()");
        pointView.pointList.clear();
        pointView.paths.clear();
        setContentView(pointView);
        return false;
    }

    // ダブルタップイベント時に呼ばれる。「押す、スクロール、離す」で呼び出される。
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d("sample", "onDoubleTapEvent()");
        pointView.pointList.clear();
        pointView.paths.clear();
        return false;
    }

    public void createPath(){
        if(PointView.pointList.size()<2) {
            PointView.pointList.clear();
            return;
        }
        Path newPath = new Path();
        newPath.moveTo(PointView.pointList.get(0).x, PointView.pointList.get(0).y);
        for(int i=1;i<PointView.pointList.size();i++){
            newPath.lineTo(PointView.pointList.get(i).x, PointView.pointList.get(i).y);
        }
        newPath.close();
        PointView.paths.add(newPath);
        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        //paint.setColor(Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
        paint.setColor(Color.RED);
        PointView.paints.add(paint);
        PointView.pointList.clear();
    }
}

