import 'dart:io';

import 'package:wearthat/src/logger.dart';
import 'package:wearthat/src/tiles/syncing/info.dart';
import 'package:xml/xml.dart';
import 'package:wearthat/src/tiles/syncing/constants.dart';
import 'package:wearthat/src/tiles/syncing/utils.dart';

Future<XmlDocument> load(Logger log) async {
  File manifestFile = File(kAndroidManifestLocation);

  if (!manifestFile.existsSync()) {
    log.e("Android manifest wasn't located at '$kAndroidManifestLocation'");
    skipTileSyncing(log);
  }
  log.d("[*] Located android manifest at '$kAndroidManifestLocation'");

  late XmlDocument document;
  try {
    document = XmlDocument.parse(await manifestFile.readAsString());
  } catch (e) {
    log.e("Failed to parse android manifest.");
    skipTileSyncing(log);
  }

  return document;
}

void clear(Logger log, XmlDocument document) {
  List<XmlElement> services = document.findAllElements('service').toList();

  for (var service in services) {
    var permissionAttribute = service.getAttribute('android:permission');
    if (permissionAttribute ==
        'com.google.android.wearable.permission.BIND_TILE_PROVIDER') {
      service.remove();

      log.d("[-] Removed ${service.getAttribute('android:name')}");
    }
  }

  log.d("[-] Removed ${services.length} tiles.");
}

void add(Logger logger, XmlDocument document, TileInformation tile) {
  var serviceParent = document.findAllElements('application').first;
  final preview = XmlElement(
    XmlName("meta-data"),
    [
      XmlAttribute(XmlName("android:name"), "androidx.wear.tiles.PREVIEW"),
      XmlAttribute(XmlName("android:resource"),
          "@drawable/${createTilePreviewResNameForManifest(tile)}"),
    ],
    [],
  );
  final intentFilter = XmlElement(XmlName("intent-filter"), [], [
    XmlElement(XmlName("action"), [
      XmlAttribute(XmlName("android:name"),
          "androidx.wear.tiles.action.BIND_TILE_PROVIDER"),
    ]),
  ]);

  final node = XmlElement(
    XmlName("service"),
    [
      XmlAttribute(
          XmlName("android:name"), "$kAndroidTilesPackage.${tile.name}"),
      XmlAttribute(XmlName("android:exported"), "true"),
      XmlAttribute(XmlName("android:label"), tile.label),
      XmlAttribute(XmlName("android:permission"),
          "com.google.android.wearable.permission.BIND_TILE_PROVIDER"),
    ],
    [
      intentFilter,
      if (tile.preview != null) preview,
    ],
  );
  serviceParent.children.add(node);
  logger.d("[+] Added tile ${tile.name} to the manifest.");
}

Future<void> save(Logger log, XmlDocument document) async {
  await File(kAndroidManifestLocation).writeAsString(document.toXmlString(
    pretty: true,
    indentAttribute: (value) =>
        value.hasParent && value.parent!.attributes.length > 1,
  ));
}
