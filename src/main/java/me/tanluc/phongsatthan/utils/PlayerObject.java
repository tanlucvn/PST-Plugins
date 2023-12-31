package me.tanluc.phongsatthan.utils;

import java.util.UUID;


public class PlayerObject {

    private final UUID uuid;
    private final String name;
    private int commonSlain;
    private int epicSlain;
    private int legendarySlain;
    private int totalSlain;
    private int currentXp;
    private int currentLevel;
    private int totalXp;
    private int commonOwned;
    private int epicOwned;
    private int legendaryOwned;
    private int totalOwned;

    public PlayerObject(UUID uuid,
                        String name,
                        int commonSlain,
                        int epicSlain,
                        int legendarySlain,
                        int totalSlain,
                        int currentXp,
                        int currentLevel,
                        int totalXp,
                        int commonOwned,
                        int epicOwned,
                        int legendaryOwned,
                        int totalOwned) {

        this.uuid = uuid;
        this.name = name;
        this.commonSlain = commonSlain;
        this.epicSlain = epicSlain;
        this.legendarySlain = legendarySlain;
        this.totalSlain = totalSlain;
        this.currentXp = currentXp;
        this.currentLevel = currentLevel;
        this.totalXp = totalXp;
        this.commonOwned = commonOwned;
        this.epicOwned = epicOwned;
        this.legendaryOwned = legendaryOwned;
        this.totalOwned = totalOwned;
    }

    public void updatePlayer(int cslain, int eslain, int lslain, int tslain, int xp, int level, int txp, int cowned, int eowned, int lowned, int towned) {
        this.commonSlain = cslain;
        this.epicSlain = eslain;
        this.legendarySlain = lslain;
        this.totalSlain = tslain;
        this.currentXp = xp;
        this.currentLevel = level;
        this.totalXp = txp;
        this.commonOwned = cowned;
        this.epicOwned = eowned;
        this.legendaryOwned = lowned;
        this.totalOwned = towned;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getCommonSlain() {
        return commonSlain;
    }

    public int getEpicSlain() {
        return epicSlain;
    }

    public int getLegendarySlain() {
        return legendarySlain;
    }

    public int getTotalSlain() {
        return (getCommonSlain() + getLegendarySlain() + getEpicSlain());
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public int getCommonOwned() {
        return commonOwned;
    }

    public int getEpicOwned() {
        return epicOwned;
    }

    public int getLegendaryOwned() {
        return legendaryOwned;
    }

    public int getTotalOwned() {
        return (getCommonOwned() + getEpicOwned() + getLegendaryOwned());
    }

    public void setCommonSlain(int slain){
        this.commonSlain = slain;
    }

    public void setEpicSlain(int slain){
        this.epicSlain = slain;
    }

    public void setLegendarySlain(int slain) { this.legendarySlain = slain; }

    public void setCurrentXp(int xp) { this.currentXp = xp; }

    public void setCurrentLevel(int level) { this.currentLevel = level; }

    public void setTotalXp(int total) { this.totalXp = total; }

    public void setCommonOwned(int owned) { this.commonOwned = owned; }

    public void setEpicOwned(int owned) { this.epicOwned = owned; }

    public void setLegendaryOwned(int owned) { this.legendaryOwned = owned; }
}
