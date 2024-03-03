// https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
// https://api.binance.com/api/v3/ticker/24hr?symbol=BTCUSDT
// {"symbol":"BTCUSDT","priceChange":"-571.56000000","priceChangePercent":"-1.106","weightedAvgPrice":"51493.92928167","prevClosePrice":"51666.00000000","lastPrice":"51094.45000000","lastQty":"0.00072000","bidPrice":"51094.45000000","bidQty":"0.17740000","askPrice":"51094.46000000","askQty":"14.49683000","openPrice":"51666.01000000","highPrice":"51958.55000000","lowPrice":"50901.44000000","volume":"22025.97271000","quoteVolume":"1134203881.08869530","openTime":1708861745914,"closeTime":1708948145914,"firstId":3433559921,"lastId":3434577987,"count":1018067}

// https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
// https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d&limit=4

import 'dart:convert';

import 'package:http/http.dart' as http;

mixin CryptoProvider {
  Future<CryptoInfo> getInfo(String symbol);

  Future<List<CryptoInfo>> getPastInfo(String symbol, int lookback);
}

class BinanceProvider implements CryptoProvider {
  @override
  Future<CryptoInfo> getInfo(String symbol) async {
    final res = await http.get(Uri.parse('https://api.binance.com/api/v3/ticker/24hr?symbol=$symbol'));
    final data = jsonDecode(res.body);

    return CryptoInfo(
      symbol: symbol,
      change: double.parse(data["priceChangePercent"]),
      price: double.parse(data["lastPrice"]),
    );
  }

  @override
  Future<List<CryptoInfo>> getPastInfo(String symbol, int lookback) async {
    final res = await http.get(Uri.parse('https://api.binance.com/api/v3/klines?symbol=$symbol&interval=1d&limit=$lookback'));
    final data = jsonDecode(res.body);
    final items = data as List;

    return items.map((e) {
      final values = e as List;
      return CryptoInfo(symbol: symbol, price: double.parse(values[4]), change: 0);
    }).toList();
  }
}

class CryptoInfo {
  final String symbol;
  final double price;
  final double change;

  CryptoInfo({
    required this.symbol,
    required this.price,
    required this.change,
  });
}
