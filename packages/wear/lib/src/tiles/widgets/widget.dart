/// The base class for all tile widgets
/// TileWidgets can be nested and be build by the [build] method.
///
/// Eventually every tile widget must be serialized and translated to protonLayout on the android side.
abstract class TileWidget {
  const TileWidget();

  /// The build method used to build the tile widget.
  TileWidget build();

  /// Serializes the tile widget to translatable tile widget to be handled by android.
  Map<String, Object> serialize() => build().serialize();
}
