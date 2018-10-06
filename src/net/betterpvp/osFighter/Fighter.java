package net.betterpvp.osFighter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import net.betterpvp.osFighter.antiban.AntiBanManager;
import net.betterpvp.osFighter.data.SessionData;
import net.betterpvp.osFighter.gui.GUI;
import net.betterpvp.osFighter.managers.Paint;
import net.betterpvp.osFighter.states.Fighting;
import net.betterpvp.osFighter.states.Looting;
import net.betterpvp.osFighter.states.ScriptState;


@ScriptManifest(author = "Tom", info = "osFighter - AIO Fighting Script", name = "osFighter", version = 0.1, logo = "http://betterpvp.net/osFighter/osfighter_logo.png")
public class Fighter extends Script{

	public boolean started;
	private SessionData data;
	public Paint listener;
	private List<ScriptState> states = new ArrayList<>();
	private GUI gui;


	@Override
	public void onStart(){



	

		new AntiBanManager(this);
	

		listener = new Paint(this);
		getBot().addMouseListener(listener);
		getBot().addPainter(listener);
		getBot().addMessageListener(listener);

		data = new SessionData();
		
		log("Version: " + getVersion());
		if(getParameters() != null && !getParameters().equalsIgnoreCase("none")){

			loadConfig(getParameters());
			return;

		}

		SwingUtilities.invokeLater(() -> gui = new GUI(this));

		data.setLastAntiban(System.currentTimeMillis());
		data.setAntibanGap();

		log("----");
		log("Like the script? Leave a review here:");
		log("https://osbot.org/forum/store/product/662-osminer/");
		log("----");

	}

	@Override
	public void onExit(){
		

		if(getGUI() != null){
			getGUI().dispose();
		}

		log("----");
		log("Like the script? Leave a review here:");
		log("https://osbot.org/forum/store/product/662-osminer/");
		log("----");
	}

	@Override
	public int onLoop() throws InterruptedException {
		if(hasStarted()){

			for(ScriptState s : states){
				if(s.execute(this)) {
				
					return random(data.getMinLoopTime(), data.getMaxLoopTime());
				}			
			}
		}
		return random(150, 250);
	}

	public void start(){

		
	
		//states.add(new AntiPattern());
		states.add(new Looting());
		states.add(new Fighting());

		started = true;
	}

	public boolean hasStarted(){
		return started;
	}



	public void setSessionData(SessionData s){
		this.data = s;
	}

	
	public GUI getGUI(){
		return gui;
	}
	
	


	public SessionData getSessionData(){
		return data;
	}



	private void loadConfig(String config){
		/*
		try{
			
			SessionData sesh = getSessionData();
			FileInputStream fs = new FileInputStream(getDirectoryData() + "osFighter/" + config + ".properties");
			Properties  prop = new Properties();
			prop.load(fs);


			sesh.setLocation(LocationManager.getInstance().getLocationByName(prop.getProperty("Location")));
			sesh.setBanking(prop.getProperty("Mode").equalsIgnoreCase("Bank"));
			sesh.setUsingCustomRocks(Boolean.valueOf(prop.getProperty("Custom Rocks")));



			String[] rocks = prop.getProperty("Rock List").split(";");
			if(rocks.length > 0){
				for(String r : rocks){
					if(r.equalsIgnoreCase("")) continue;
					String[] r2 = r.split(", ");

					sesh.getRocks().add(new CachedObject(new Position(Integer.valueOf(r2[1]),
							Integer.valueOf(r2[2]), Integer.valueOf(r2[3])), Rocks.valueOf(r2[0])));


				}
			}


			if(!sesh.isUsingCustomRocks()){
				sesh.setRock(Rocks.valueOf(prop.getProperty("Ore")));
			}


			sesh.setDropMethod(prop.getProperty("Drop Method"));

			sesh.setFinishMiningLevel(Integer.valueOf(prop.getProperty("Stop Level")));
			sesh.setBank(Bank.valueOf(prop.getProperty("Bank").toUpperCase().replace(" ", "_")));
			sesh.setMaxLoopTime(Integer.valueOf(prop.getProperty("Max Loop Speed")));
			sesh.setMinLoopTime(Integer.valueOf(prop.getProperty("Min Loop Speed")));
			sesh.setFatigue(Boolean.valueOf(prop.getProperty("Fatigue")));

			sesh.setHoverNextOre(Boolean.valueOf(prop.getProperty("Hover Next Ore")));

			sesh.setShiftDropping(Boolean.valueOf(prop.getProperty("Shift Dropping")));
			sesh.setWorldHop(Boolean.valueOf(prop.getProperty("World Hop")));
			sesh.setHopType(HopData.valueOf(prop.getProperty("World Hop Type")));


			String antibans = prop.getProperty("Antiban");
			if(!antibans.equals("")) {
				String[] a2 = antibans.split(";");
				for(String s : a2){
					AntiBan m = AntiBanManager.getModule(s);
					if(m != null) {
						m.setEnabled(true);
					}

				}
			}

			log("Config loaded");

			fs.close();
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
*/
		start();
		
	}


}
