package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Varun Kumar
 */
public class XMLGenerator {

    public XMLGenerator(ArrayList<String> locations, ArrayList<Session> sessions) {

        int i, j, k, l;
        String FILENAME = "sample.xml";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {

            String day = "null";
            String day_line = "null day";
            String room = "no room";
            String room_line;

            for (k = 1; k <= 3; k++) {

                switch (k) {
                    case 1:
                        day_line = "<day index=\"1\" date=\"2016-3-18\">" + "\n";
                        day = "1";
                        break;
                    case 2:
                        day_line = "<day index=\"2\" date=\"2016-3-19\">" + "\n";
                        day = "2";
                        break;
                    case 3:
                        day_line = "<day index=\"3\" date=\"2016-3-20\">" + "\n";
                        day = "3";
                }

                bw.write(day_line);

                for (i = 0; i < locations.size(); i++) {

                    room = locations.get(i);
                    room_line = "<room name=\"" + room + "\">" + "\n";
                    bw.write(room_line);

                    for (j = 0; j < sessions.size(); j++) {

                        Session s = sessions.get(j);

                        if (s.getRoom().equals(room) && s.getDay().equals(day)) {
                            bw.write("<event id=\"" + s.getId() + "\">" + "\n");
                            bw.write("<start>" + s.getStart() + "</start>" + "\n");
                            bw.write("<duration>" + s.getDuration() + "</duration>" + "\n");
                            bw.write("<room>" + s.getRoom() + "</room>" + "\n");

                            String tit_p = s.getTitle();

                            tit_p = tit_p.replaceAll(",", "");
                            tit_p = tit_p.replaceAll(":", "");
                            tit_p = tit_p.replaceAll("'", "");
                            tit_p = tit_p.replaceAll("-", "");
                            tit_p = tit_p.replaceAll(".", "");
                            tit_p = tit_p.replaceAll(" ", "_");

                            bw.write("<slug>" + tit_p + "</slug>" + "\n");
                            bw.write("<title>" + s.getTitle() + "</title>" + "\n");
                            bw.write("<subtitle/>" + "\n");
                            bw.write("<track>" + s.getTrack() + "</track>" + "\n");
                            bw.write("<type>" + s.getType() + "</type>" + "\n");
                            bw.write("<language/>" + "\n");
                            bw.write("<abstract><p>" + s.getAbst() + "</p></abstract>" + "\n");
                            bw.write("<description><p>" + s.getDescr() + "</p></description>" + "\n");

                            bw.write("<persons>" + "\n");

                            for (l = 0; l < s.getSpeakers().size(); l++) {
                                bw.write("<person id=\"" + s.getSpeakers().get(l).getId() + "\">" + s.getSpeakers().get(l).getName() + "</person>" + "\n");
                            }
                            
                            bw.write("</persons>" + "\n");
                            
                            bw.write("<links>" + "\n");
                            bw.write("<link href=\"" + s.getVideo() +"\">Video</link>" + "\n");
                            bw.write("<link href=\"" + s.getAudio() +"\">Audio</link>" + "\n");
                            bw.write("<link href=\"" + s.getSlides() +"\">Slides</link>" + "\n");
                            bw.write("</links>" + "\n");

                            bw.write("</event>" + "\n");
                        }
                    }

                    bw.write("</room>" + "\n");
                }

                bw.write("</day>" + "\n");
            }
            bw.write("</schedule>");

        } catch (IOException ex) {
            Logger.getLogger(XMLGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
