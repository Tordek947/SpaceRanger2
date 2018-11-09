package my.projects.spacerangers2.game.concurrent;

public interface WarriorSynchronizable extends EntitySynchronizable {
	boolean enemyDyingIsLast();
	void userShipDying();
	void sendFightIsFinished();
}
