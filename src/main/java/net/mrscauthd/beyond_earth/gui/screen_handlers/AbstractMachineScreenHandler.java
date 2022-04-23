package net.mrscauthd.beyond_earth.gui.screen_handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.mrscauthd.beyond_earth.blocks.machines.entity.AbstractMachineBlockEntity;

public abstract class AbstractMachineScreenHandler extends ScreenHandler {

    protected final AbstractMachineBlockEntity blockEntity;

    public AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory,
            AbstractMachineBlockEntity entity) {
        this(type, syncId, inventory, entity, new Slot[] {});
    }

    // Add additional slots.
    public AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory,
            AbstractMachineBlockEntity entity, Slot[] slots) {
        super(type, syncId);
        this.blockEntity = entity;

        checkSize(inventory, this.blockEntity.getInventorySize());

        this.blockEntity.onOpen(inventory.player);

        for (Slot slot : slots) {
            this.addSlot(slot);
        }

        this.setPlayerInventory(inventory);
    }

    public AbstractMachineBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.blockEntity.canPlayerUse(player);
    }

    protected void setPlayerInventory(PlayerInventory inventory) {
        int m;
        int l;

        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            addSlot(new Slot(inventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (index < this.blockEntity.size()) {
                if (!this.insertItem(originalStack, this.blockEntity.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.blockEntity.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    // Fixes a client sync issue.
    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        this.updateToClient();
    }
}