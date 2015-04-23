package com.frostbyte.ludumdare.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.frostbyte.ludumdare.blocks.Block;
import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.menus.Menu;

public class MapLoader {
	String mapLocation;
	String[][] blockString;
	String[][] overString;
	Map map;
	Menu menu;

	public MapLoader(Menu menu, String mapLocation) {
		this.mapLocation = mapLocation;
		this.menu = menu;

		load();
	}

	public Map getMap() {
		return map;
	}

	public void load() {
		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/MAPS/" + mapLocation + "_BACKGROUND" + ".txt"), "UTF-8"));

			int lines = 0;
			int width = -1;
			while (fileReader.readLine() != null) {
				if (width == -1) {
					width = fileReader.readLine().length();
				}

				lines++;
			}

			fileReader.close();
			fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/MAPS/" + mapLocation + "_BACKGROUND" + ".txt"), "UTF-8"));

			blockString = new String[width + 2][lines + 2];

			if (fileReader != null) {
				String data;
				int y = 0;
				while ((data = fileReader.readLine()) != null) {
					int x = 0;

					for (String string : data.split(",")) {
						blockString[x][y] = string;
						x++;
					}

					y++;
				}

				fileReader.close();
			}

			fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/MAPS/" + mapLocation + "_FOREGROUND" + ".txt"), "UTF-8"));
			overString = new String[width + 2][lines + 2];

			String data;
			int y = 0;
			while ((data = fileReader.readLine()) != null) {
				int x = 0;

				for (String string : data.split(",")) {
					overString[x][y] = string;
					x++;
				}

				y++;
			}

			fileReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		loadMap();
	}

	public void loadMap() {
		if (mapLocation.equalsIgnoreCase("MAP_2")) {
			map = new Map(menu, 101, blockString[0].length);
		} else {
			map = new Map(menu, blockString[0].length, blockString.length);
		}
		
		for (int x = 0; x < blockString.length; x++) {
			for (int y = 0; y < blockString[0].length; y++) {
				if (map.getBlocks().length > x) {
					if (blockString[x][y] != null) {
						map.getBlocks()[x][y] = new Block(Material.getMaterialFromCode(Integer.parseInt(blockString[x][y]) - 1));
					}
				}
			}
		}

		for (int x = 0; x < overString.length; x++) {
			for (int y = 0; y < overString[0].length; y++) {
				if (map.getOverlay().length > x) {
					if (overString[x][y] != null) {
						map.getOverlay()[x][y] = new Block(Material.getMaterialFromCode(Integer.parseInt(overString[x][y]) - 1));
					}
				}
			}
		}
	}
}
