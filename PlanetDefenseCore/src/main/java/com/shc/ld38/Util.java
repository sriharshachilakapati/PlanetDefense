package com.shc.ld38;

import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.cameras.Camera;
import com.shc.silenceengine.input.Touch;
import com.shc.silenceengine.math.Matrix4;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.Vector3;

/**
 * @author Sri Harsha Chilakapati
 */
public class Util
{
    private static Matrix4 invProjView = new Matrix4();
    private static Vector2 mouseInView = new Vector2();

    public static void update()
    {
        invProjView.set(Camera.CURRENT.getProjection())
                .multiply(Camera.CURRENT.getView())
                .invert();
    }

    public static Vector2 getMouseInView()
    {
        Vector3 m = Vector3.REUSABLE_STACK.pop();

        Vector2 pos = Touch.getFingerPosition(Touch.FINGER_0);

        m = unProjectMouse(pos.x, pos.y, m);
        mouseInView.x = m.x;
        mouseInView.y = m.y;

        Vector3.REUSABLE_STACK.push(m);

        return mouseInView;
    }

    // From the docs of gluUnproject
    private static Vector3 unProjectMouse(float x, float y, Vector3 dest)
    {
        final float viewPortX = 0;
        final float viewPortY = 0;
        final float viewPortW = SilenceEngine.display.getWidth();
        final float viewPortH = SilenceEngine.display.getHeight();

        x = x - viewPortX;
        y = SilenceEngine.display.getHeight() - y - 1;
        y = y - viewPortY;

        dest.x = (2 * x) / viewPortW - 1;
        dest.y = (2 * y) / viewPortH - 1;
        dest.z = 2 * (float) 0 - 1;

        // Compute the inverseProjView matrix
        Matrix4 proj = Camera.CURRENT.getProjection();
        Matrix4 view = Camera.CURRENT.getView();
        Matrix4 temp = Matrix4.REUSABLE_STACK.pop();

        temp.set(proj).multiply(view).invert();

        // Project dest using temp
        dest.multiply(temp);

        Matrix4.REUSABLE_STACK.push(temp);

        return dest;
    }
}
