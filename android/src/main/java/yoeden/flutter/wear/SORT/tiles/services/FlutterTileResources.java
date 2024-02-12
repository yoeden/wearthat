package yoeden.flutter.wear.SORT.tiles.services;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.wear.tiles.RequestBuilders;
import androidx.wear.tiles.ResourceBuilders;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.FileInputStream;
import java.util.List;

import yoeden.flutter.wear.FlutterTilesChannel;
import yoeden.flutter.wear.FlutterWearTiles;
import io.flutter.FlutterInjector;
import yoeden.flutter.wear.SORT.tiles.channel.Tiles;
import yoeden.flutter.wear.SORT.tiles.channel.TilesChannel;

public class FlutterTileResources {
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static ListenableFuture<ResourceBuilders.Resources> onResourcesRequest(
            @NonNull Context context,
            @NonNull TilesChannel channel,
            @NonNull RequestBuilders.ResourcesRequest requestParams) {

        try{
            List<String> assets = Tiles.getChannel().requestResources("main");

            ResourceBuilders.Resources.Builder builder = new ResourceBuilders.Resources.Builder();
            builder.setVersion(requestParams.getVersion());
            loadDefaultResources(builder, context, requestParams);
            loadResourcesFromFlutter(builder, assets, context);

            return Futures.immediateFuture(builder.build());
        }catch (Exception e)
        {
            return Futures.immediateFailedFuture(e);
        }
//        ListenableFuture<List<String>> future = JdkFutureAdapters.listenInPoolThread(
//                FlutterTilesChannel.requestResources("main")
//        );
//
//        ListenableFuture<ResourceBuilders.Resources> result = Futures.transform(
//                future,
//                assets -> {
//                    ResourceBuilders.Resources.Builder builder = new ResourceBuilders.Resources.Builder();
//                    builder.setVersion(requestParams.getVersion());
//                    loadDefaults(builder, context, requestParams);
//                    loadAssetsFromFlutter(builder, assets, context);
//                    return builder.build();
//                },
//                context.getMainExecutor()
//        );
//        return result;
    }

    private static void loadDefaultResources(ResourceBuilders.Resources.Builder builder, Context context, RequestBuilders.ResourcesRequest requestParams) {

//        builder
//                .addIdToImageMapping("__tile_error_image", new ResourceBuilders.ImageResource.Builder()
//                        .setAndroidResourceByResId(
//                                new ResourceBuilders.AndroidImageResourceByResId.Builder()
//                                        .setResourceId(R.drawable.error)
//                                        .build()
//                        ).build()
//                )
//                .addIdToImageMapping("__app_icon", new ResourceBuilders.ImageResource.Builder()
//                        .setAndroidResourceByResId(
//                                new ResourceBuilders.AndroidImageResourceByResId.Builder()
//                                        .setResourceId(context.getResources().getIdentifier("ic_launcher", "mipmap", context.getPackageName()))
//                                        .build()
//                        ).build()
//                );
    }

    private static void loadResourcesFromFlutter(ResourceBuilders.Resources.Builder builder, List<String> resources, Context context) {
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
                FlutterTilesChannel.throwError(e.getMessage());
            }
        }
    }
}
