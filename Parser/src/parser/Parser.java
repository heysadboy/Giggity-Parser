package parser;

/**
 *
 * @author Varun Kumar
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Parser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {

        String FILENAME = "sessions";
        String jsonData = "";

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                jsonData += sCurrentLine + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonData);
        JSONArray array = (JSONArray) obj;

        int i, j;

        ArrayList<Session> sessions = new ArrayList<>();

        for (i = 0; i < array.size(); i++) {

            ArrayList<Speaker> speaker_list = new ArrayList<>();

            JSONObject session = (JSONObject) array.get(i);
            String title = session.get("title").toString();
            String id = session.get("id").toString();
            String s_abst = session.get("short_abstract").toString();
            String l_abst = session.get("long_abstract").toString();
            String start_time = session.get("start_time").toString();
            String end_time = session.get("end_time").toString();
            String str = start_time.substring(8, 10);
            String day = "4";

            switch (str) {
                case "18":
                    day = "1";
                    break;
                case "19":
                    day = "2";
                    break;
                case "20":
                    day = "3";
                    break;
            }

            start_time = start_time.substring(11);
            end_time = end_time.substring(11);

            JSONObject session_type = (JSONObject) session.get("session_type");
            String s_type = session_type.get("name").toString();

            JSONObject session_track = (JSONObject) session.get("track");
            String t_name = session_track.get("name").toString();

            JSONObject mic_loc = (JSONObject) session.get("microlocation");
            String loc_name = mic_loc.get("name").toString();

            JSONArray speakers = (JSONArray) session.get("speakers");

            for (j = 0; j < speakers.size(); j++) {
                JSONObject speaker = (JSONObject) speakers.get(j);

                Speaker si = new Speaker();
                si.setName(speaker.get("name").toString());
                si.setId(speaker.get("id").toString());
                speaker_list.add(si);
            }

            String language = null;
            String audio = null;
            String video = null;
            String slides = null;

            if (session.get("language") != null) {
                language = session.get("language").toString();
            }

            if (session.get("audio") != null) {
                audio = session.get("audio").toString();
            }

            if (session.get("video") != null) {
                video = session.get("video").toString();
            }

            if (session.get("slides") != null) {
                slides = session.get("slides").toString();
            }

            Session s = new Session();
            s.setAbst(s_abst);
            s.setDay(day);
            s.setAudio(audio);
            s.setDescr(l_abst);
            s.setDuration(start_time, end_time);
            s.setEvent(id);
            s.setLanguage(language);
            s.setRoom(loc_name);
            s.setSlides(slides);
            s.setSpeakers(speaker_list);
            s.setStart(start_time);
            s.setTrack(t_name);
            s.setType(s_type);
            s.setVideo(video);
            s.setTitle(title);
            s.setId(id);
            sessions.add(s);
        }

        Locations l = new Locations();
        ArrayList<String> locations = l.getLocations();

        XMLGenerator x = new XMLGenerator(locations, sessions);
    }

}
