/**
 * Copyright 2025 Chagas Inc.
 * Copyright 2025 Chagas LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * This file contains components derived from the Android Open Source Project.
 * Copyright (C) The Android Open Source Project
 */

package com.android.systemui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.FrameLayout;
import android.os.Handler;
import java.util.Calendar;

public class ChagasSystemUIView extends FrameLayout {

    private InnerSystemUIView mInnerView;
    private Handler mHandler = new Handler();
    private boolean loaded = false;
    private float progress = 0f;

    public ChagasSystemUIView(Context context) {
        super(context);

        mInnerView = new InnerSystemUIView(context);
        addView(mInnerView, new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));

        startLoading();
    }

    private void startLoading() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress += 3f;
                if (progress >= 100f) {
                    loaded = true;
                }
                mInnerView.invalidate();
                startLoading();
            }
        }, 1);
    }

    private class InnerSystemUIView extends View {

        Paint textPaint = new Paint();
        Paint iconPaint = new Paint();
        Paint loadPaint = new Paint();
        Paint barPaint = new Paint();

        int batteryLevel = 0;

        public InnerSystemUIView(Context context) {
            super(context);

            textPaint.setAntiAlias(true);
            textPaint.setTextSize(48f);
            textPaint.setColor(0xFFFFFFFF);

            iconPaint.setAntiAlias(true);
            iconPaint.setStrokeWidth(4f);
            iconPaint.setStyle(Paint.Style.STROKE);
            iconPaint.setColor(0xFFFFFFFF);

            barPaint.setColor(0xFFFFFFFF);
            loadPaint.setColor(0x55FFFFFF);

            registerBatteryReceiver();

            updateLooper();
        }

        private void updateLooper() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                    updateLooper();
                }
            }, 1);
        }

        private void registerBatteryReceiver() {
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = getContext().registerReceiver(null, filter);

            if (batteryStatus != null) {
                batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int w = getWidth();
            int h = getHeight();

            if (!loaded) {
                textPaint.setTextSize(56f);
                canvas.drawText("Sistema de Interface está carregando…", 40, h / 2f - 40, textPaint);

                float barW = w - 80;
                float barH = 25;

                canvas.drawRect(40, h / 2f, 40 + barW, h / 2f + barH, loadPaint);
                canvas.drawRect(40, h / 2f, 40 + (barW * (progress / 100f)), h / 2f + barH, barPaint);

                return;
            }

            Calendar c = Calendar.getInstance();
            String hora = String.format("%02d:%02d:%02d",
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    c.get(Calendar.SECOND));

            textPaint.setTextSize(60f);
            canvas.drawText(hora, 50, 100, textPaint);

            float left = w - 200;
            float top = 40;
            float right = w - 80;
            float bottom = 120;

            RectF rect = new RectF(left, top, right, bottom);
            canvas.drawRect(rect, iconPaint);

            float inside = (batteryLevel / 100f) * (right - left - 20);
            canvas.drawRect(left + 10, top + 10, left + 10 + inside, bottom - 10, barPaint);

            textPaint.setTextSize(40f);
            canvas.drawText(batteryLevel + "%", left - 90, top + 60, textPaint);
        }
    }
}
