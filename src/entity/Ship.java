package entity;
import audio.SoundManager;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Set;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import engine.InputManager;

/**
 * Implements a ship, to be controlled by the player.
 */
public class Ship extends Entity {

	private static final int SPEED = 2;
	private static final int BULLET_SPEED = -6;
	private static final int SHOOTING_INTERVAL = 750;

	private Cooldown shootingCooldown;
	private Cooldown destructionCooldown;
	private Cooldown shieldCooldown;
	private boolean isInvincible;

	// === Player ID (1 = P1, 2 = P2) ===
	private int playerId = 1;
	public void setPlayerId(int pid) { this.playerId = pid; }
	public int getPlayerId() { return this.playerId; }

	public Ship(final int positionX, final int positionY, final Color color) {
		super(positionX, positionY, 13 * 2, 8 * 2, color);
		this.spriteType = SpriteType.Ship;
		this.shootingCooldown = Core.getCooldown(ShopItem.getShootingInterval());
		this.destructionCooldown = Core.getCooldown(1000);
		this.shieldCooldown = Core.getCooldown(0);
		this.isInvincible = false;
	}

	public final void moveRight() {
		int shipspeed = ShopItem.getSHIPSpeedCOUNT();
		this.positionX += SPEED * (1 + shipspeed / 10);
	}

	public final void moveLeft() {
		int shipspeed = ShopItem.getSHIPSpeedCOUNT();
		this.positionX -= SPEED * (1 + shipspeed / 10);
	}

	public final void moveUp() {
		int shipspeed = ShopItem.getSHIPSpeedCOUNT();
		this.positionY -= SPEED * (1 + shipspeed / 10);
	}

	public final void moveDown() {
		int shipspeed = ShopItem.getSHIPSpeedCOUNT();
		this.positionY += SPEED * (1 + shipspeed / 10);
	}

	public final boolean shoot(final Set<Bullet> bullets) {
		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();

			int bulletCount = ShopItem.getMultiShotBulletCount();
			int spacing = ShopItem.getMultiShotSpacing();
			int centerX = positionX + this.width / 2;
			int centerY = positionY;

			if (bulletCount == 1) {
				Bullet b = BulletPool.getBullet(centerX, centerY, BULLET_SPEED);
				SoundManager.stop("sfx/laser.wav");
				SoundManager.play("sfx/laser.wav");
				b.setOwnerId(this.playerId);
				bullets.add(b);
			} else {
				int startOffset = -(bulletCount / 2) * spacing;
				for (int i = 0; i < bulletCount; i++) {
					int offsetX = startOffset + (i * spacing);
					Bullet b = BulletPool.getBullet(centerX + offsetX, centerY, BULLET_SPEED);
					b.setOwnerId(this.playerId);
					bullets.add(b);

					SoundManager.stop("sfx/laser.wav");
					SoundManager.play("sfx/laser.wav");
				}
			}
			return true;
		}
		return false;
	}

	public final void update() {
		// --- Invincibilité ---
		if (this.isInvincible && this.shieldCooldown.checkFinished()) {
			this.isInvincible = false;
			this.setColor(Color.GREEN);
		}

		// --- Changement de sprite selon destruction ---
		if (!this.destructionCooldown.checkFinished())
			this.spriteType = SpriteType.ShipDestroyed;
		else
			this.spriteType = SpriteType.Ship;

		// --- Changement de couleur selon touches ---
		InputManager input = Core.getInputManager();
		if (playerId == 1) {
			if (input.isKeyDown(KeyEvent.VK_1)) this.setColor(Color.BLUE);
			if (input.isKeyDown(KeyEvent.VK_2)) this.setColor(Color.RED);
			if (input.isKeyDown(KeyEvent.VK_3)) this.setColor(Color.GREEN);
			if (input.isKeyDown(KeyEvent.VK_4)) this.setColor(Color.YELLOW);
		} else if (playerId == 2) {
			if (input.isKeyDown(KeyEvent.VK_7)) this.setColor(Color.BLUE);
			if (input.isKeyDown(KeyEvent.VK_8)) this.setColor(Color.RED);
			if (input.isKeyDown(KeyEvent.VK_9)) this.setColor(Color.GREEN);
			if (input.isKeyDown(KeyEvent.VK_0)) this.setColor(Color.YELLOW);
		}
	}

	public final void destroy() {
		if (!this.isInvincible) {
			SoundManager.stop("sfx/impact.wav");
			SoundManager.play("sfx/impact.wav");
			this.destructionCooldown.reset();
		}
	}

	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	public final int getSpeed() {
		return SPEED;
	}

	public final boolean isInvincible() {
		return this.isInvincible;
	}

	public final void activateInvincibility(final int duration) {
		this.isInvincible = true;
		this.shieldCooldown.setMilliseconds(duration);
		this.shieldCooldown.reset();
		this.setColor(Color.BLUE);
	}
}
