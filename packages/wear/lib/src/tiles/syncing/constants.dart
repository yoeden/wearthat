const String kAndroidManifestLocation = 'android/app/src/main/AndroidManifest.xml';
const String kAndroidDrawablesFolder = "android/app/src/main/res/drawable/";

const String kAndroidTilesFolder = "android/app/src/main/java/$kAndroidTilesPackage/";
const String kAndroidTilesPackage = "tiles";

const String kAndroidTilePreviewPostfix = ".png";
const String kAndroidTilePreviewPrefix = "tilepreview_";

const String kAndroidJavaTileClassTemplate = '''
package $kAndroidTilesPackage;

import yoeden.flutter.wear.tiles.services.FlutterTileService;

public class :name: extends FlutterTileService {
    public :name:() {
        super(":name:");
    }
}

''';
