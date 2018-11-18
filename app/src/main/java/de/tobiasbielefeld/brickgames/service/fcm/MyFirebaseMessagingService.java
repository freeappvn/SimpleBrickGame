/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tobiasbielefeld.brickgames.service.fcm;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import de.tobiasbielefeld.brickgames.constant.NotificationConst;
import de.tobiasbielefeld.brickgames.ui.Main;
import de.tobiasbielefeld.brickgames.util.GsonUtils;
import de.tobiasbielefeld.brickgames.util.NotificationUtil;
import de.tobiasbielefeld.brickgames.util.StringUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "From: " + GsonUtils.toString(remoteMessage.getData()));
        if (remoteMessage.getFrom().equals(NotificationConst.SLASH + NotificationConst.TOPICS + NotificationConst.SLASH + NotificationConst.TOPIC_NAME)) {
            try {
                Map<String, String> mapDatas = remoteMessage.getData();
                String message = remoteMessage.getData().get("message");
                String title = remoteMessage.getData().get("title");
                String typeStr = remoteMessage.getData().get("type");
                int type = 0;
                if (!StringUtils.isNullOrWhiteSpace(typeStr)) {
                    type = Integer.parseInt(typeStr);
                }
                Log.d(TAG, "onMessageReceived message: " + message);
                Log.d(TAG, "onMessageReceived title: " + title);
                Log.d(TAG, "onMessageReceived type: " + type);

                Intent intent = new Intent(this, Main.class);
                switch (type) {
                    case NotificationConst.NOTI_ID_NEW_APP:
                        String pkg = remoteMessage.getData().get("pkg");
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + pkg));
                        break;
                    case NotificationConst.NOTI_ID_HIGH_SCORE:
                        break;
                    case NotificationConst.NOTI_ID_REWARD:
                        break;
                    case NotificationConst.NOTI_ID_QUESTION:
                        break;
                }

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                NotificationUtil.createNotification(title, message, this, pendingIntent, type);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}