package org.apache.cordova.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import org.apache.cordova.camera.ScriptC_rotator;

public class Rotator {

    private final Context context;

    public Rotator(Context context) {
        this.context = context;
    }

    public Bitmap rotate(Bitmap bitmap, int rotate) {
        RenderScript rs = RenderScript.create(context);
        ScriptC_rotator script = new ScriptC_rotator(rs);
        script.set_inWidth(bitmap.getWidth());
        script.set_inHeight(bitmap.getHeight());
        Allocation sourceAllocation = Allocation.createFromBitmap(rs, bitmap,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        bitmap.recycle();
        script.set_inImage(sourceAllocation);

        int targetHeight = bitmap.getHeight();
        int targetWidth = bitmap.getWidth();
        if (rotate == 90 || rotate == 270) {
            targetHeight = bitmap.getWidth();
            targetWidth = bitmap.getHeight();
        }
        Bitmap.Config config = bitmap.getConfig();
        Bitmap target = Bitmap.createBitmap(targetWidth, targetHeight, config);
        final Allocation targetAllocation = Allocation.createFromBitmap(rs, target,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        if (rotate == 90) {
            script.forEach_rotate_90_counterclockwise(targetAllocation, targetAllocation);
        } else if (rotate == 180) {
            script.forEach_rotate_180(targetAllocation, targetAllocation);
        } else if (rotate == 270) {
            script.forEach_rotate_270_counterclockwise(targetAllocation, targetAllocation);
        }

        targetAllocation.copyTo(target);
        rs.destroy();
        return target;
    }
}
