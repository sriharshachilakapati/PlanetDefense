package com.shc.ld38.entities;

import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sri Harsha Chilakapati
 */
public class PathComponent extends Component
{
    public static final List<Vector2> PATH = new ArrayList<>();

    private int   pathIndex   = 0;
    private float midstVertex = 0;

    private float rotation = 90;

    private boolean doRotation;

    public PathComponent(boolean doRotation)
    {
        this.doRotation = doRotation;
    }

    private static float lerpDegrees(float start, float end, float amount)
    {
        float difference = Math.abs(end - start);
        if (difference > 180)
        {
            // We need to add on to one of the values.
            if (end > start)
            {
                // We'll add it on to start...
                start += 360;
            }
            else
            {
                // Add it on to end.
                end += 360;
            }
        }

        // Interpolate it.
        float value = (start + ((end - start) * amount));

        // Wrap it..
        float rangeZero = 360;

        if (value >= 0 && value <= 360)
            return value;

        return (value % rangeZero);
    }

    @Override
    protected void onUpdate(float elapsedTime)
    {
        Vector2 currentPoint = PATH.get(pathIndex % PATH.size());
        Vector2 nextPoint = PATH.get((pathIndex + 1) % PATH.size());

        midstVertex += elapsedTime * 2.5f;

        Vector2 temp = Vector2.REUSABLE_STACK.pop();

        temp.set(currentPoint).lerp(nextPoint, midstVertex);
        transformComponent.setPosition(temp);

        if (doRotation)
        {
            float newRotation = currentPoint.angle(temp) + 90;
            newRotation = lerpDegrees(rotation, newRotation, midstVertex);
            rotation = newRotation;
            transformComponent.setRotation(rotation);
        }

        Vector2.REUSABLE_STACK.push(temp);

        if (midstVertex > 1.0)
        {
            pathIndex++;
            midstVertex = 0;
        }
    }

    static
    {
        PATH.add(new Vector2(1086.0f, 7.0f));
        PATH.add(new Vector2(1079.0f, 25.0f));
        PATH.add(new Vector2(1073.0f, 42.0f));
        PATH.add(new Vector2(1060.0f, 64.0f));
        PATH.add(new Vector2(1040.0f, 103.0f));
        PATH.add(new Vector2(1011.0f, 139.00002f));
        PATH.add(new Vector2(971.0f, 154.00002f));
        PATH.add(new Vector2(911.99994f, 151.00002f));
        PATH.add(new Vector2(865.0f, 140.0f));
        PATH.add(new Vector2(814.0f, 123.000015f));
        PATH.add(new Vector2(767.0f, 104.00003f));
        PATH.add(new Vector2(735.0f, 88.00003f));
        PATH.add(new Vector2(688.0f, 69.00003f));
        PATH.add(new Vector2(645.0f, 65.0f));
        PATH.add(new Vector2(562.0f, 55.0f));
        PATH.add(new Vector2(506.0f, 58.0f));
        PATH.add(new Vector2(458.0f, 66.0f));
        PATH.add(new Vector2(424.0f, 82.0f));
        PATH.add(new Vector2(404.0f, 103.0f));
        PATH.add(new Vector2(392.0f, 117.999985f));
        PATH.add(new Vector2(381.0f, 146.99997f));
        PATH.add(new Vector2(375.0f, 177.99995f));
        PATH.add(new Vector2(370.0f, 211.99994f));
        PATH.add(new Vector2(375.0f, 246.99997f));
        PATH.add(new Vector2(372.0f, 279.99994f));
        PATH.add(new Vector2(379.0f, 299.99997f));
        PATH.add(new Vector2(381.0f, 323.0f));
        PATH.add(new Vector2(398.0f, 341.0f));
        PATH.add(new Vector2(430.0f, 358.0f));
        PATH.add(new Vector2(463.0f, 358.0f));
        PATH.add(new Vector2(499.0f, 360.0f));
        PATH.add(new Vector2(540.0f, 358.0f));
        PATH.add(new Vector2(566.0f, 337.0f));
        PATH.add(new Vector2(595.0f, 310.99997f));
        PATH.add(new Vector2(617.0f, 276.0f));
        PATH.add(new Vector2(633.0f, 249.99997f));
        PATH.add(new Vector2(645.0f, 226.99997f));
        PATH.add(new Vector2(667.0f, 210.0f));
        PATH.add(new Vector2(682.0f, 197.99997f));
        PATH.add(new Vector2(713.0f, 191.99997f));
        PATH.add(new Vector2(729.0f, 185.0f));
        PATH.add(new Vector2(774.0f, 186.99998f));
        PATH.add(new Vector2(821.0f, 188.0f));
        PATH.add(new Vector2(865.0f, 194.99997f));
        PATH.add(new Vector2(902.99994f, 213.99997f));
        PATH.add(new Vector2(920.0f, 230.0f));
        PATH.add(new Vector2(931.0f, 260.0f));
        PATH.add(new Vector2(936.0f, 290.0f));
        PATH.add(new Vector2(934.0f, 329.99997f));
        PATH.add(new Vector2(930.0f, 374.0f));
        PATH.add(new Vector2(922.0f, 415.0f));
        PATH.add(new Vector2(912.0f, 433.0f));
        PATH.add(new Vector2(866.0f, 445.0f));
        PATH.add(new Vector2(802.0f, 438.0f));
        PATH.add(new Vector2(763.0f, 434.0f));
        PATH.add(new Vector2(712.0f, 427.0f));
        PATH.add(new Vector2(655.0f, 419.0f));
        PATH.add(new Vector2(632.0f, 422.0f));
        PATH.add(new Vector2(597.0f, 429.0f));
        PATH.add(new Vector2(560.0f, 439.0f));
        PATH.add(new Vector2(525.99994f, 452.0f));
        PATH.add(new Vector2(490.0f, 469.0f));
        PATH.add(new Vector2(470.0f, 488.0f));
        PATH.add(new Vector2(453.0f, 515.0f));
        PATH.add(new Vector2(431.0f, 540.0f));
        PATH.add(new Vector2(414.0f, 565.0f));
        PATH.add(new Vector2(403.0f, 585.0f));
        PATH.add(new Vector2(402.0f, 605.0f));
        PATH.add(new Vector2(396.0f, 665.0f));
        PATH.add(new Vector2(398.0f, 688.0f));
    }
}
