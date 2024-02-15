package yoeden.flutter.wear.communication;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

import yoeden.flutter.wear.communication.dtos.WearMessage;
import yoeden.flutter.wear.communication.dtos.WearNode;


public class WearCommunicationHelper {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Task<List<Integer>> send(WearMessage message, Context context) {
        try {
            List<WearNode> nodes = Tasks.await(getNodes(context));
            if (nodes.stream().noneMatch(node -> node.getId().equals(message.getNode()))) throw new RuntimeException("Invalid node id");

            return sendToIdWithoutValidation(message, message.getNode(), context)
                    .continueWith(task -> {
                        ArrayList<Integer> list =  new ArrayList<Integer>(1);
                        list.add(task.getResult());
                        return list;
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Task<List<Integer>> sendAll(WearMessage message, Context context) {
        try {
            List<WearNode> nodes = Tasks.await(getNodes(context));
            List<Integer> ids = new ArrayList<>(nodes.size());

            for (WearNode node : nodes) {
                String nodeId = node.getId();

                Integer messageId = Tasks.await(sendToIdWithoutValidation(message, nodeId, context));

                ids.add(messageId);
            }

            return Tasks.forResult(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Task<Integer> sendToIdWithoutValidation(WearMessage message, String nodeId, Context context) {
        return Wearable
                .getMessageClient(context)
                .sendMessage(nodeId, message.getPath(), message.getData());
    }

    public static Task<List<WearNode>> getNodes(Context context) {
        return Wearable
                .getNodeClient(context.getApplicationContext())
                .getConnectedNodes()
                .continueWith(task -> {
                    List<Node> nodes = task.getResult();
                    List<WearNode> wearNodes = new ArrayList<>(nodes.size());
                    for (Node node : nodes) {
                        wearNodes.add(new WearNode(node.getDisplayName(), node.getId(), node.isNearby()));
                    }
                    return wearNodes;
                });
    }
}