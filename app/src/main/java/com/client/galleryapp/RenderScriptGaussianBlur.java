package com.client.galleryapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * Created by lizhaotailang on 2017/1/12.
 */

public class RenderScriptGaussianBlur {

    private RenderScript renderScript;
    private static final float BITMAP_SCALE = 0.4f;

    //Set the radius of the Blur. Supported range 0 < radius <= 25
    private static float BLUR_RADIUS = 10.5f;

    public RenderScriptGaussianBlur(@NonNull Context context) {
        this.renderScript = RenderScript.create(context);
    }

    public Bitmap gaussianBlur(@IntRange(from = 0, to = 25) int radius, Bitmap original) {
//        Allocation input = Allocation.createFromBitmap(renderScript, original);
//        Allocation output = Allocation.createTyped(renderScript, input.getType());
//        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
//        scriptIntrinsicBlur.setRadius(radius);
//        scriptIntrinsicBlur.setInput(input);
//        scriptIntrinsicBlur.forEach(output);
//        output.copyTo(original);
//        return original;

        Bitmap outputBitmap = null;

        if (original != null) {

            if (radius == 0) {
                return original;
            }

            if (radius < 1) {
                radius = 1;
            }

            if (radius > 25) {
                radius = 25;
            }

            BLUR_RADIUS = radius;

            int width = Math.round(original.getWidth() * BITMAP_SCALE);
            int height = Math.round(original.getHeight() * BITMAP_SCALE);

            Bitmap inputBitmap = Bitmap.createScaledBitmap(original, width, height, false);
            outputBitmap = Bitmap.createBitmap(inputBitmap);


            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            Allocation tmpIn = Allocation.createFromBitmap(renderScript, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
            theIntrinsic.setRadius(BLUR_RADIUS);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);
        }

        return outputBitmap;
    }

}

