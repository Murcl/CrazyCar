/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.location;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 *
 * @author Martin Antenreiter
 */
class TrackData {

    static class Data {
        private Long systemtime;
        private double x;
        private double y;
        private double z;
        private double direction;
        private Quaternion quaternion;
    }

    private NavigableMap<Long, TrackData.Data> data = new TreeMap<>();
    
    private TrackData.Data current;
    
    private double minX, minY, maxX, maxY;
    
    TrackData(String filename) throws FileNotFoundException, IOException {
        BufferedReader rd = new BufferedReader(new FileReader(filename));
        String line = null;
        minX = minY = Double.POSITIVE_INFINITY;
        maxX = maxY = Double.NEGATIVE_INFINITY;
        while( (line = rd.readLine()) != null) {
            String[] columns = line.split(",");
            TrackData.Data d = new TrackData.Data();
            d.systemtime = Long.parseLong(columns[0]);            
            d.x = Double.parseDouble(columns[1]);
            d.y = Double.parseDouble(columns[2]);
            d.z = Double.parseDouble(columns[3]);
            d.direction = Double.parseDouble(columns[4]);
            double q1 = Double.parseDouble(columns[5]);
            double q2 = Double.parseDouble(columns[6]);
            double q3 = Double.parseDouble(columns[7]);
            double q4 = Double.parseDouble(columns[8]);
            d.quaternion = new Quaternion(q1, q2, q3, q4);
            data.put(d.systemtime, d);
            minX = Math.min(d.x, minX);
            minY = Math.min(d.y, minY);
            maxX = Math.max(d.x, maxX);
            maxY = Math.max(d.y, maxY);
        }
        rd.close();
        current = data.firstEntry().getValue();
    }
    
    void seekToTime(long time) {
        current = data.floorEntry(time).getValue();
    }

    void getNext() {
        current = data.higherEntry(current.systemtime).getValue();
    }

    double getMinX() {
        return minX;
    }

    double getMinY() {
        return minY;
    }

    double getMaxX() {
        return maxX;
    }

    double getMaxY() {
        return maxY;
    }

    long getTime() {
        return current.systemtime;
    }

    double getX() {
        return current.x;
    }

    double getY() {
        return current.y;
    }

    double getZ() {
        return current.z;
    }

    double getDirection() {
        return current.direction;
    }

    Quaternion getQuaternion() {
        return current.quaternion;
    }

}
