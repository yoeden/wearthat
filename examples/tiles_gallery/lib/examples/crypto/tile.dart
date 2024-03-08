import 'package:tiles_gallery/examples/crypto/provider.dart';
import 'package:flutterwear/tiles.dart';
import 'package:flutter/material.dart' as m;

class CryptoTile extends Tile<CryptoInfo> {
  @override
  TileFreshness get freshness => TileFreshness.interval(Duration(seconds: 2));

  @override
  Future<CryptoInfo?> createState() {
    return BinanceProvider().getInfo("BTCUSDT");
  }

  @override
  TileWidget build(context, state) {
    return PrimaryLayout(
      title: SizedBox(
        height: 24,
        width: 24,
        child: Image("btc"),
      ),
      body: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text("Bitcoin"),
          Text(
            "\$${state!.price.toStringAsFixed(2)}",
            style: TextStyle.display3(),
          ),
          SizedBox(height: 8),
          DecoratedBox(
            decoration: BoxDecoration(
              padding: const EdgeInsets.all(4.0),
              color: state.change >= 0 ? m.Colors.green.shade500 : m.Colors.red.shade500,
              borderRadius: 4,
            ),
            child: Text(
              state.change >= 0 ? "▲ ${state.change.toStringAsFixed(2)} %" : "▼ ${state.change.toStringAsFixed(2)} %",
              style: TextStyle.caption1(),
            ),
          ),
        ],
      ),
    );
  }

  @override
  Map<String, TileResourceProvider> resources(TileContext context, CryptoInfo? state) => {
        //TODO: Beautify all examples
        'btc': TileResources.asset("assets/crypto/bitcoin.png"),
      };
}
