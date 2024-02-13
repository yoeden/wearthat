import 'package:wear/tiles/widgets.dart';

class Scaffold extends TileWidgetBuilder {
  final TileWidget? top;
  final TileWidget body;
  final TileWidget? bottom;
  final EdgeInsets? margin;
  final ArcLayoutMixin? arcLayout;
  final ClickableAction? action;

  Scaffold({
    required this.body,
    this.top,
    this.bottom,
    this.arcLayout,
    this.action,
    this.margin,
  }) : assert(margin == null || ((margin.top != 0 || margin.bottom != 0)), "Vertical margin isn't accepted in Scaffold.");

  @override
  TileWidget build() {
    return _possibleAction(
      _possibleArc(
        _possibleMargin(
          _child(),
        ),
      ),
    );
  }

  TileWidget _possibleAction(TileWidget child) {
    if (action != null) return Clickable(action: action!, child: child);
    return child;
  }

  TileWidget _possibleArc(TileWidget child) {
    if (arcLayout != null) {
      return Stack(children: [
        arcLayout!,
        child,
      ]);
    }
    return child;
  }

  TileWidget _possibleMargin(TileWidget child) {
    if (margin != null) return Padding(padding: margin!, child: child);
    return child;
  }

  TileWidget _child() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        FractionallySizedBox(heightFactor: 0.021),
        if (top != null) top!,
        body,
        if (bottom != null) ..._bottomLayout(),
      ],
    );
  }

  _bottomLayout() {
    return [
      bottom!,
      FractionallySizedBox(heightFactor: 0.068),
    ];
  }
}
