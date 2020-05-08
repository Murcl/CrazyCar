package com.sweng.drive;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import lombok.Getter;

@Getter
public class Sensor extends Rectangle {

    Rectangle[] sensoren = new Rectangle[50];
    int minIndex = sensoren.length;
    int dir = 1;
    CarImpl c;
    boolean allTransparent = true;

    public Sensor(double range,CarImpl c) {
        super(50,0,range,20);
        this.c = c;
        setFill(Color.TRANSPARENT);
        setStroke(Color.BLACK);
        getStrokeDashArray().addAll(3.0,7.0,3.0,7.0);
        double rangePerSensor = range/sensoren.length;
        for (int i = 0;i<sensoren.length;i++) {
            Rectangle r = new Rectangle(50,0,rangePerSensor*i,20);
            sensoren[i] = r;
            r.setFill(Color.TRANSPARENT);
            r.setStroke(Color.TRANSPARENT);
        }

    }

    /**
     * 0 ... links
     * 1 ... front
     * 2 ... right
     * @param range
     * @param dir
     */
    public Sensor(double range, int dir, CarImpl c) {
        super(50,0,range,20);
        this.c = c;
        this.dir = dir;
        int angle = 0;
        if (dir == 0)
            angle = 45;
        if (dir == 2)
            angle = -45;
        Rotate rot = new Rotate(angle);
        rot.setPivotX(40);
        rot.setPivotY(10);
        setFill(Color.TRANSPARENT);
        setStroke(Color.BLACK);
        getStrokeDashArray().addAll(3.0,7.0,3.0,7.0);
        getTransforms().add(rot);
        double rangePerSensor = range/sensoren.length;
        for (int i = 0;i<sensoren.length;i++) {
            Rectangle r = new Rectangle(50,0,rangePerSensor*i,20);
            sensoren[i] = r;
            r.setFill(Color.TRANSPARENT);
            r.setStroke(Color.TRANSPARENT);
            r.getStrokeDashArray().addAll(3.0,7.0,3.0,7.0);
            r.getTransforms().add(rot);
        }

    }

    public int intersectsAnyObstacle(ObservableList<Node> input) {
        Rectangle min = null;
        outer: for (Node n:input) {
            if (n instanceof Obstacle) {
                setStroke(Color.BLACK);
                Obstacle o = (Obstacle) n;
                if (!allTransparent) {
                    for (Rectangle r : sensoren)
                        r.setStroke(Color.TRANSPARENT);
                    allTransparent=true;
                }
                if (Shape.intersect(this,o).getBoundsInParent().getWidth()<=0)
                    continue;
                for (Rectangle r:sensoren) {
                    r.setStroke(Color.TRANSPARENT);
                    if (Shape.intersect(r,o).getBoundsInParent().getWidth() > 0) {
                        if (min != null && r.getWidth() > min.getWidth()) {
                            continue outer;
                        }
                        min = r;
                        continue outer;
                    }
                }
            }
        }
        if (min == null)
            return 150;
        min.setStroke(Color.RED);
        allTransparent = false;
        setStroke(Color.TRANSPARENT);
        return (int) min.getWidth();
    }
}
