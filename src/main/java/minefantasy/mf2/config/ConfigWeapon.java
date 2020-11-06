package minefantasy.mf2.config;

public class ConfigWeapon extends ConfigurationBaseMF {
    public static final String CATEGORY_PENALTIES = "Penalties";
    public static final String CATEGORY_BONUS = "Bonuses";
    public static boolean useBalance;
    public static boolean ranged;
    public static boolean  hide_Delay;
    public static boolean breakArrowsGround;
    public static float arrowBreakMod;
    public static boolean xpTrain;

    @Override
    protected void loadConfig() {
        ranged = Boolean.parseBoolean(config.get(CATEGORY_PENALTIES, "Range of weapons", false,
                "This option disable modification of weapon range")
                .getString());

        hide_Delay = Boolean.parseBoolean(config.get(CATEGORY_PENALTIES, "Delay hide", true,
                "This option disable tooltip")
                .getString());

        useBalance = Boolean.parseBoolean(config.get(CATEGORY_PENALTIES, "Heavy Weapon Balance", true,
                "This causes heavy weapons to throw the camera off angle. This is recommended as it gives a downside to avoid overpowering heavy weapons. With this active: spamming hits is difficult")
                .getString());
        breakArrowsGround = Boolean.parseBoolean(
                config.get(CATEGORY_PENALTIES, "Arrow Ground Break", true, "Arrows can break when hitting the ground")
                        .getString());
        arrowBreakMod = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Arrow Break Chance modifier", 1.0F,
                "This modifies the rate that arrows break when hitting blocks, 1.0=normal higher numbers increases the break chance")
                .getString());

        xpTrain = Boolean.parseBoolean(config
                .get(CATEGORY_BONUS, "Training weapon xp", true,
                        "This option allows training weapons to increase your xp level slowly when hitting with them")
                .getString());
    }

}
