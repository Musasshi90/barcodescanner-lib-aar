package com.google.zxing.client.android.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.zxing.client.android.camera.CameraManager;
import barcodescanner.xservices.nl.barcodescanner.R;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class LineAnimationView extends View {
    private boolean revAnimation;
    private float endY;
    private final Paint paint;
    private float lineWidth = 4;
    private float lineHeight = 5;
    private int lineRed;
    private int lineYellow;
    private boolean canDrawLine = false;
    private String TAG = LineAnimationView.class.getSimpleName();
    private CameraManager cameraManager;

    // This constructor is used when the class is built from an XML resource.
    public LineAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every
        // time in onDraw().
        paint = new Paint();
        Resources resources = getResources();
        lineRed = resources.getColor(R.color.line_red);
        lineYellow = resources.getColor(R.color.line_yellow);
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        if (!canDrawLine) return;
        // draw horizontal line
        int[] colors = {lineRed, lineYellow, lineRed};
        float[] position = {0f, 0.5f, 1f};
        paint.setShader(new LinearGradient(frame.left, endY, frame.right, endY, colors, position, Shader.TileMode.MIRROR));
        paint.setStrokeWidth(Float.valueOf(lineWidth));

        if (endY == 0) {
            endY = frame.top;
        }
        // draw the line to product animation
        if (endY >= frame.bottom + lineHeight) {
            revAnimation = true;
        } else if (endY == frame.top + lineHeight) {
            revAnimation = false;
        }

        // check if the line has reached to bottom
        if (revAnimation) {
            endY -= lineHeight;
        } else {
            endY += lineHeight;
        }
        canvas.drawLine(frame.left, endY, frame.right, endY, paint);
        invalidate();
    }

    public void drawLineAnimation() {
        canDrawLine = true;
        Log.i(TAG, "Can draw the line");
        invalidate();
    }
}
