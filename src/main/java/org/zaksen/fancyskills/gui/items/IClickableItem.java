package org.zaksen.fancyskills.gui.items;


public abstract class IClickableItem implements ClickableItem {

    private final int slot;
    public IClickableItem(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}