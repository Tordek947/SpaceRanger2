package my.projects.resources;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class MusicsBuilder {
	private static MusicsBuilder instance;
	private Map<StreamName, InputStream> resourceMap;
	
	public static MusicsBuilder getInstance() {
		if (instance == null) {
			instance = new MusicsBuilder();
		}
		return instance;
	}
	
	private MusicsBuilder() {
		resourceMap = new TreeMap<>();
	}
	
	public InputStream get(StreamName name) {
		if (!resourceMap.containsKey(name)) {
			InputStream is = loadInputStream(name.path);
			resourceMap.put(name, is);
			return is;
		}
		return resourceMap.get(name);
	}
	
	private InputStream loadInputStream(String path) {
		InputStream is = this.getClass().getResourceAsStream(path);
		return is;
	}
	
	public enum StreamName {
		EXPLOSION("/sounds/EXPL0.WAV"), LONG_WAY_BALLADE("/sounds/LONGWAY.WAV");
		String path;
		private StreamName(String path) {
			this.path = path;
		}
	}
}
