package cyano.steamadvantage.gui;

import net.minecraft.util.ResourceLocation;
import cyano.poweradvantage.api.simple.SimpleMachineGUI;
import cyano.poweradvantage.math.Integer2D;
import cyano.steamadvantage.SteamAdvantage;
import cyano.steamadvantage.machines.SteamTankTileEntity;

public class SteamTankGUI extends SimpleMachineGUI{

	public SteamTankGUI() {
		super(
				new ResourceLocation(SteamAdvantage.MODID+":textures/gui/container/steam_tank.png"), 
				new Integer2D[0]
		);
	}
	
	
	private static final float maxNeedleSpeed = 0.015625f;
	
	private float oldSteam = 0;
	

	private long lastUpdate = 0;
	/**
	 * Override this method to draw on the GUI window.
	 * <br><br>
	 * This method is invoked when drawing the GUI so that you can draw 
	 * animations and other foreground decorations to the GUI.
	 * @param srcEntity This is the TileEntity (or potentially a LivingEntity) 
	 * for whom we are drawing this interface
	 * @param guiContainer This is the instance of GUIContainer that is drawing 
	 * the GUI. You need to use it to draw on the screen. For example:<br>
	   <pre>
guiContainer.mc.renderEngine.bindTexture(arrowTexture);
guiContainer.drawTexturedModalRect(x+79, y+35, 0, 0, arrowLength, 17); // x, y, textureOffsetX, textureOffsetY, width, height)
	   </pre>
	 * @param x This is the x coordinate (in pixels) from the top-left corner of 
	 * the GUI
	 * @param y This is the y coordinate (in pixels) from the top-left corner of 
	 * the GUI
	 * @param z This is the z coordinate (no units) into the depth of the screen
	 */
	@Override
	public void drawGUIDecorations(Object srcEntity, GUIContainer guiContainer, int x, int y, float  z){
		
		if(srcEntity.getClass() == SteamTankTileEntity.class){
			SteamTankTileEntity target = (SteamTankTileEntity)srcEntity;

			
			float steamPivotX = 88f;
			float steamPivotY = 60f;
			
			float steam = target.getSteamLevel(); 

			long t = System.currentTimeMillis();
			if((t - lastUpdate) < 1000L){
				// slow the needles if it has been less than 1 second since last invocation
				steam = GUIHelper.maxDelta(steam, oldSteam, maxNeedleSpeed);
			}
			oldSteam = steam;
			lastUpdate = t;
			
			GUIHelper.drawNeedle(x+steamPivotX, y+steamPivotY, z, steam);
			
		}
		
	}


	

}
