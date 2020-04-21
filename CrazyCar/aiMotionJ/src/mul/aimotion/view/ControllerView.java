package mul.aimotion.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mul.aimotion.car.Car;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Martin Antenreiter <martin.antenreiter@unileoben.ac.at>
 */
public class ControllerView extends javax.swing.JFrame implements ChangeListener {

    public static ControllerView buildView() {
        final ControllerView view = new ControllerView();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.setVisible(true);
            }
        });
        return view;
    }

    private Car car;
    private DefaultValueDataset speeddata;
    private DefaultValueDataset rpmdata;
    private final DefaultValueDataset frontdata;
/*    private final JSlider slider1;
    private final JSlider slider2;*/
    private final int SPEED_START = 0;
    private final int SPEED_END = 250;
    private final int SPEED_TICKS = 50;
    private final double RPM_START = 0;
    private final double RPM_END = 4000;
    private final double RPM_TICKS = 500;
    private final double DISTANCE_START = 0;
    private final double DISTANCE_END = 4096;
    private final double DISTANCE_TICKS = 500;
    private final DefaultValueDataset leftdata;
    private final DefaultValueDataset rightdata;

    /*    private double BATTERY_START = 2.0;
    private double BATTERY_END = 4.0;
    private double BATTERY_TICKS = 0.5;*/

    /**
     * Creates new form ControllerView
     */
    public ControllerView() {
        initComponents();
        DialPlot dialplot = createSpeedRPMDial();
        
        frontdata = new DefaultValueDataset(0D);
        DialPlot frontplot = createDistanceDial(frontdata);
        leftdata = new DefaultValueDataset(0D);
        DialPlot leftplot = createDistanceDial(leftdata);
        rightdata = new DefaultValueDataset(0D);
        DialPlot rightplot = createDistanceDial(rightdata);
        
        //ChartFactory.create
        JFreeChart jfreechart = new JFreeChart(dialplot);
        jfreechart.setTitle("aiMotion Crazy Car");
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setPreferredSize(new Dimension(400, 400));
/*        JPanel jpanel = new JPanel(new GridLayout(2, 2));
                                jpanel.add(new JLabel("Outer Needle:"));
                        jpanel.add(new JLabel("Inner Needle:"));
                        slider1 = new JSlider(SPEED_START, SPEED_END);
                        slider1.setMajorTickSpacing(SPEED_TICKS);
                        slider1.setPaintTicks(true);
                        slider1.setPaintLabels(true);
                        slider1.addChangeListener(this);
                        jpanel.add(slider1);
                        //jpanel.add(slider1);
                        slider2 = new JSlider((int)RPM_START, (int)RPM_END);
                        slider2.setMajorTickSpacing((int)RPM_TICKS);
                        slider2.setPaintTicks(true);
                        slider2.setPaintLabels(true);
                        slider2.addChangeListener(this);
                        jpanel.add(slider2);*/
        dialPanel.add(chartpanel);
//        dialPanel.add(jpanel, "South");

        JFreeChart jfreechart2 = new JFreeChart(frontplot);
        jfreechart2.setTitle("Front");
        ChartPanel chartpanel2 = new ChartPanel(jfreechart2);
        chartpanel2.setPreferredSize(new Dimension(200, 200));


        JFreeChart jfreechart3 = new JFreeChart(leftplot);
        jfreechart3.setTitle("Left");
        ChartPanel chartpanel3 = new ChartPanel(jfreechart3);
        chartpanel3.setPreferredSize(new Dimension(200, 200));

        JFreeChart jfreechart4 = new JFreeChart(rightplot);
        jfreechart4.setTitle("Right");
        ChartPanel chartpanel4 = new ChartPanel(jfreechart4);
        chartpanel4.setPreferredSize(new Dimension(200, 200));
        
        dialPanel.add(chartpanel);
        frontDistancePanel.add(chartpanel2);
        leftDistancePanel.add(chartpanel3);
        rightDistancePanel.add(chartpanel4);
        pack();
    }

    private DialPlot createSpeedRPMDial() {
        speeddata = new DefaultValueDataset(10D);
        rpmdata = new DefaultValueDataset(50D);
        DialPlot dialplot = new DialPlot();
        dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
        dialplot.setDataset(0, speeddata);
        dialplot.setDataset(1, rpmdata);
        StandardDialFrame standarddialframe = new StandardDialFrame();
        standarddialframe.setBackgroundPaint(Color.lightGray);
        standarddialframe.setForegroundPaint(Color.darkGray);
        dialplot.setDialFrame(standarddialframe);
        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(100, 100, 100));
        DialBackground dialbackground = new DialBackground(gradientpaint);
        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);
        DialTextAnnotation dialtextannotation = new DialTextAnnotation("Speed");
        dialtextannotation.setFont(new Font("Dialog", 1, 14));
        dialtextannotation.setRadius(0.69999999999999996D);
        dialplot.addLayer(dialtextannotation);
        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialvalueindicator.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator.setOutlinePaint(Color.darkGray);
        dialvalueindicator.setRadius(0.59999999999999998D);
        dialvalueindicator.setAngle(-103D);
        dialplot.addLayer(dialvalueindicator);
        DialValueIndicator dialvalueindicator1 = new DialValueIndicator(1);
        dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator1.setOutlinePaint(Color.red);
        dialvalueindicator1.setRadius(0.59999999999999998D);
        dialvalueindicator1.setAngle(-77D);
        dialplot.addLayer(dialvalueindicator1);
        StandardDialScale standarddialscale = new StandardDialScale(SPEED_START, SPEED_END, -120D, -300D, 20D, 4);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.14999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
        dialplot.addScale(0, standarddialscale);
        StandardDialScale standarddialscale1 = new StandardDialScale(RPM_START, RPM_END, -120D, -300D, 500D, 4);
        standarddialscale1.setTickRadius(0.5D);
        standarddialscale1.setTickLabelOffset(0.14999999999999999D);
        standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 10));
        standarddialscale1.setMajorTickPaint(Color.red);
        standarddialscale1.setMinorTickPaint(Color.red);
        dialplot.addScale(1, standarddialscale1);
        dialplot.mapDatasetToScale(1, 1);
        StandardDialRange standarddialrange = new StandardDialRange(RPM_END - 300, RPM_END, Color.blue);
        standarddialrange.setScaleIndex(1);
        standarddialrange.setInnerRadius(0.58999999999999997D);
        standarddialrange.setOuterRadius(0.58999999999999997D);
        dialplot.addLayer(standarddialrange);
        org.jfree.chart.plot.dial.DialPointer.Pin pin = new org.jfree.chart.plot.dial.DialPointer.Pin(1);
        pin.setRadius(0.55000000000000004D);
        dialplot.addPointer(pin);
        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
        dialplot.addPointer(pointer);
        DialCap dialcap = new DialCap();
        dialcap.setRadius(0.10000000000000001D);
        dialplot.setCap(dialcap);
        return dialplot;
    }

    private DialPlot createDistanceDial(DefaultValueDataset dataset) {
        DialPlot dialplot = new DialPlot();
        dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
        dialplot.setDataset(0, dataset);
        StandardDialFrame standarddialframe = new StandardDialFrame();
        standarddialframe.setBackgroundPaint(Color.lightGray);
        standarddialframe.setForegroundPaint(Color.darkGray);
        dialplot.setDialFrame(standarddialframe);
        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(100, 100, 100));
        DialBackground dialbackground = new DialBackground(gradientpaint);
        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);
        DialTextAnnotation dialtextannotation = new DialTextAnnotation("Distance");
        dialtextannotation.setFont(new Font("Dialog", 1, 14));
        dialtextannotation.setRadius(0.69999999999999996D);
        dialplot.addLayer(dialtextannotation);
/*        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialvalueindicator.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator.setOutlinePaint(Color.darkGray);
        dialvalueindicator.setRadius(0.59999999999999998D);
        dialvalueindicator.setAngle(-103D);
        dialplot.addLayer(dialvalueindicator);*/
/*        DialValueIndicator dialvalueindicator1 = new DialValueIndicator(1);
        dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator1.setOutlinePaint(Color.red);
        dialvalueindicator1.setRadius(0.59999999999999998D);
        dialvalueindicator1.setAngle(-77D);
        dialplot.addLayer(dialvalueindicator1);*/
        StandardDialScale standarddialscale = new StandardDialScale(DISTANCE_START, DISTANCE_END, -120D, -300D, DISTANCE_TICKS, 4);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.24999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
        dialplot.addScale(0, standarddialscale);

        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
        dialplot.addPointer(pointer);
        DialCap dialcap = new DialCap();
        dialcap.setRadius(0.10000000000000001D);
        dialplot.setCap(dialcap);

        return dialplot;
    }

    @Override
    public void stateChanged(ChangeEvent changeevent) {
        /*        speeddata.setValue(new Integer(slider1.getValue()));
        rpmdata.setValue(new Integer(slider2.getValue()));*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        dialPanel = new javax.swing.JPanel();
        leftDistancePanel = new javax.swing.JPanel();
        frontDistancePanel = new javax.swing.JPanel();
        rightDistancePanel = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dialPanel.setPreferredSize(new java.awt.Dimension(400, 400));
        dialPanel.setLayout(new java.awt.BorderLayout());

        leftDistancePanel.setLayout(new java.awt.BorderLayout());

        frontDistancePanel.setLayout(new java.awt.BorderLayout());

        rightDistancePanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dialPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(leftDistancePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(frontDistancePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightDistancePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dialPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(leftDistancePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rightDistancePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(frontDistancePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void updateSpeed(int speed) {
        speeddata.setValue(speed);
    }

    public void updateRPM(double rpm) {
        rpmdata.setValue(rpm);
    }

    public void updateDistanceFront(int distance) {
        frontdata.setValue(distance);
    }
    public void updateDistanceLeft(int distance) {
        leftdata.setValue(distance);
    }
    public void updateDistanceRight(int distance) {
        rightdata.setValue(distance);
    }

    public void setCar(Car car) {
        this.car = car;
        new Thread() {
            @Override
            public void run() {
                int speed = car.getSpeed();
                int rpm = car.getRpm();
                int front = car.getDistFront();
                int left = car.getDistLeft();
                int right = car.getDistRight();
                updateSpeed(speed);
                updateRPM(rpm);
                updateDistanceFront(front);
                updateDistanceLeft(left);
                updateDistanceRight(right);
                while (car.isConnected()) {
                    int newspeed = car.getSpeed();
                    if (newspeed != speed) {
                        speed = newspeed;
                        updateSpeed(newspeed);
                    }
                    int newrpm = car.getRpm();
                    if (newrpm != rpm) {
                        rpm = newrpm;
                        updateRPM(rpm);
                    }
                    int newfront = car.getDistFront();
                    if(newfront != front) {
                        front = newfront;
                        updateDistanceFront(front);
                    }
                    int distance = car.getDistLeft();
                    if(distance != left) {
                        left = distance;
                        updateDistanceLeft(left);
                    }
                    distance = car.getDistRight();
                    if(distance != right) {
                        right = distance;
                        updateDistanceRight(right);
                    }
                }
            }

        }.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ControllerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControllerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControllerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControllerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControllerView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dialPanel;
    private javax.swing.JPanel frontDistancePanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel leftDistancePanel;
    private javax.swing.JPanel rightDistancePanel;
    // End of variables declaration//GEN-END:variables

}
