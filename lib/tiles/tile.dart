import 'package:wear/widgets.dart';

abstract class Tile<TState> {
  const Tile();

  Future<TState?> createState() => Future.value(null);

  TileFreshness get freshness => const TileFreshness.never();

  TileWidget build(TState? state);
}

class TileFreshness {
  final DateTime? _until;
  final Duration? _interval;

  //TODO: The while point of validate is TimelineEntry collection on android code [buildTile]
  //const TileFreshness.validate(this._until) : _after = null;

  /// What is the duration this needs to be refreshed at
  const TileFreshness.interval(this._interval) : _until = null;

  //TODO:
  //const TileFreshness.refreshAndValidate(this._after, this._until);

  const TileFreshness.never()
      : _interval = null,
        _until = null;

  Map<String, Object> toJson() => {
        if (_until != null) "until": _until!.millisecondsSinceEpoch,
        if (_interval != null) "interval": _interval!.inMilliseconds,
      };
}
