package yoeden.flutter.wear.tiles.services;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.wear.protolayout.ResourceBuilders;
import androidx.wear.tiles.RequestBuilders;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.FileInputStream;
import java.util.List;

import yoeden.flutter.wear.FlutterWearTiles;
import io.flutter.FlutterInjector;
import yoeden.flutter.wear.R;
import yoeden.flutter.wear.tiles.channels.TilesLayoutChannel;

public class FlutterTileResources {
    public static ListenableFuture<ResourceBuilders.Resources> onResourcesRequest(
            @NonNull Context context,
            @NonNull TilesLayoutChannel channel,
            @NonNull RequestBuilders.ResourcesRequest requestParams,
            @NonNull String tile) {
        ListenableFuture<List<String>> future = JdkFutureAdapters.listenInPoolThread(
                channel.requestResources(tile)
        );

        ListenableFuture<ResourceBuilders.Resources> result = Futures.transform(
                future,
                assets -> {
                    Log.i(FlutterWearTiles.Tag, "onResourcesRequest: " + assets.size());
                    ResourceBuilders.Resources.Builder builder = new ResourceBuilders.Resources.Builder();
                    apply(builder, context, requestParams);
                    loadAssets(builder, assets, context);
                    return builder.build();
                },
                context.getMainExecutor()
        );

        return result;
    }

    private static void apply(ResourceBuilders.Resources.Builder builder, Context context, RequestBuilders.ResourcesRequest requestParams) {
        builder
                .setVersion(FlutterWearTiles.ResourceVersion)
                .addIdToImageMapping("__tile_error_image", new ResourceBuilders.ImageResource.Builder()
                        .setAndroidResourceByResId(
                                new ResourceBuilders.AndroidImageResourceByResId.Builder()
                                        .setResourceId(R.drawable.error)
                                        .build()
                        ).build()
                )
                .addIdToImageMapping("__app_icon", new ResourceBuilders.ImageResource.Builder()
                        .setAndroidResourceByResId(
                                new ResourceBuilders.AndroidImageResourceByResId.Builder()
                                        .setResourceId(context.getResources().getIdentifier("ic_launcher", "mipmap", context.getPackageName()))
                                        .build()
                        ).build()
                );
    }

    private static void loadAssets(ResourceBuilders.Resources.Builder builder, List<String> resources, Context context) {
        AssetManager assetManager = context.getAssets();
        FlutterInjector injector = FlutterInjector.instance();

        for (String resource : resources) {
            String key = injector.flutterLoader().getLookupKeyForAsset(resource);

            try {
                AssetFileDescriptor fd = assetManager.openFd(key);
                int length = (int) fd.getLength();

                //Do not optimize this to a fixed size (2MB for example), the resource manager will eventually cry that the resources are too large
                //because it appends the entire array.
                byte[] data = new byte[(int) fd.getLength()];

                FileInputStream in = new FileInputStream(fd.getFileDescriptor());
                in.skip(fd.getStartOffset());
                in.read(data, 0, length);
                in.close();

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, length);
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                bmp.recycle();

                builder
                        .addIdToImageMapping(resource, new ResourceBuilders.ImageResource.Builder()
                                .setInlineResource(new ResourceBuilders.InlineImageResource.Builder()
                                        .setData(data)
                                        .setFormat(ResourceBuilders.IMAGE_FORMAT_UNDEFINED)
                                        .setHeightPx(height)
                                        .setWidthPx(width)
                                        .build()
                                )
                                .build()
                        );

            } catch (Exception e) {
                Log.e(FlutterWearTiles.Tag,"Failed to fetch resource: "+key,e);
            }
        }
    }
}

