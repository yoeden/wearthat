//https://developer.android.com/reference/androidx/wear/tiles/TileBuilders.Tile.Builder#setFreshnessIntervalMillis(long)
class TileFreshness {
  final DateTime? _until;
  final Duration? _interval;

  //TODO: The whole point of validate is TimelineEntry collection on android code [buildTile]
  //const TileFreshness.validate(this._until) : _after = null;

  /// What is the duration this needs to be refreshed at
  const TileFreshness.interval(this._interval) : _until = null;

  //TODO:
  //const TileFreshness.refreshAndValidate(this._after, this._until);

  const TileFreshness.auto()
      : _interval = null,
        _until = null;

  //TODO: should send 0
  const TileFreshness.never()
      : _interval = Duration.zero,
        _until = null;

  Map<String, Object> toJson() => {
        if (_until != null) "until": _until.millisecondsSinceEpoch,
        if (_interval != null) "interval": _interval.inMilliseconds,
      };
}
