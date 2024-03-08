import 'package:flutter/services.dart';
import 'package:flutter/painting.dart';
import 'package:flutterwear/tiles.dart';
import 'dart:ui' as ui;
import 'package:flutter/material.dart' as m;

class MyTile extends Tile {
  @override
  TileWidget build(TileContext context, state) {
    return Text("Hello !!!!");
  }
}

class DemoState {
  final bool isOn;
  final String name;

  DemoState({
    required this.isOn,
    required this.name,
  });

  factory DemoState.fromJson(Map<String, dynamic> json) {
    return DemoState(
      name: json["name"],
      isOn: json["isOn"],
    );
  }

  Map<String, dynamic> toJson() => {
        "name": name,
        "isOn": isOn,
      };
}

class OnOffTile extends Tile<DemoState> {
  OnOffTile() : super(fromJson: DemoState.fromJson);

  @override
  Future<DemoState?> createState() => Future.value(DemoState(isOn: false, name: "Living Room"));

  static final color = m.Colors.blueAccent.shade100;
  final bgColor = color.withOpacity(0.6);

  @override
  TileWidget build(TileContext context, state) {
    return Stack(
      children: [
        if (state!.isOn)
          SizedBox(
            height: 150,
            width: 150,
            child: Image("bg"),
          ),
        Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Button(
              child: SizedBox(
                height: 24,
                width: 24,
                child: Image("power"),
              ),
              style: ButtonStyle(
                backgroundColor: color,
              ),
              action: ClickableActions.setState(() {
                return DemoState(isOn: !state.isOn, name: state.name);
              }),
            ),
            Text(state.name),
          ],
        )
      ],
    );
  }

  @override
  Map<String, TileResourceProvider> resources(context, state) {
    return {
      "power": TileResources.asset("assets/smarthome/power.png"),
      "bg": TileResources.image(_TintAssetImage.resolve("assets/smarthome/light-bg.png", bgColor, BlendMode.srcIn)),
    };
  }
}

class _TintAssetImage {
  static Future<ui.Image> resolve(String asset, ui.Color color, ui.BlendMode blendMode) async {
    final data = await rootBundle.load(asset);
    Uint8List bytes = data.buffer.asUint8List();
    final image = await decodeImageFromList(bytes);

    final result = await tintImage(image, color, blendMode);

    return result;
  }

  static Future<ui.Image> tintImage(ui.Image image, ui.Color color, ui.BlendMode blendMode) {
    final paint = ui.Paint()..colorFilter = ui.ColorFilter.mode(color, blendMode);
    final size = ui.Size(image.width.toDouble(), image.height.toDouble());
    final rect = ui.Offset.zero & size;
    final recorder = ui.PictureRecorder();
    final canvas = ui.Canvas(recorder, rect);
    canvas.drawImage(image, ui.Offset.zero, paint);
    final picture = recorder.endRecording();
    final recorder2 = ui.PictureRecorder();
    final canvas2 = ui.Canvas(recorder2, rect);
    canvas2.drawPicture(picture);
    return recorder2.endRecording().toImage(image.width, image.height);
  }
}
