package yoeden.flutter.wear.utils;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.concurrent.Executors;

import io.flutter.plugin.common.MethodChannel;

public class TaskHelper {

    public static <TResult> void run(Func<Task<TResult>> func, MethodChannel.Result result) {
        try {
            Executors.defaultThreadFactory().newThread(() -> {
                Task<TResult> task = func.run();
                task
                        .addOnSuccessListener(result::success)
                        .addOnFailureListener(e -> result.error("", e.getMessage(), null));
            }).start();
        } catch (Exception e) {
            result.error("", e.getMessage(), null);
        }
    }

    public static <TResult> void runResultAsJson(Func<Task<TResult>> func, MethodChannel.Result result) {
        try {
            Executors.defaultThreadFactory().newThread(() -> {
                Task<TResult> task = func.run();
                task
                        .addOnSuccessListener(r -> result.success(new Gson().toJson(r)))
                        .addOnFailureListener(e -> result.error("", e.getMessage(), null));
            }).start();
        } catch (Exception e) {
            result.error("", e.getMessage(), null);
        }
    }
}