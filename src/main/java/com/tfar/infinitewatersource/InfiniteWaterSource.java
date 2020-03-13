package com.tfar.infinitewatersource;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

@Mod(value = InfiniteWaterSource.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InfiniteWaterSource {

  public static final String MODID = "infinitewatersource";

  @ObjectHolder(MODID + ":" + MODID)
  public static BlockInfiniteWater infiniteWater;

  @ObjectHolder(MODID + ":" + MODID)
  public static TileEntityType<?> infiniteWaterTile;

  @SubscribeEvent
  public static void registerBlock(RegistryEvent.Register<Block> e) {
    register(
            new BlockInfiniteWater
            (Block.Properties.create(Material.IRON).hardnessAndResistance
                    (2,6).sound(SoundType.METAL)),MODID,e.getRegistry());
  }

  private static <T extends IForgeRegistryEntry<T>> void register(T obj, String name, IForgeRegistry<T> registry) {
    registry.register(obj.setRegistryName(new ResourceLocation(MODID, name)));
  }

  @SubscribeEvent
  public static void registerItemBlock(RegistryEvent.Register<Item> e) {
    register(new BlockItem(infiniteWater, new Item.Properties().group(ItemGroup.REDSTONE))
            ,MODID,e.getRegistry());
  }

  @SubscribeEvent
  public static void registerTile(RegistryEvent.Register<TileEntityType<?>> e) {
            register(TileEntityType.Builder.create(TileInfiniteWater::new, infiniteWater).build(null), MODID, e.getRegistry());
  }
}
