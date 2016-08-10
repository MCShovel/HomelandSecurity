package com.pvpMinecraft.display;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class VisualizationBox {

	private static final Byte NO_DATA = (byte)0;
	public ArrayList<VisualizationElement> elements = new ArrayList<VisualizationElement>();
	
    public void Draw(Player player, boolean draw)
	{
	    if(!player.isOnline()) return;

	    //send real block information for any remaining elements
        for(int i = 0; i < this.elements.size(); i++)
		{
		    VisualizationElement element = this.elements.get(i);
		    
		    //check player still in world where visualization exists
		    if(i == 0)
		    {
		        if(!player.getWorld().equals(element.location.getWorld())) return;
		    }
		    
		    if (draw) {
		    	player.sendBlockChange(element.location, element.visualizedMaterial, element.visualizedData);
		    }
		    else {
		    	player.sendBlockChange(element.location, element.realMaterial, element.realData);
		    }
		}
	}
	
	//convenience method to build a visualization from a claim
	//visualizationType determines the style (gold blocks, silver, red, diamond, etc)
	public static VisualizationBox FromLocation(World world, BRect box, VisualizationType vtype, Location locality)
	{
		VisualizationBox visualization = new VisualizationBox();
		ArrayList<VisualizationElement> elements = visualization.elements;

		BPoint pt;
		pt = box.NorthWestBottom();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, 1)));

		pt = box.NorthWestTop();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, -1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, 1)));

		pt = box.NorthEastBottom();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(-1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, 1)));

		pt = box.NorthEastTop();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(-1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, -1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, 1)));

		pt = box.SouthWestBottom();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, -1)));

		pt = box.SouthWestTop();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, -1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, -1)));

		pt = box.SouthEastBottom();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(-1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, -1)));

		pt = box.SouthEastTop();
		elements.add(elementFrom(world, vtype.corner, pt));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(-1, 0, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, -1, 0)));
		elements.add(elementFrom(world, vtype.accent, pt.Offset(0, 0, -1)));

		return visualization;
	}
	
	private static VisualizationElement elementFrom(World world, Material m, BPoint minPt) {
		Location location = minPt.ToLocation(world);
		Block save = location.getBlock();
		Material type = save.getType();
		byte data = save.getData();
		return new VisualizationElement(location, m, NO_DATA, type, data);
	}
}
