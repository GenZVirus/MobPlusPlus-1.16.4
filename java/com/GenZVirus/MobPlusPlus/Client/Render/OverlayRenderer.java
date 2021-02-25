package com.GenZVirus.MobPlusPlus.Client.Render;

import java.util.List;

import com.GenZVirus.MobPlusPlus.MobPlusPlus;
import com.GenZVirus.MobPlusPlus.Client.File.XMLFileJava;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

public class OverlayRenderer {

	public static float RED;
	public static float GREEN;
	public static float BLUE;
	public static boolean BORDER;
	public static ResourceLocation Background = new ResourceLocation(MobPlusPlus.MOD_ID, "textures/bar.png");
	public static ResourceLocation Health = new ResourceLocation(MobPlusPlus.MOD_ID, "textures/health_bar_fill.png");

	public OverlayRenderer() {
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entitylivingbaseIn) {
		XMLFileJava.checkFileAndMake();
		if (!ModList.get().isLoaded("simplenameplate"))
			this.renderName(entitylivingbaseIn, entitylivingbaseIn.getName().getString(), matrixStackIn, bufferIn, packedLightIn);
		this.renderHealthBar(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn);
		String percentage = ((int) (entitylivingbaseIn.getHealth() / entitylivingbaseIn.getMaxHealth() * 100)) + "%";
		this.renderPercentage(entitylivingbaseIn, percentage, matrixStackIn, bufferIn, packedLightIn);
	}

	protected void renderPercentage(LivingEntity entityIn, String displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		double d0 = renderManager.squareDistanceTo(entityIn);
		if (!(d0 > 4096.0D)) {
			float f = entityIn.getHeight() + 0.56F;
			int i = 0;
			matrixStackIn.push();

			/****************************
			 * 
			 * Set entity name's position and orientation
			 * 
			 ****************************/

			matrixStackIn.translate(0.0D, (double) f, 0.0D);
			matrixStackIn.rotate(renderManager.getCameraOrientation());
			matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
			Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();

			/****************************
			 * 
			 * Render Entity Health Percentage
			 * 
			 ****************************/

			FontRenderer fontrenderer = renderManager.getFontRenderer();
			float f2 = (float) (-fontrenderer.getStringWidth(displayNameIn) / 2);
			renderString(displayNameIn, f2, (float) i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
			matrixStackIn.pop();
		}
	}

	/****************************
	 * 
	 * Entity name renderer
	 * 
	 ****************************/

	protected void renderName(LivingEntity entityIn, String displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		double d0 = renderManager.squareDistanceTo(entityIn);
		if (!(d0 > 4096.0D)) {
			float f = entityIn.getHeight() + 1.0F;
			int i = 0;
			matrixStackIn.push();

			/****************************
			 * 
			 * Set entity name's position and orientation
			 * 
			 ****************************/

			matrixStackIn.translate(0.0D, (double) f, 0.0D);
			matrixStackIn.rotate(renderManager.getCameraOrientation());
			matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
			Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();

			/****************************
			 * 
			 * Render Entity Name
			 * 
			 ****************************/

			FontRenderer fontrenderer = renderManager.getFontRenderer();
			float f2 = (float) (-fontrenderer.getStringWidth(displayNameIn) / 2);
			renderString(displayNameIn, f2, (float) i, -1, false, matrix4f, bufferIn, false, 553648127, packedLightIn);
			renderString(displayNameIn, f2, (float) i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
			matrixStackIn.pop();
		}
	}

	/****************************
	 * 
	 * Health bar renderer
	 * 
	 ****************************/

	protected void renderHealthBar(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entityIn) {
		matrixStackIn.push();
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();

		/****************************
		 * 
		 * Set Health bar position and orientation
		 * 
		 ****************************/

		float f = entityIn.getHeight() + 0.6F;
		matrixStackIn.translate(0.0D, (double) f, 0.0D);
		matrixStackIn.rotate(renderManager.getCameraOrientation());
		matrixStackIn.rotate(new Quaternion(0, 0, 180, true));

		/****************************
		 * 
		 * Set Health bar rendering properties and buffer
		 * 
		 ****************************/

		RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY = new RenderState.TransparencyState("translucent_transparency", () -> {
			RenderSystem.enableBlend();
		}, () -> {
			RenderSystem.disableBlend();
		});
		RenderState.AlphaState DEFAULT_ALPHA = new RenderState.AlphaState(0.003921569F);
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		IVertexBuilder bar = bufferIn.getBuffer(RenderType.makeType("mppbar", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 65536,
				RenderType.State.getBuilder().transparency(TRANSLUCENT_TRANSPARENCY).texture(new RenderState.TextureState(new ResourceLocation(MobPlusPlus.MOD_ID, "textures/bar.png"), false, true))
						.alpha(DEFAULT_ALPHA).cull(new RenderState.CullState(false)).lightmap(new RenderState.LightmapState(true)).build(true)));

		/***************************
		 * 
		 * Render Background bar
		 * 
		 ***************************/

		float length = 1.0F;
		addVertexPairBackground(bar, matrix4f, packedLightIn, length);
		bar = bufferIn.getBuffer(RenderType.makeType("mpphealthbar", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 65536,
				RenderType.State.getBuilder().transparency(TRANSLUCENT_TRANSPARENCY)
						.texture(new RenderState.TextureState(new ResourceLocation(MobPlusPlus.MOD_ID, "textures/health_bar_fill.png"), false, true)).alpha(DEFAULT_ALPHA)
						.cull(new RenderState.CullState(false)).lightmap(new RenderState.LightmapState(true)).build(true)));
		length = entityIn.getHealth() / entityIn.getMaxHealth();

		/***************************
		 * 
		 * Render health bar
		 * 
		 ***************************/

		addVertexPairHealth(bar, matrix4f, packedLightIn, length);
		matrixStackIn.pop();
	}

	public static void addVertexPairBackground(IVertexBuilder bufferIn, Matrix4f matrixIn, int packedLight, float length) {
		float x = 1.4F - 2.8F * (1 - length);
		float y = 0.2F;
		float z = 0.102F;

		bufferIn.pos(matrixIn, -1.4F, 0, z).color(1.0F, 1.0F, 1.0F, 1.0F).tex(0, 0).lightmap(packedLight).endVertex();
		bufferIn.pos(matrixIn, -1.4F, y, z).color(1.0F, 1.0F, 1.0F, 1.0F).tex(0, 1).lightmap(packedLight).endVertex();

		bufferIn.pos(matrixIn, x, y, z).color(1.0F, 1.0F, 1.0F, 1.0F).tex(1, 1).lightmap(packedLight).endVertex();
		bufferIn.pos(matrixIn, x, 0, z).color(1.0F, 1.0F, 1.0F, 1.0F).tex(1, 0).lightmap(packedLight).endVertex();
	}

	/***************************
	 * 
	 * Add vertex pair with custom colors
	 * 
	 ***************************/

	public static void addVertexPairHealth(IVertexBuilder bufferIn, Matrix4f matrixIn, int packedLight, float length) {
		float x = 1.4F - 2.8F * (1 - length);
		float y = 0.2F;
		float z = 0.101F;

		bufferIn.pos(matrixIn, -1.4F, 0, z).color(RED, GREEN, BLUE, 1.0F).tex(0, 0).lightmap(packedLight).endVertex();
		bufferIn.pos(matrixIn, -1.4F, y, z).color(RED, GREEN, BLUE, 1.0F).tex(0, 1).lightmap(packedLight).endVertex();

		bufferIn.pos(matrixIn, x, y, z).color(RED, GREEN, BLUE, 1.0F).tex(1, 1).lightmap(packedLight).endVertex();
		bufferIn.pos(matrixIn, x, 0, z).color(RED, GREEN, BLUE, 1.0F).tex(1, 0).lightmap(packedLight).endVertex();
	}

	public static int renderString(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean transparentIn, int colorBackgroundIn,
			int packedLight) {
		return renderStringAt(text, x, y, color, dropShadow, matrix, buffer, transparentIn, colorBackgroundIn, packedLight);
	}

	private static int renderStringAt(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean transparentIn, int colorBackgroundIn,
			int packedLight) {
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		FontRenderer fontrenderer = renderManager.getFontRenderer();
		boolean bidiFlag = fontrenderer.getBidiFlag();
		if (bidiFlag) {
			text = fontrenderer.bidiReorder(text);
		}

		if ((color & -67108864) == 0) {
			color |= -16777216;
		}

		if (dropShadow) {
			renderStringAtPos(text, x, y, color, true, matrix, buffer, transparentIn, colorBackgroundIn, packedLight);
		}

		Matrix4f matrix4f = matrix.copy();
		matrix4f.translate(new Vector3f(0.0F, 0.0F, 0.001F));
		x = renderStringAtPos(text, x, y, color, false, matrix4f, buffer, transparentIn, colorBackgroundIn, packedLight);
		return (int) x + (dropShadow ? 1 : 0);
	}

	private static float renderStringAtPos(String text, float x, float y, int color, boolean isShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean isTransparent, int colorBackgroundIn,
			int packedLight) {
		float f = isShadow ? 0.25F : 1.0F;
		float f1 = (float) (color >> 16 & 255) / 255.0F * f;
		float f2 = (float) (color >> 8 & 255) / 255.0F * f;
		float f3 = (float) (color & 255) / 255.0F * f;
		float f4 = x;
		float redIn = f1;
		float greenIn = f2;
		float blueIn = f3;
		float alphaIn = (float) (color >> 24 & 255) / 255.0F;
		boolean flag = false;
		boolean boldIn = false;
		boolean italicIn = false;
		boolean flag3 = false;
		boolean flag4 = false;
		List<Effect> list = Lists.newArrayList();
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		FontRenderer fontrenderer = renderManager.getFontRenderer();
		for (int i = 0; i < text.length(); ++i) {
			char c0 = text.charAt(i);
			if (c0 == 167 && i + 1 < text.length()) {
				TextFormatting textformatting = TextFormatting.fromFormattingCode(text.charAt(i + 1));
				if (textformatting != null) {
					if (!textformatting.isFancyStyling()) {
						flag = false;
						boldIn = false;
						flag4 = false;
						flag3 = false;
						italicIn = false;
						redIn = f1;
						greenIn = f2;
						blueIn = f3;
					}

					if (textformatting.getColor() != null) {
						int j = textformatting.getColor();
						redIn = (float) (j >> 16 & 255) / 255.0F * f;
						greenIn = (float) (j >> 8 & 255) / 255.0F * f;
						blueIn = (float) (j & 255) / 255.0F * f;
					} else if (textformatting == TextFormatting.OBFUSCATED) {
						flag = true;
					} else if (textformatting == TextFormatting.BOLD) {
						boldIn = true;
					} else if (textformatting == TextFormatting.STRIKETHROUGH) {
						flag4 = true;
					} else if (textformatting == TextFormatting.UNDERLINE) {
						flag3 = true;
					} else if (textformatting == TextFormatting.ITALIC) {
						italicIn = true;
					}
				}

				++i;
			} else {
				IGlyph iglyph = fontrenderer.getFont(Style.EMPTY.getFontId()).func_238557_a_(c0);
				TexturedGlyph texturedglyph = flag && c0 != ' ' ? fontrenderer.getFont(Style.EMPTY.getFontId()).obfuscate(iglyph) : fontrenderer.getFont(Style.EMPTY.getFontId()).func_238559_b_(c0);
				if (!(texturedglyph instanceof EmptyGlyph)) {
					float f9 = boldIn ? iglyph.getBoldOffset() : 0.0F;
					float f10 = isShadow ? iglyph.getShadowOffset() : 0.0F;
					IVertexBuilder ivertexbuilder = buffer.getBuffer(texturedglyph.getRenderType(isTransparent));
					float xIn = f4 + f10;
					float yIn = y + f10;
					texturedglyph.render(italicIn, xIn, yIn, matrix, ivertexbuilder, redIn, greenIn, blueIn, alphaIn, packedLight);
					if (boldIn) {
						texturedglyph.render(italicIn, xIn + f9, yIn, matrix, ivertexbuilder, redIn, greenIn, blueIn, alphaIn, packedLight);
					}
				}

				float f15 = iglyph.getAdvance(boldIn);
				float f16 = isShadow ? 1.0F : 0.0F;
				if (flag4) {
					list.add(new Effect(f4 + f16 - 1.0F, y + f16 + 4.5F, f4 + f16 + f15, y + f16 + 4.5F - 1.0F, -0.01F, redIn, greenIn, blueIn, alphaIn));
				}

				if (flag3) {
					list.add(new Effect(f4 + f16 - 1.0F, y + f16 + 9.0F, f4 + f16 + f15, y + f16 + 9.0F - 1.0F, -0.01F, redIn, greenIn, blueIn, alphaIn));
				}

				f4 += f15;
			}
		}

		if (colorBackgroundIn != 0) {
			float f11 = (float) (colorBackgroundIn >> 24 & 255) / 255.0F;
			float f12 = (float) (colorBackgroundIn >> 16 & 255) / 255.0F;
			float f13 = (float) (colorBackgroundIn >> 8 & 255) / 255.0F;
			float f14 = (float) (colorBackgroundIn & 255) / 255.0F;
			list.add(new Effect(x - 1.0F, y + 9.0F, f4 + 1.0F, y - 1.0F, 0.01F, f12, f13, f14, f11));

			if (BORDER) {
				// ADD BORDER

				// horizontal up
				list.add(new Effect(x - 1.0F, y - 0.5F, f4 + 1.0F, y - 1.0F, 0.009F, RED, GREEN, BLUE, 1.0F));
				// horizontal down
				list.add(new Effect(x - 1.0F, y + 9.0F, f4 + 1.0F, y + 8.5F, 0.009F, RED, GREEN, BLUE, 1.0F));
				// vertical up
				list.add(new Effect(x - 1.0F, y + 9.0F, x - 0.5F, y - 1.0F, 0.009F, RED, GREEN, BLUE, 1.0F));
				// vertical down
				list.add(new Effect(f4 + 0.5F, y + 9.0F, f4 + 1.0F, y - 1.0F, 0.009F, RED, GREEN, BLUE, 1.0F));
			}
		}

		if (!list.isEmpty()) {
			TexturedGlyph texturedglyph1 = fontrenderer.getFont(Style.EMPTY.getFontId()).getWhiteGlyph();
			IVertexBuilder ivertexbuilder1 = buffer.getBuffer(texturedglyph1.getRenderType(isTransparent));

			for (Effect texturedglyph$effect : list) {
				renderEffect(texturedglyph1, texturedglyph$effect, matrix, ivertexbuilder1, packedLight);
			}
		}

		return f4;
	}

	public static void renderEffect(TexturedGlyph glyph, Effect effectIn, Matrix4f matrixIn, IVertexBuilder bufferIn, int packedLightIn) {
		bufferIn.pos(matrixIn, effectIn.x0, effectIn.y0, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u0, glyph.v0).lightmap(packedLightIn).endVertex();
		bufferIn.pos(matrixIn, effectIn.x1, effectIn.y0, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u0, glyph.v1).lightmap(packedLightIn).endVertex();
		bufferIn.pos(matrixIn, effectIn.x1, effectIn.y1, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u1, glyph.v1).lightmap(packedLightIn).endVertex();
		bufferIn.pos(matrixIn, effectIn.x0, effectIn.y1, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u1, glyph.v0).lightmap(packedLightIn).endVertex();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Effect {
		protected final float x0;
		protected final float y0;
		protected final float x1;
		protected final float y1;
		protected final float depth;
		protected final float r;
		protected final float g;
		protected final float b;
		protected final float a;

		public Effect(float p_i225923_1_, float p_i225923_2_, float p_i225923_3_, float p_i225923_4_, float p_i225923_5_, float p_i225923_6_, float p_i225923_7_, float p_i225923_8_,
				float p_i225923_9_) {
			this.x0 = p_i225923_1_;
			this.y0 = p_i225923_2_;
			this.x1 = p_i225923_3_;
			this.y1 = p_i225923_4_;
			this.depth = p_i225923_5_;
			this.r = p_i225923_6_;
			this.g = p_i225923_7_;
			this.b = p_i225923_8_;
			this.a = p_i225923_9_;
		}
	}
}
