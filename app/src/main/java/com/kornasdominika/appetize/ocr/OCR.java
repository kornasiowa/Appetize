package com.kornasdominika.appetize.ocr;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class OCR {

    public static StringBuilder extractTextFromImage(Context context, Bitmap bitmap) {
        TextRecognizer recognizer = new TextRecognizer.Builder(context).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sparseArray.size(); i++) {
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();
            stringBuilder.append(str);
        }

        return stringBuilder;
    }
}
