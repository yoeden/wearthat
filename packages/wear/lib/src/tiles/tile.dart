import 'package:wear/tiles.dart';

abstract class TileContext {
  //TODO: Support themes
  final TileTheme theme = const TileTheme();

  const TileContext();
}

class DemoContext extends TileContext {
  DemoContext();
}

/// Represents a single tile.
///
/// Tiles are contained inside [TileService].
///
/// [Tile]s are expected to be immutable and stateless.
/// When a [Tile] is requested, a new instance of the entire app will be created and destroyed right after.
///
/// As of writing this doc, the state of the tile is passed around by serializing it to json.
/// If the [ClickableActions.setState] is planned on being used, supply a [Tile.fromJson] function, and implement [toJson] method as well.
abstract class Tile<TState> {
  final TState Function(Map<String, dynamic>)? fromJson;

  const Tile({this.fromJson});

  Future<TState?> createState() => Future.value(null);

  /// The freshness of the tile.
  /// See [TileFreshness] for more information.
  TileFreshness get freshness => const TileFreshness.never();

  /// Builds the tile.
  TileWidget build(TileContext context, TState? state);

  /// Provides resources for the tile.
  ///
  /// Each resource should have a unique identifier and be defined before layout can be requested.
  Map<String, TileResourceProvider> resources(TileContext context, TState? state) => {};
}
