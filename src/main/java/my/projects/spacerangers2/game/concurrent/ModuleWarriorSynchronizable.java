package my.projects.spacerangers2.game.concurrent;

public interface ModuleWarriorSynchronizable {
	boolean enemyDyingIsLast();
	void userShipDying();
	void sendFightIsFinished();
}
