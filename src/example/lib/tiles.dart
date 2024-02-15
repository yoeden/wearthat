import 'package:wear/tiles.dart';
import 'package:wear_example/tiles/progress.dart';
import 'package:wear_example/tiles/reminder.dart';

class MyTiles extends AppTiles {
  const MyTiles();

  @override
  List<TileService> getTiles() => [
        TileService(
          resources: [
            "assets/item.png",
            "assets/check.png",
            "assets/face.png",
            "assets/double_check.png",
            "assets/spongebob-dance.gif",
          ],
          routes: {
            '/': () => ProgressTile(),
            '/reminder': () => ReminderTile(),
          },
        ),
      ];
}
