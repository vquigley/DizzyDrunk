package com.grangewood.dizzydrunk.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by vquig on 07/10/15.
 */
public class Wheel {
    private Bitmap image;
    int defaultWidth = 640;
    int defaultHeight = 640;
    int shapeOutsideBorder = 10;
    double shapeBetweenBorder = 1.5D;

    public Wheel() {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        image = Bitmap.createBitmap(defaultWidth, defaultHeight, config);
    }


    public Bitmap create(ArrayList<Player> segments, Canvas canvas) {

        float currentAngle = 0;
        float sliceSize = 360.0f / segments.size();

        for (Player segment: segments) {
            InsertArc(currentAngle, sliceSize, segment, canvas);

            currentAngle += sliceSize;
        }

       // currentAngle = 0;
//
       // for (int index = 0; index < segments.size(); ++index) {
       //     BufferedImage picture = null;
//
       //     try {
       //         picture = ImageIO.read(new File(segments.get(index).getPictureUrl()));
       //     }
       //     catch (IOException ex) {
//
       //     }
//
       //     if (picture != null) {
//
       //         Arc2D.Double arc = new Arc2D.Double(shapeOutsideBorder /2,
       //                 shapeOutsideBorder /2,
       //                 (double)defaultWidth - shapeOutsideBorder,
       //                 (double)defaultHeight - shapeOutsideBorder,
       //                 currentAngle + shapeBetweenBorder,
       //                 sliceSize - shapeBetweenBorder*2,
       //                 Arc2D.PIE);
//
       //         AffineTransformOp op = getAffineTransformOp(sliceSize, index, picture);
       //         BufferedImage clippedImage = clip(picture, arc, op);
//
       //         scalePicture(picture);
       //         image.createGraphics().drawImage(clippedImage,
       //                 null,
       //                 0,
       //                 0);
//
       //         currentAngle += sliceSize;
       //     }
       // }
        return image;
    }

   // private void scalePicture(BufferedImage picture) {
//
   // }
//
   // private AffineTransformOp getAffineTransformOp(double sliceSize, int index, BufferedImage picture) {
   //     double rotationRequired = -1 * Math.toRadians((index * sliceSize + sliceSize / 2));
//
   //     AffineTransform tx = new AffineTransform();
   //     tx.rotate(Math.toRadians(90), picture.getWidth() / 2, picture.getHeight() / 2);
   //     tx.rotate(rotationRequired, picture.getWidth() / 2, picture.getHeight() / 2);
   //     tx.translate(0, - (defaultWidth / 2 - picture.getWidth() / 2));
//
//
   //     //tx.scale();
   //     return new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
   // }
//
   // public BufferedImage clip( BufferedImage image, Shape clip, AffineTransformOp op )
   // {
   //     BufferedImage out = new BufferedImage( defaultWidth, defaultHeight, image.getType() );
   //     Graphics2D g = out.createGraphics();
   //     g.setClip( clip );
   //     g.drawImage(image, op, defaultWidth / 2 - image.getWidth() / 2,
   //             defaultHeight / 2 - image.getHeight() / 2 );
   //     g.dispose();
//
   //     return out;
   // }

    private void InsertArc(float currentAngle, float sliceSize, Player segment, Canvas canvas) {


        RectF rectF = new RectF(0, defaultHeight, defaultWidth, 0);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(segment.getColor());
        canvas.drawText(segment.getName(), 0, 0, paint);
        canvas.drawArc(rectF, currentAngle, sliceSize, true, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setColor(Color.BLACK);

        canvas.drawArc(rectF, currentAngle, sliceSize, true, paint);
    }
}