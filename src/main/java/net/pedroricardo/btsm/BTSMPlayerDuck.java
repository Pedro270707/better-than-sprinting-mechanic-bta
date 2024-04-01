package net.pedroricardo.btsm;

public interface BTSMPlayerDuck {
	void addStamina(float value);
	void setStamina(float value);
	float getStamina();
	float getMaxStamina();

	boolean isHungry();
	void setHungry(boolean value);
	boolean isWrongTemperature();
	boolean areLegsDestroyed();
	void setLegsDestroyed(boolean value);
	boolean isTooTired();
	void setTooTired(boolean value);
	boolean isSick();
	void setSick(boolean value);
	boolean isThirsty();
	void setThirsty(boolean value);
	boolean isFive();
	void setFive(boolean value);
	boolean isTooDark();
	void setTooDark(boolean value);
	boolean isUnderRain();
	void setUnderRain(boolean value);
	boolean isTooHeavy();
	void setTooHeavy(boolean value);
	boolean isLookingUp();
	boolean gameTooSlow();
}
