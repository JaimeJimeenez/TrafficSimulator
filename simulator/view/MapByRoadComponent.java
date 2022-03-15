package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private static final Color _BG_COLOR = Color.white;
	private static final Color _JUNCTION_COLOR = Color.blue;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.green;
	private static final Color _RED_LIGHT_COLOR = Color.red;
	private static final int _JRADIUS = 10;
	
	
	private RoadMap _map;
	private static Image _car;
	
	MapByRoadComponent(Controller ctrl) {
		initGui();
		ctrl.addObserver(this);
	}
	
	private void initGui() {
		_car = loadImage("car.png");
		setPreferredSize(new Dimension(300, 200));
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if (_map == null || _map.getJunctions().isEmpty()) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		drawRoads(g);
		drawRoadsId(g);
		drawVehicles(g);
		drawJunctions(g);
		drawWeather(g);
		drawCO2(g);
	}
	
	private void drawRoads(Graphics g) {
		int i = 0;
		for (int j = 0; j < _map.getRoads().size(); j++) {
			
			// The road goes from (x1, y1) to (x2, y2)
			int x1 = 50;
			int y1 = (i + 1)*50;
			int x2 = getWidth() - 100;
			int y2 = (i + 1)*50;
			
			// Draw line from (x1, y1) to (x2, y2) with arrow of color arrowColor and line of
			// color roadColor. The size of the arrow is 15px length 5 px width
			
			g.setColor(Color.black);
			g.drawLine(x1, y1, x2, y2);
			i++;
		}
	}
	
	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) {
			int i = 0;
			int y = 0;
			
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				
				// The calculation below compute the coordinate (vX, vY) of the vehicle on the
				// corresponding road. It is calculated relativly to the length of the road, and
				// the location on the vehicle
				
				Road r = v.getRoad();
				int x1 = 50;
				int x2 = getWidth() - 120;
				int vX = (x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength())));
				
				for (Road roadAux : _map.getRoads())
					if (roadAux.getId() != r.getId())
						i++;
					else
						y = i;
				int vY = (y + 1) * 50;
				
				// Choose a color for the vehicle's label and background, depending on it's 
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));
				
				// Draw an image of a car and it's identifier
				g.drawImage(_car, vX, vY - 10, 16, 16, this);
				g.drawString(v.getId(), vX, vY - 10);
			}
		}
	}
	
	private void drawJunctions(Graphics g) {
		int i = 0;
		
		for (Road r : _map.getRoads()) {
			Junction src = r.getSrc();
			Junction dest = r.getDest();
			
			// (x, y) are the coordinates of the junction
			int x1 = 50;
			int y1 = (i + 1) * 50;
			int x2 = getWidth() - 100;
			int y2 = (i + 1) * 50;
			
			// Color junction destine
			Color junctionColor = _RED_LIGHT_COLOR;
			int idx = dest.getGreenLightIndex();
			if (idx != -1 && r.equals(dest.getInRoads().get(idx))) 
				junctionColor = _GREEN_LIGHT_COLOR;
			
			// Draw a circle in center at (x1, y1) with radius _JRADIUS junction origin
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y1 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		
			// Draw a circle with center at (x2, y2) with radius _JRADIUS junctions destine
			g.setColor(junctionColor);
			g.fillOval(x2 - _JRADIUS / 2, y1 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			// Draw the junction's identifier at (x, y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(src.getId(), x1 - 2, y1 - 7);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(dest.getId(), x2 - 2, y2 - 7);
			i++;
		}
	}
	
	private void drawRoadsId(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {
			g.drawString(r.getId(), 25, ((i + 1) * 50) + 4);
			i++;
		}
	}
	
	private void drawWeather(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {
			switch(r.getWeather()) {
				case CLOUDY:
					g.drawImage(loadImage("cloud.png"),  getWidth()- 85, ((i + 1) * 50) - 16, 32, 32, this);
					break;
				case RAINY:
					g.drawImage(loadImage("rain.png"),  getWidth()- 85, ((i + 1) * 50) - 16, 32, 32, this);
					break;
				case STORM:
					g.drawImage(loadImage("storm.png"),  getWidth()- 85, ((i + 1) * 50) - 16, 32, 32, this);
					break;
				case SUNNY:
					g.drawImage(loadImage("sun.png"),  getWidth()- 85, ((i + 1) * 50) - 16, 32, 32, this);
					break;
				case WINDY:
					g.drawImage(loadImage("wind.png"),  getWidth()- 85, ((i + 1) * 50) - 16, 32, 32, this);
					break;
			}
			i++;
		}
	}
	
	private void drawCO2(Graphics g) {
		int i = 0;
		
		for (Road r : _map.getRoads()) {
			int c = (int) Math.floor(Math.min((double) r.getTotalCO2() / (1.0 + (double) r.getContLimit()), 1.0) / 0.19);
			switch(c) {
				case 0:
					g.drawImage(loadImage("cont_0.png"), getWidth() - 48,((i + 1) * 50) - 16, 32, 32, this);
					break;
				case 1:
					g.drawImage(loadImage("cont_1.png"), getWidth() - 48,((i + 1) * 50) - 16, 32, 32, this);
					break;
				case 2:
					g.drawImage(loadImage("cont_2.png"), getWidth()- 48,((i + 1) * 50) - 16, 32, 32, this);
					break;
				case 3:
					g.drawImage(loadImage("cont_3.png"), getWidth()- 48,((i + 1) * 50) - 16, 32, 32, this);
					break;
				case 4:
					g.drawImage(loadImage("cont_4.png"), getWidth()- 48,((i + 1) * 50) - 16, 32, 32, this);
					break;
				default:
					g.drawImage(loadImage("cont_5.png"), getWidth()- 48,((i + 1) * 50) - 16, 32, 32, this);
					break;
			}
			i++;
		}
	}
	
	private void updatePrefferedSize() {
		int maxW = 455;
		int maxH = 350;
		
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		
		maxW += 20;
		maxH += 20;
		setPreferredSize(new Dimension(maxW, maxH));
		setSize(new Dimension(maxW, maxH));
	}
	
	private Image loadImage(String path) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + path));
		} catch (IOException e) {
			
		}
		return i;
	}
	
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

}
