package com.treasurehunting.java.graphics;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.enemy.Bat;
import com.treasurehunting.java.entity.enemy.BlueGolem;
import com.treasurehunting.java.entity.enemy.Skeleton;
import com.treasurehunting.java.entity.enemy.ToxicFruit;
import com.treasurehunting.java.obstacle.Trap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    public static SpriteSheet playerSSGunIdle = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Idle/Gun/Idle8.png", 48, 64 );
    public static SpriteSheet playerSSGunRun = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Run/Gun/Run8.png", 48, 64 );
    public static SpriteSheet playerSSGunStandShooting = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Attack/Gun/StandShooting8.png", 48, 64 );
    public static SpriteSheet playerSSGunRunShooting = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Run_while_shooting/RunWhileShooting8.png", 48, 64 );
    public static SpriteSheet playerSSGunDash = new SpriteSheet( "com/treasurehunting/assets/entities/adventurer/Dash/Gun/Dash8.png", 48, 64 );
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
    public static SpriteSheet ultimateBullet = new SpriteSheet("com/treasurehunting/assets/effects/72x72ElectroThorn.png", 72, 72);
    public static SpriteSheet explodeBomb = new SpriteSheet("com/treasurehunting/assets/effects/32x32Explode.png", 32, 32);
    public static SpriteSheet chestSS = new SpriteSheet("com/treasurehunting/assets/objects/16x16Chest.png", 16, 16);
    public static SpriteSheet boxSS = new SpriteSheet("com/treasurehunting/assets/objects/16x16Box.png", 16, 16);
    public static SpriteSheet manaSS = new SpriteSheet("com/treasurehunting/assets/objects/16x16Mana.png", 16, 16);
    public static SpriteSheet portalSS = new SpriteSheet("com/treasurehunting/assets/objects/32x32Portal.png", 32, 32);
    public static SpriteSheet spiderNestSS = new SpriteSheet("com/treasurehunting/assets/objects/16x32SpiderNest.png", 16, 32);
    public static SpriteSheet trapSS = new SpriteSheet("com/treasurehunting/assets/objects/16x16Trap.png", 16, 16);

    public static SpriteSheet playerSkillIcon = new SpriteSheet("com/treasurehunting/assets/ui/PlayerSkillIcon.png", 43, 44);

    public static String dungeonMap = "com/treasurehunting/assets/tile/DungeonMap.xml";
    public static String graveMap = "com/treasurehunting/assets/tile/GraveMaze.xml";

    public static int getNormLayerIndex() {
        if (currMapID == takeMapID(dungeonMap)) {
            return 3;
        } else if (currMapID == takeMapID(graveMap)) {
            return 1;
        }

        return -1;
    }

    public static int getObjLayerIndex() {
        if (currMapID == takeMapID(dungeonMap)) {
            return 0;
        } else if (currMapID == takeMapID(graveMap)) {
            return 0;
        }

        return -1;
    }

    public static int getEntityLayerIndex() {
        if (currMapID == takeMapID(dungeonMap)) {
            return 5;
        } else if (currMapID == takeMapID(graveMap)) {
            return 4;
        }

        return -1;
    }

    public static int getObsLayerIndex() {
        if (currMapID == takeMapID(dungeonMap)) {
            return 6;
        } else if (currMapID == takeMapID(graveMap)) {
            return 5;
        }

        return -1;
    }

    public static int mapQty = 2;
    public static int currMapID = takeMapID(dungeonMap);
    public static int firstMapID = takeMapID(dungeonMap);

    public static boolean switchNextMap() {
        if (currMapID++ == mapQty) {
            return false;
        }

        return true;
    }

    public static void returnFirstMap() {
        currMapID = firstMapID;
    }

    public static int takeMapID(String fileURL) {
        if (fileURL.equals(dungeonMap)) {
            return 0;
        } else if (fileURL.equals(graveMap)) {
            return 1;
        }

        return -1;
    }

    public static String takeMapURL(int mapID) {
        if (mapID == 0) {
            return "com/treasurehunting/assets/tile/DungeonMap.xml";
        } else if (mapID == 1) {
            return "com/treasurehunting/assets/tile/GraveMaze.xml";
        }
        return "";
    }

    public static int holeTileID = 8;
    public static int chestTileID = 288;
    public static int boxTileID = 206;
    public static int manaTileID = 543;
    public static int portalTileID = 247;
    public static int spiderNestTileID = 260;
    public static int trapTileID = 296;

    public static int playerTileID = 203;
    public static int blueGolemTileID = 201;
    public static int batTileID = 202;
    public static int skeletonTileID = 106;
    public static int toxicFruitTileID = 107;
    public static int explodeBombID = 100;
    public static int bombBulletID = 101;
    public static int normBulletID = 102;
    public static int critBulletID = 103;
    public static int ultimateBulletID = 104;

    public static String font = "com/treasurehunting/assets/fonts/font.png";
    public static Fontf fontf;
    public static String meatMadnessFont = "com/treasurehunting/assets/fonts/Stackedpixel.ttf";
    public static String gravityBoldFont = "com/treasurehunting/assets/fonts/GravityBold8.ttf";
    public static String pixelCandyFont = "com/treasurehunting/assets/fonts/PixelCandy.ttf";
    static {
        fontf = new Fontf();
        fontf.loadFont(Assets.pixelCandyFont, "Pixel Game", 128);
        fontf.addSize("Pixel Game", java.awt.Font.PLAIN, 140);
        fontf.addSize("Pixel Game", java.awt.Font.PLAIN, 48);
        fontf.addSize("Pixel Game", java.awt.Font.PLAIN, 32);
        fontf.addSize("Pixel Game", java.awt.Font.PLAIN, 16);
        fontf.addSize("Pixel Game", java.awt.Font.PLAIN, 8);
        fontf.loadFont(Assets.meatMadnessFont, "MeatMadness", 32);
        fontf.addSize("MeatMadness", java.awt.Font.PLAIN, 16);
        fontf.addSize("MeatMadness", java.awt.Font.PLAIN, 8);
        fontf.loadFont(Assets.gravityBoldFont, "GravityBold8", 8);
    }

    public static SpriteSheet buttonSS = new SpriteSheet("com/treasurehunting/assets/ui/HumbleUI/PNG/SpriteSheet.png", 704, 2160);
    public static SpriteSheet healthBarSS = new SpriteSheet("com/treasurehunting/assets/ui/HealthBar.png", 120, 30);
    public static SpriteSheet manaBarSS = new SpriteSheet("com/treasurehunting/assets/ui/ManaBar.png", 64, 21);
    public static SpriteSheet backGroundSS = new SpriteSheet("com/treasurehunting/assets/ui/StaticHub.png", 1920, 1080);
    public static SpriteSheet pauseBoardSS = new SpriteSheet("com/treasurehunting/assets/ui/PauseBoard.png", 183, 280);
    public static SpriteSheet displayBoardSS = new SpriteSheet("com/treasurehunting/assets/ui/DisplayBoard.png", 248, 280);
    public static SpriteSheet audioBoardSS = new SpriteSheet("com/treasurehunting/assets/ui/AudioBoard.png", 248, 280);
    public static SpriteSheet videoBoardSS = new SpriteSheet("com/treasurehunting/assets/ui/VideoBoard.png", 248, 280);
    public static SpriteSheet controlsBoardSS = new SpriteSheet("com/treasurehunting/assets/ui/ControlsBoard.png", 248, 280);
    public static SpriteSheet abilityBoardSS = new SpriteSheet("com/treasurehunting/assets/ui/AbilityBar.png", 256, 64);

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

