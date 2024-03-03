import 'dart:async';
import 'dart:convert';
import 'dart:typed_data';
import 'dart:ui' as ui;
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

import 'package:fl_chart/fl_chart.dart';
import 'package:tiles_gallery/examples/crypto/provider.dart';

class MyWidget extends StatefulWidget {
  const MyWidget({super.key});

  @override
  State<MyWidget> createState() => _MyWidgetState();
}

class _MyWidgetState extends State<MyWidget> {
  final GlobalKey _globalKey = GlobalKey();

  Uint8List? bytes;

  Future<Uint8List> _capturePng() async {
    print('inside');
    RenderRepaintBoundary boundary = _globalKey.currentContext!.findRenderObject() as RenderRepaintBoundary;
    ui.Image image = await boundary.toImage(pixelRatio: 1.0);

    ByteData? byteData = await image.toByteData(format: ui.ImageByteFormat.png);
    var pngBytes = byteData!.buffer.asUint8List();
    var bs64 = base64Encode(pngBytes);
    log(bs64);
    return pngBytes;
  }

  @override
  Widget build(BuildContext context) {
    if (bytes != null) {
      return Stack(
        children: [
          Text("THIS IS IMAGE"),
          Image.memory(bytes!),
        ],
      );
    }

    List<Color> gradientColors = [
      Colors.green.shade800,
      Colors.green,
    ];

    List<Color> gradientColorsBelow = [
      Colors.green.shade800,
      Colors.green,
      Colors.transparent,
    ];

    return FutureBuilder<List<CryptoInfo>>(
      future: BinanceProvider().getPastInfo("BTCUSDT", 5),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        }

        final data = snapshot.data;

        WidgetsBinding.instance.addPostFrameCallback((timeStamp) async {
          bytes = await _capturePng();
          setState(() {});
        });
        return RepaintBoundary(
          key: _globalKey,
          child: SizedBox(
            height: 56,
            width: 56,
            child: LineChart(
              LineChartData(
                lineBarsData: [
                  LineChartBarData(
                    spots: data!.asMap().map((key, value) => MapEntry(key, FlSpot(key.toDouble(), value.price))).values.toList(),
                    gradient: LinearGradient(
                      colors: gradientColors,
                    ),
                    barWidth: 3,
                    isStrokeCapRound: true,
                    dotData: const FlDotData(
                      show: false,
                    ),
                    belowBarData: BarAreaData(
                      show: true,
                      applyCutOffY: true,
                      gradient: LinearGradient(
                        colors: gradientColorsBelow.map((color) => color.withOpacity(0.3)).toList(),
                      ),
                    ),
                  ),
                ],
                backgroundColor: Colors.transparent,
                borderData: FlBorderData(show: false),
                lineTouchData: LineTouchData(enabled: false),
                gridData: FlGridData(show: false),
                betweenBarsData: List.empty(),
                titlesData: FlTitlesData(show: false),
              ),
              duration: Duration(milliseconds: 150), // Optional
              curve: Curves.linear, // Optional
            ),
          ),
        );
      },
    );
  }
}
