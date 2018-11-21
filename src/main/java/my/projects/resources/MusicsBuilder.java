package my.projects.resources;

import java.io.InputStream;

public class MusicsBuilder {
	private static MusicsBuilder instance;
	
	public static MusicsBuilder getInstance() {
		if (instance == null) {
			instance = new MusicsBuilder();
		}
		return instance;
	}
	
	private MusicsBuilder() {
	}
	
	public InputStream get(StreamName name) {
		return loadInputStream(name.path);
	}
	
	private InputStream loadInputStream(String path) {
		InputStream is = this.getClass().getResourceAsStream(path);
		return is;
	}
	
	public enum StreamName {
		EXPLOSION("/sounds/EXPL0.WAV"),
		LONG_WAY_BALLADE("/sounds/LONGWAY.wav"),
		VICTORY("/sounds/GALAXY_CONQUER.WAV"),
		DEFEAT("/sounds/DISCIPLES_DEFEAT.WAV");
		String path;
		private StreamName(String path) {
			this.path = path;
		}
	}
}
