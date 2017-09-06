package com.smyhvae.widgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by smyhvae on 2016/9/7.
 */
public class TestWidget extends AppWidgetProvider {

    public static final String WIDGET_BTN_ACTION = "widget_btn_action";
    private static TestWidget sInstance;
    private Intent intent;
    private Context context;

    static synchronized TestWidget getInstance() {
        if (sInstance == null) {
            sInstance = new TestWidget();
        }
        return sInstance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent != null && TextUtils.equals(intent.getAction(), WIDGET_BTN_ACTION)) { //当intent不为空，且action匹配成功时，就接收广播，然后点击事件成功
            Log.i(WIDGET_BTN_ACTION, "is clicked");
            //接下来开始做点击事件里面的内容
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);//注意：需要【重新】构造一个RemoteViews
            remoteViews.setTextViewText(R.id.widget_tv, "be clicked");
            remoteViews.setTextColor(R.id.widget_tv, Color.RED);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);// 单例模式
            ComponentName componentName = new ComponentName(context, TestWidget.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);//setText之后，记得更新一下
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);//需要构造一个RemoteViews
        Log.i("TAG",context.getPackageName());
        Intent intent = new Intent();
        intent.setClass(context, TestWidget.class); //通过intent把广播发给TestWidget本身，TestWidget接受到广播之后，会调用。。进而刷新借鉴       // 。
        intent.setAction(WIDGET_BTN_ACTION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        remoteViews.setOnClickPendingIntent(R.id.widget_btn, pendingIntent);//控件btn_widget的点击事件：点击按钮时，会发一个带action的广播。

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews); //点击完了之后，记得更新一下。

    }

    public void perforUpdate (  ){

        intent = new Intent();
        intent.setAction("widget");
        intent.putExtra("ss",1);
        context.sendBroadcast(intent);

    }
}