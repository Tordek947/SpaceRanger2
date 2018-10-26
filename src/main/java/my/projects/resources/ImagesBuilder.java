package my.projects.resources;


import javafx.scene.image.Image;

public class ImagesBuilder {
	private Image[] asteroidImages;
	private Image[] explosionImages;
	private Image[] userShipImages;
	private Image weaponImage;
	private Image bulletImage;
	private static ImagesBuilder instance;
	
	public static ImagesBuilder getInstance() {
		if(instance == null) {
			instance = new ImagesBuilder();
		}
		return instance;
	}
	
	public Image[] getAsteroidImages() {
		if (asteroidImages == null) {
			loadAsteroidImages();
		}
		return asteroidImages;
	}

	private void loadAsteroidImages() {
		String path = "/images/Asteroids/1/ast";
		String suffix = ".png";
		int count = 81;
		asteroidImages = Loader.loadImages(path, suffix,count);
	}
	
	public Image[] getExplosionImages() {
		if (explosionImages == null) {
			loadExplosionImages();
		}
		return explosionImages;
	}

	private void loadExplosionImages() {
		String path = "/images/Explosions/1/Expl";
		String suffix = ".png";
		int count = 72;
		explosionImages = Loader.loadImages(path, suffix,count);
	}
	
	public Image[] getUserShipImages() {
		if (userShipImages == null) {
			loadUserShipImages();
		}
		return userShipImages;
	}

	private void loadUserShipImages() {
		String path = "/images/Ship/ship";
		String suffix = ".png";
		int count = 26;
		userShipImages = Loader.loadImages(path, suffix,count);
	}
	
	public Image getWeaponImage() {
		if (weaponImage == null) {
			loadWeaponImage();
		}
		return weaponImage;
	}

	private void loadWeaponImage() {
		String path = "/images/Weapon/w1_.png";
		weaponImage = Loader.loadImage(path);
	}
	
	public Image getBulletImage() {
		if (bulletImage == null) {
			loadBulletImage();
		}
		return bulletImage;
	}

	private void loadBulletImage() {
		String path = "/images/Weapon/b1.png";
		bulletImage = Loader.loadImage(path);
	}

	
	private static class Loader{

		public static Image[] loadImages(String path, String suffix, int count) {
			Image[] result = new Image[count];
			for(int i = 0;i<count;i++) {
//				result[i] = new Image(Loader.class.getResourceAsStream(path+i+suffix));
				result[i] = new Image(path+i+suffix);
			}
			return result;
		}

		public static Image loadImage(String path) {
			Image result;
//			result = new Image(Loader.class.getResourceAsStream(path));;
			result = new Image(path);
			return result;
		}
	}
	
}
