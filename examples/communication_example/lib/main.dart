import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:wearthat/communication.dart';
import 'package:wearthat/wear.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({
    super.key,
  });

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const String kPhoneRoute = "to.phone";
  static const String kWearRoute = "to.watch";

  static const String kPhoneMessage = "From 🤳";
  static const String kWearMessage = "From ⌚";

  String _latestMessageSpecifiedRoute = "...";
  String _latestMessageGenericRoute = "...";

  String _sendToRoute = "";
  String _listenOnRoute = "";
  String _message = "";

  @override
  void initState() {
    super.initState();

    FlutterView view = WidgetsBinding.instance.platformDispatcher.views.first;
    Size size = view.physicalSize;
    double width = size.width;

    if (width > 400) {
      // Phone
      _sendToRoute = kWearRoute;
      _listenOnRoute = kPhoneRoute;
      _message = kPhoneMessage;

      Wear.listen(_listenOnRoute, _incomingMessage);
      Wear.listen(".", _incomingGenericMessage);
    } else {
      // Watch
      _sendToRoute = kPhoneRoute;
      _listenOnRoute = kWearRoute;
      _message = kWearMessage;

      Wear.listen(_listenOnRoute, _incomingMessage);
      Wear.listen(".", _incomingGenericMessage);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Row(
              children: [
                Expanded(
                  child: Column(
                    children: [
                      Text(_latestMessageSpecifiedRoute),
                      Text("$_listenOnRoute"),
                    ],
                  ),
                ),
                Expanded(
                  child: Column(
                    children: [
                      Text(_latestMessageGenericRoute),
                      Text("Generic"),
                    ],
                  ),
                ),
              ],
            ),
            TextButton(
              onPressed: () {
                Wear.send(WearMessage.string(_sendToRoute, _message));
              },
              child: Text("Send"),
            ),
          ],
        ),
      ),
    );
  }

  Future _incomingGenericMessage(WearMessage message) {
    _latestMessageGenericRoute = message.dataAsString();
    setState(() {});

    return Future.value();
  }

  Future _incomingMessage(WearMessage message) {
    _latestMessageSpecifiedRoute = message.dataAsString();
    setState(() {});

    return Future.value();
  }
}
