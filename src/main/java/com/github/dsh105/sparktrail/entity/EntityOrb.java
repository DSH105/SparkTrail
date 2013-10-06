package com.github.dsh105.sparktrail.entity;

import net.minecraft.server.v1_6_R3.*;

/**
 * Project by DSH105
 */

public class EntityOrb extends EntityExperienceOrb {

	public int targetTick;
	public int age;
	public int c;
	private int d = 5;
	private int value;
	private EntityHuman targetPlayer;
	private int targetTime;

	public EntityOrb(World world, double d0, double d1, double d2, int i) {
		super(world);
		this.value = i;
	}

	protected boolean e_() {
		return false;
	}

	public EntityOrb(World world) {
		super(world);
	}

	protected void a() {}

	public void l_() {
		super.l_();
		if (this.c > 0) {
			--this.c;
		}

		this.lastX = this.locX;
		this.lastY = this.locY;
		this.lastZ = this.locZ;
		this.motY -= 0.029999999329447746D;
		if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA) {
			this.motY = 0.20000000298023224D;
			this.motX = (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			this.motZ = (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			//this.makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
		}

		this.i(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
		double d0 = 8.0D;

		if (this.targetTime < this.targetTick - 20 + this.id % 100) {
			if (this.targetPlayer == null || this.targetPlayer.e(this) > d0 * d0) {
				this.targetPlayer = this.world.findNearbyPlayer(this, d0);
			}

			this.targetTime = this.targetTick;
		}

		if (this.targetPlayer != null) {
			double d1 = (this.targetPlayer.locX - this.locX) / d0;
			double d2 = (this.targetPlayer.locY + (double) this.targetPlayer.getHeadHeight() - this.locY) / d0;
			double d3 = (this.targetPlayer.locZ - this.locZ) / d0;
			double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
			double d5 = 1.0D - d4;

			if (d5 > 0.0D) {
				d5 *= d5;
				this.motX += d1 / d4 * d5 * 0.1D;
				this.motY += d2 / d4 * d5 * 0.1D;
				this.motZ += d3 / d4 * d5 * 0.1D;
			}
		}

		this.move(this.motX, this.motY, this.motZ);
		float f = 0.98F;

		if (this.onGround) {
			f = 0.58800006F;
			int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

			if (i > 0) {
				f = Block.byId[i].frictionFactor * 0.98F;
			}
		}

		this.motX *= (double) f;
		this.motY *= 0.9800000190734863D;
		this.motZ *= (double) f;
		if (this.onGround) {
			this.motY *= -0.8999999761581421D;
		}

		++this.targetTick;
		++this.age;
		if (this.age >= 50) {
			this.die();
		}
	}

	@Override
	public void b_(EntityHuman entityhuman) {
		/*if (!this.world.isStatic) {
			if (this.c == 0 && entityhuman.bv == 0) {
				entityhuman.bv = 2;
				this.makeSound("random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
				entityhuman.receive(this, 1);
				entityhuman.giveExp(this.value);*/
				this.die();
			/*}
		}*/
	}

	public int getValue() {
		return this.value;
	}
}