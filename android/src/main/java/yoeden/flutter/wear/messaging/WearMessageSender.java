package yoeden.flutter.wear.messaging;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import yoeden.flutter.wear.FlutterWearTiles;


public class WearMessageSender {

    public static void post(WearMessage message, Context context) {
        new Thread(() -> send(message, context)).start();
    }

    public static void send(WearMessage message, Context context)
    {
        try {
            Task<List<Node>> nodeListTask = Wearable.getNodeClient(context.getApplicationContext()).getConnectedNodes();
            List<Node> nodes =  Tasks.await(nodeListTask);

            for (Node node : nodes) {
                String nodeId = node.getId();

                Task<Integer> sendMessageTask = Wearable
                        .getMessageClient(context)
                        .sendMessage(nodeId, message.getPath(), message.getData());

                sendMessageTask.addOnSuccessListener(runnable -> {
                    Log.d(FlutterWearTiles.Tag,"Wear message sent: " +runnable);
                });
                sendMessageTask.addOnFailureListener(exception -> {
                    Log.d(FlutterWearTiles.Tag,"Wear message failed: " + exception.getMessage());
                });

                try {
                    Tasks.await(sendMessageTask);
                } catch (Exception exception) {
                    Log.e(FlutterWearTiles.Tag, "Wear message exception: "+exception.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}