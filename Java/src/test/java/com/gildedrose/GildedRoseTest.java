package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void sellInAndQualityDropsByOneAtEndOfDay() {
        int sellIn = 10;
        int quality = 20;
        Item item = item("Generic Item", sellIn, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(sellIn - 1, item.sellIn, "SellIn: At the end of each day our system lowers both values for every item");
        assertEquals(quality - 1, item.quality, "Quality: At the end of each day our system lowers both values for every item");
    }

    @Test
    void qualityDropsByTwoWithPassedSellDate() {
        int quality = 20;
        Item item = item("Generic Item", 0, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(quality - 2, item.quality, "Once the sell by date has passed, Quality degrades twice as fast");
    }

    @Test
    void qualityNeverNegative() {
        Item item = item("Generic Item", 10, 0);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(0, item.quality, "The Quality of an item is never negative");
    }

    @Test
    void qualityNeverNegativeWithPassedSellData() {
        Item item = item("Generic Item", 0, 0);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(0, item.quality, "The Quality of an item is never negative");
    }

    @Test
    void agedBrieIncreasesQuality() {
        int quality = 20;
        Item item = item("Aged Brie", 10, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(quality + 1, item.quality, "'Aged Brie' actually increases in Quality the older it gets");
    }

    @Test
    void agedBrieWithPassedDateIncreasesQuality() {
        int quality = 20;
        Item item = item("Aged Brie", 0, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(quality + 2, item.quality, "'Aged Brie' actually increases in Quality the older it gets");
    }

    @Test
    void qualityNeverHigherThan50() {
        int quality = 50;
        Item item = item("Aged Brie", 10, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(50, item.quality, "The Quality of an item is never more than 50");
    }

    @Test
    void qualityNeverHigherThan50WithPassedSellDate() {
        int quality = 50;
        Item item = item("Aged Brie", 0, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(50, item.quality, "The Quality of an item is never more than 50");
    }

    @Test
    void sulfurasDoesNotDropAtTheEndOfDay() {
        int sellIn = 10;
        int quality = 80;
        Item item = item("Sulfuras, Hand of Ragnaros", sellIn, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(sellIn, item.sellIn, "SellIn: 'Sulfuras', being a legendary item, never has to be sold or decreases in Quality");
        assertEquals(quality, item.quality, "Quality: 'Sulfuras', being a legendary item, never has to be sold or decreases in Quality");
    }

    @Test
    void sulfurasDoesNotDropAtTheEndOfDayWithPassedSellDate() {
        int sellIn = 0;
        int quality = 80;
        Item item = item("Sulfuras, Hand of Ragnaros", sellIn, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(sellIn, item.sellIn, "SellIn: 'Sulfuras', being a legendary item, never has to be sold or decreases in Quality");
        assertEquals(quality, item.quality, "Quality: 'Sulfuras', being a legendary item, never has to be sold or decreases in Quality");
    }

    @Test
    void backstagePassesIncreasesBy1WithPassedSellDateHigherThan10() {
        backStagePassesIncreasesWhenSellDateDecreases(11, 20, 1);
    }

    @Test
    void backstagePassesIncreasesBy2IWithPassedSellDateHigherBetween5And10() {
        backStagePassesIncreasesWhenSellDateDecreases(10, 20, 2);
    }

    @Test
    void backstagePassesIncreasesBy3WithPassedSellDateLessThan6() {
        backStagePassesIncreasesWhenSellDateDecreases(5, 20, 3);
    }

    private void backStagePassesIncreasesWhenSellDateDecreases(int sellIn, int quality, int increase) {
        Item item = item("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(quality + increase, item.quality, "'Backstage passes', like aged brie, increases in Quality as its SellIn value approaches");
    }

    @Test
    void backstagePassesQualityNeverHigherThan50WithPassedSellDateHigherThan10() {
        backStagePassesQualityNeverHigherThan50(11);
    }

    @Test
    void backstagePassesQualityNeverHigherThan50WithPassedSellDateBetween5And10() {
        backStagePassesQualityNeverHigherThan50(10);
    }

    @Test
    void backstagePassesQualityNeverHigherThan50WithPassedSellDateLessThan6() {
        backStagePassesQualityNeverHigherThan50(5);
    }


    private void backStagePassesQualityNeverHigherThan50(int sellIn) {
        Item item = item("Backstage passes to a TAFKAL80ETC concert", sellIn, 50);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(50, item.quality, "'Backstage passes', like aged brie, increases in Quality as its SellIn value approaches");
    }

    @Test
    void backStagePassesQuality0ExpiredSellDate() {
        Item item = item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        GildedRose app = gildedRose(item);
        app.updateQuality();
        assertEquals(0, item.quality, "Quality drops to 0 after the concert");
    }

    private Item item(String name, int sellIn, int quality) {
        return new Item(name, sellIn, quality);
    }

    private GildedRose gildedRose(Item... items) {
        return new GildedRose(items);
    }
}
