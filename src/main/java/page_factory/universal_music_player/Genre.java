package page_factory.universal_music_player;

public enum Genre {
    ROCK("Rock"),
    CINEMATIC("Cinematic"),
    JAZZ_AND_BLUES("Jazz & Blues");

    private final String value;

    Genre(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
