# MiniProjects


ConnectFour Dokumentation

+++++++++ TO PLACE A DISC - CLICK ON THE COLUMN ++++++++++

Minimum Requirements all working, added adjustable disc board and Mode connectFive, the time gets measured while the game itself is runnning and displayed at the end,

Model:

public Moves[][] setBoardSize(String size) => Benutzer kann Grösse von Spielfeld bestimmen, String aus ChoiceBox wird wird nach x aufgeteilt und zu Int umgewandelt

public void makeMove(int col) => setzt Enum Move in 2d-Array an richtige Stelle, wechselt zu nächstem Move (Red/Yellow) und prüft nach Zug ob jemand gewonnen hat

public Moves[][] resetDiscBoard() => setzt Elemente von Model auf null = neustart

public boolean fullPlayingBoard() => prüft ob Spielbrett voll ist, gibt true zurück wenn voll, gebraucht für Untentschieden 

private void checkWinner(String mode) => Die 4 Varianten für Gewinner werden getestet und setzt winner, Parameter mode wird für weitere Methode weitergegeben

private Moves checkHorWinner(String mode) => nimmt mode durch Parameter und iteriert durch Array Moves (Durch cols), gibt den Move zurück der gewonnen hat

private Moves checkVertWinner(String mode) => iteriert durch Array (Rows), ausgehend von Position currentMove und prüft ob 4 oder 5 Moves (abhängig von Mode) gleich sind, gibt Moves zurück falls gleich

private Moves checkDiagWinnerDown(String mode) => vergleicht Positionen von Array ausgehend von aktueller Position  von oben links nach unten rechts und sucht nach 4 oder 5 gleichen und gibt Gewinner zurück falls vorhanden

private Moves checkDiagWinnerUp (String mode) => vergleicht Positionen von Array ausgehend von aktueller Position von unten links nach oben rechts und sucht nach 4 oder 5 gleichen und gibt Gewinner zurück falls vorhanden

public void showRule() => Alert für Regeln Anzeige im inGame Menu - eine Methode für Deutsch, eine für Englisch

public void showHelp() => Alert für Hilfe Anzeige im inGame Menu - eine Methode für Deutsch, eine für Englisch

public void countGameTime() => speichert die Zeit zu Call, gebraucht für Zeitmessung von Spiel

public void readGameTime() => nimmt Zeit zu Aufruf und erstellt Duration Objekt um Spielzeit zu messen

public double getGameTime() => gibt die gemessene Spielzeit zurück

View:

Konstruktor => erstellt inGameScene und startScene und setzt startScene in Stage

public void changeScene(Scene sceneToGo)  => nimmt scene aus Parameter und setzt sie in stage

public void updateScene() => aktualisiert scenes indem sie neu erstellt werden

private Scene createStartScene() => erstellt 1. Szene

public void resetStartScene() => setzt startScene auf null zurück

private Scene createGameScene() => erstellt Spielszene

private Shape makeGrid() => Create the playing board - the circles are not objects so we need an overlay to choose the column to place the disc

private List<Rectangle> makeOverlay() => create an overlay to highlight the desired column
  
public Scene createGameOverScene() => creates the last scene

public void placeDisc() => gets the last move from model thanks to currentCol and currentRow, , creates a disc and sets disc at this position in discPane

public void prepareBoard() => prepares the board for the mode ConnectFive - last 2 rows needs to be filled alternately

private void placeDiscNoAnimation() => needed to prepare Board - same as placeDisc() but no animation

private Class Disc extends Circle => creates a circle that is red if boolean = true or yellow if boolean = false

Controller: 

handles the action of the scenes and updates the GUI
