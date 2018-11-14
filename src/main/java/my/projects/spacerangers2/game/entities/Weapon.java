package my.projects.spacerangers2.game.entities;

import my.projects.spacerangers2.game.common.SingleImageAnimation;
import my.projects.spacerangers2.game.objects.SpaceObject;

public class Weapon extends SpaceObject<SingleImageAnimation> {

	private int shootPeriodInTicks;
	private BulletSample bulletSample;
	private int currentFlip;
	//private boolean isUpDirection; -- для того, чтобы знать, куда прилепить снаряд (в дальнейшем, когда будут инопланетяне)
	
	public Weapon(SingleImageAnimation animation) {
		super(animation);
		shootPeriodInTicks = -1;
	}
	
	public void setShootingParameters(BulletSample bulletSample, int shootPeriodInTicks) {
		if (shootPeriodInTicks < 1) {
			return;
		}
		this.bulletSample = bulletSample;
		this.shootPeriodInTicks = shootPeriodInTicks;
	}
	
	public void tickAndMaybeShoot() {
		if (shootPeriodInTicks == -1) {
			return;
		}
		currentFlip++;
		if (currentFlip == shootPeriodInTicks) {
			currentFlip = 0;
			shoot();
		}
	}

	private void shoot() {
		Bullet newBullet = BulletSample.makeBulletFrom(bulletSample);
		newBullet.object.setXCenter(getLeftRightCenter());
		newBullet.object.setBottom(getTop());
		Thread t = new Thread(newBullet);
		t.setDaemon(true);
		t.start();
	}
}
