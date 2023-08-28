package com.app.beyondlottotv.Model;

public class GameItem {

    private int imageResId;
    private String gamePrice;
    private String gameDayAndWinnings;

    public GameItem(int imageResId, String gamePrice, String gameDayAndWinnings) {
        this.imageResId = imageResId;
        this.gamePrice = gamePrice;
        this.gameDayAndWinnings = gameDayAndWinnings;
    }

    public GameItem() {
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(String gamePrice) {
        this.gamePrice = gamePrice;
    }

    public String getGameDayAndWinnings() {
        return gameDayAndWinnings;
    }

    public void setGameDayAndWinnings(String gameDayAndWinnings) {
        this.gameDayAndWinnings = gameDayAndWinnings;
    }
}
