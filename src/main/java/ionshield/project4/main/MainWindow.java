package ionshield.project4.main;

import ionshield.project4.graphics.GraphDisplay;
import ionshield.project4.graphics.GraphDisplay3D;
import ionshield.project4.math.InterpolationException;
import ionshield.project4.math.Interpolator3D;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.colors.colormaps.ColorMapRainbowNoBorder;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.maths.Rectangle;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.FixedDecimalTickRenderer;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.mariuszgromada.math.mxparser.Function;

import javax.swing.*;

public class MainWindow {
    private JPanel rootPanel;
    private JTextArea log;
    private JButton calculateButton;
    private JTextField t0Field;
    private JTextField tInField;
    private JTextField tTField;
    private JTextField daField;
    private JTextField dbField;
    private JTextField lengthField;
    private JTextField diameterField;
    private JTextField uField;
    private JTextField tMaxField;
    
    public static final String TITLE = "Project-4";
    
    private MainWindow() {
        initComponents();
    }
    
    private void initComponents() {
        calculateButton.addActionListener(e -> calculate());
    }
    
    
    
    private void calculate() {
        try {
            log.setText("");
    
            Function t0 = new Function(t0Field.getText());
            Function tIn = new Function(tInField.getText());
            if (!(t0.checkSyntax() && tIn.checkSyntax())) {
                throw new NumberFormatException("Invalid function syntax");
            }
            
            double tT = Double.parseDouble(tTField.getText());
            double da = Double.parseDouble(daField.getText());
            double db = Double.parseDouble(dbField.getText());
            double length = Double.parseDouble(lengthField.getText());
            double diameter = Double.parseDouble(diameterField.getText());
            double u = Double.parseDouble(uField.getText());
            double tMax = Double.parseDouble(tMaxField.getText());
            
            HeatExchanger exchanger = new HeatExchanger();
            exchanger.settT(tT);
            exchanger.setDa(da);
            exchanger.setDb(db);
            exchanger.setLength(length);
            exchanger.setDiameter(diameter);
            exchanger.setU(u);
            exchanger.setTimeMax(tMax);
    
            Interpolator3D i3d = exchanger.calculate(t0::calculate, tIn::calculate);
    
            GraphDisplay3D ui = new GraphDisplay3D(i3d);
            AnalysisLauncher.open(ui, new Rectangle(600, 600));

            
        }
        catch (NumberFormatException e) {
            log.append("\nInvalid input format");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        MainWindow gui = new MainWindow();
        frame.setContentPane(gui.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
