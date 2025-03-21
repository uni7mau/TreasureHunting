package com.treasurehunting.java.graphics;

public class Assets {

    public static SpriteSheet playerSSGunIdle = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Idle/Gun/Idle8.png", 48, 64 );
    public static SpriteSheet playerSSGunRun = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Run/Gun/Run8.png", 48, 64 );
    public static SpriteSheet playerSSGunStandShooting = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Attack/Gun/StandShooting8.png", 48, 64 );
    public static SpriteSheet playerSSGunRunShooting = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Run_while_shooting/RunWhileShooting8.png", 48, 64 );
    public static SpriteSheet playerSSGunDash = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Dash/Gun/Dash8.png", 48, 64 );
    public static SpriteSheet playerSSGunReloading = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Reloading/Reloading8.png", 48, 64 );
    public static SpriteSheet playerSSGunDeath = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Death/Gun/Death8.png", 48, 64 );

    public static SpriteSheet batSSIdleSleep  = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-Sleep.png", 64, 64);
    public static SpriteSheet batSSWakeUp = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-WakeUp.png", 64, 64);
    public static SpriteSheet batSSFly  = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-IdleFly.png", 64, 64);
    public static SpriteSheet batSSRun = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-Run.png", 64, 64);
    public static SpriteSheet batSSSkill1 = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-Attack1.png", 64, 64);
    public static SpriteSheet batSSSkill2 = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-Attack2.png", 64, 64);
    public static SpriteSheet batSSHurt = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-Hurt.png", 64, 64);
    public static SpriteSheet batSSDie = new SpriteSheet("com/treasurehunting/assets/entities/enemies/Bat/Bat-Die.png", 64, 64);

    public static SpriteSheet blueGolemSSIdle = new SpriteSheet("com/treasurehunting/assets/entities/enemies/BlueGolemBoss/Golem_1_idle.png", 90, 64);
    public static SpriteSheet blueGolemSSSkill1 = new SpriteSheet("com/treasurehunting/assets/entities/enemies/BlueGolemBoss/Golem_1_attack.png", 90, 64);
    public static SpriteSheet blueGolemSSWalk  = new SpriteSheet("com/treasurehunting/assets/entities/enemies/BlueGolemBoss/Golem_1_walk.png", 90, 64);
    public static SpriteSheet blueGolemSSHurt = new SpriteSheet("com/treasurehunting/assets/entities/enemies/BlueGolemBoss/Golem_1_hurt.png", 90, 64);
    public static SpriteSheet blueGolemSSDie = new SpriteSheet("com/treasurehunting/assets/entities/enemies/BlueGolemBoss/Golem_1_die.png", 90, 64);

    public static SpriteSheet toxicFruitSSIdle  = new SpriteSheet("com/treasurehunting/assets/entities/enemies/ToxicFruit/Enemy3-Idle.png", 64, 64);
    public static SpriteSheet toxicFruitSSSkill1 = new SpriteSheet("com/treasurehunting/assets/entities/enemies/ToxicFruit/Enemy3-Attack.png", 64, 64);
    public static SpriteSheet toxicFruitSSFly  = new SpriteSheet("com/treasurehunting/assets/entities/enemies/ToxicFruit/Enemy3-Fly.png", 64, 64);
    public static SpriteSheet toxicFruitSSHurt = new SpriteSheet("com/treasurehunting/assets/entities/enemies/ToxicFruit/Enemy3-Hit.png", 64, 64);
    public static SpriteSheet toxicFruitSSDie = new SpriteSheet("com/treasurehunting/assets/entities/enemies/ToxicFruit/Enemy3-Die.png", 64, 64);

    public static SpriteSheet yellowSleketonSSIdle  = new SpriteSheet("com/treasurehunting/assets/entities/enemies/YellowSkeleton/Skeleton_01_Yellow_Idle.png", 96, 64);
    public static SpriteSheet yellowSleketonSSSkill1 = new SpriteSheet("com/treasurehunting/assets/entities/enemies/YellowSkeleton/Skeleton_01_Yellow_Attack1.png", 96, 64);
    public static SpriteSheet yellowSleketonSSSkill2 = new SpriteSheet("com/treasurehunting/assets/entities/enemies/YellowSkeleton/Skeleton_01_Yellow_Attack2.png", 96, 64);
    public static SpriteSheet yellowSleketonSSWalk  = new SpriteSheet("com/treasurehunting/assets/entities/enemies/YellowSkeleton/Skeleton_01_Yellow_Walk.png", 96, 64);
    public static SpriteSheet yellowSleketonSSHurt = new SpriteSheet("com/treasurehunting/assets/entities/enemies/YellowSkeleton/Skeleton_01_Yellow_Hurt.png", 96, 64);
    public static SpriteSheet yellowSleketonSSDie = new SpriteSheet("com/treasurehunting/assets/entities/enemies/YellowSkeleton/Skeleton_01_Yellow_Die.png", 96, 64);

    public static SpriteSheet normBullet = new SpriteSheet("com/treasurehunting/assets/objects/16x16NormBullet.png", 16, 16);
    public static SpriteSheet critBullet = new SpriteSheet("com/treasurehunting/assets/objects/16x16CritBullet.png", 16, 16);
    public static SpriteSheet skillBullet = new SpriteSheet("com/treasurehunting/assets/objects/32x32SkillBullet.png", 32, 32);
    public static SpriteSheet ultimateBullet = new SpriteSheet("com/treasurehunting/assets/objects/48x48UltimateEnergyBullet.png", 48, 48);
    public static SpriteSheet explodeBomb = new SpriteSheet("com/treasurehunting/assets/objects/16x16ExplodeBomb.png", 16, 16);

    public static String beginTileMap = "com/treasurehunting/assets/tile/tilemap2.xml";
    public static String font = "com/treasurehunting/assets/fonts/font.png";
    public static String meatMadnessFont = "com/treasurehunting/assets/fonts/Stackedpixel.ttf";
    public static String gravityBoldFont = "com/treasurehunting/assets/fonts/GravityBold8.ttf";

    public static String uiSS = "com/treasurehunting/assets/ui/ui.png";
    public static String buttonSS = "com/treasurehunting/assets/ui/buttons.png";

    public static SpriteSheet inventorySS = new SpriteSheet("com/treasurehunting/assets/ui/Inventory.png", 120, 172);
    public static SpriteSheet healthBarSS = new SpriteSheet("com/treasurehunting/assets/ui/HealthBar.png", 120, 30);
    public static SpriteSheet manaBarSS = new SpriteSheet("com/treasurehunting/assets/ui/ManaBar.png", 64, 21);

    // Animation/state things
    public static final int IDLE = 0;
    public static final int WALK = 1;
    public static final int ATTACK = 2;
    public static final int INVINCIBLE = 3;
    public static final int DIE = 4;
    public static final int RUN = 5;
    public static final int DASH = 6;
    public static final int STANDSHOOTING = 7;
    public static final int RUNSHOOTING = 8;
    public static final int RELOADING = 9;
    public static final int WAKEUP = 10;
    public static final int SMASH = 11;
    public static final int STRAIGHTATTACK = 12;
    public static final int ROUNDATTACK = 13;
    public static final int TARGETATTACK = 14;
    public static final int RANGEATTACK = 15;
    public static final int DASHSATTACK = 16;
    public static final int FLY = 17;

    // Following the Adventurer sprite sheet
    public static final int DOWN = 0;
    public static final int LEFTDOWN = 1;
    public static final int LEFT = 2;
    public static final int LEFTUP = 3;
    public static final int UP = 4;
    public static final int RIGHTUP = 5;
    public static final int RIGHT = 6;
    public static final int RIGHTDOWN = 7;

    public static int takeAnimId(String strID) {
        if (strID.equals("IDLE")) return IDLE;
        else if (strID.equals("WALK")) return WALK;
        else if (strID.equals("ATTACK")) return ATTACK;
        else if (strID.equals("INVINCIBLE")) return INVINCIBLE;
        else if (strID.equals("DIE")) return DIE;
        else if (strID.equals("RUN")) return RUN;
        else if (strID.equals("DASH")) return DASH;
        else if (strID.equals("STANDSHOOTING")) return STANDSHOOTING;
        else if (strID.equals("RUNSHOOTING")) return RUNSHOOTING;
        else if (strID.equals("RELOADING")) return RELOADING;
        else if (strID.equals("WAKEUP")) return WAKEUP;
        else if (strID.equals("SMASH")) return SMASH;
        else if (strID.equals("STRAIGHTATTACK")) return STRAIGHTATTACK;
        else if (strID.equals("ROUNDATTACK")) return ROUNDATTACK;
        else if (strID.equals("TARGETATTACK")) return TARGETATTACK;
        else if (strID.equals("RANGEATTACK")) return RANGEATTACK;
        else if (strID.equals("DASHSATTACK")) return DASHSATTACK;
        else if (strID.equals("FLY")) return FLY;
        return -1;
    }

}

