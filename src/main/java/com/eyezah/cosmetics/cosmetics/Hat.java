package com.eyezah.cosmetics.cosmetics;

import cc.cosmetica.api.Model;
import com.eyezah.cosmetics.Cosmetica;
import com.eyezah.cosmetics.cosmetics.model.BakableModel;
import com.eyezah.cosmetics.cosmetics.model.OverriddenModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class Hat<T extends Player> extends CustomLayer<T, PlayerModel<T>> {
	private ModelManager modelManager;

	public Hat(RenderLayerParent<T, PlayerModel<T>> renderLayerParent) {
		super(renderLayerParent);
		this.modelManager = Minecraft.getInstance().getModelManager();
	}

	@Override
	public void render(PoseStack stack, MultiBufferSource multiBufferSource, int packedLightProbably, T player, float f, float g, float pitch, float j, float k, float l) {
		if (player.isInvisible()) return;
		List<BakableModel> hats = overridden.getList(() -> Cosmetica.getPlayerData(player).hats());

		stack.pushPose();

		for (BakableModel modelData : hats) {
			if ((modelData.extraInfo() & Model.HIDE_HAT_UNDER_HELMET) == 0 && player.hasItemInSlot(EquipmentSlot.HEAD))
				return; // disable hat flag

			if ((modelData.extraInfo() & Model.LOCK_HAT_ORIENTATION) == 0) {
				doCoolRenderThings(modelData, this.getParentModel().getHead(), stack, multiBufferSource, packedLightProbably, 0, 0.75f, 0);
			} else {
				doCoolRenderThings(modelData, this.getParentModel().body, stack, multiBufferSource, packedLightProbably, 0, 0.77f, 0);
			}

			stack.scale(1.001f, 1.001f, 1.001f); // stop multiple hats conflicting
		}

		stack.popPose();
	}

	public static final OverriddenModel overridden = new OverriddenModel();
}