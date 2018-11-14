package my.projects.spacerangers2.game.scene;

import java.util.concurrent.Semaphore;
import my.projects.spacerangers2.game.entities.SpaceEntityCreator;
import my.projects.spacerangers2.game.geometry.Vector2D;

public abstract class GameModule {
	private Semaphore buildSynchronizer;
	private volatile BuildState buildState;
	protected SpaceEntityCreator entityCreator;
	protected Vector2D sceneSize;
	
	public GameModule(Vector2D sceneSize, SpaceEntityCreator entityCreator) {
		buildSynchronizer = new Semaphore(0);
		buildState = BuildState.NOT_STARTED;
		this.entityCreator = entityCreator;
		this.sceneSize = sceneSize;
	}
	
	public final void buildInBackground() {
		buildState = BuildState.IN_PROCESS;
		Thread t = new Thread(() -> buildModule());
		t.setDaemon(true);
		t.start();
	}
	
	public final void buildModule() {
		build();
		buildState = BuildState.ACCOMPLISHED;
		buildSynchronizer.release();
	}
	
	protected abstract void build();
	
	public final void executeModule() {
		switch(buildState) {

		case NOT_STARTED:
			build();
			execute();
			break;
		case IN_PROCESS:
		case ACCOMPLISHED:
			try {
				buildSynchronizer.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			execute();
			buildState = BuildState.NOT_STARTED;
			break;
		default:
			break;
		}
	}
	
	protected abstract void execute();

	private enum BuildState {
		IN_PROCESS, ACCOMPLISHED, NOT_STARTED;
	}

}
