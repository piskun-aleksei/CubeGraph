package control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button computeButton;

    @FXML
    private BarChart chart;


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }

    public void setActions(Stage stage) {
        final FileChooser fileChooser = new FileChooser();

        computeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chart.getData().clear();

                try {
                    File file = fileChooser.showOpenDialog(stage);
                    FileReader fileReader = null;

                    if (file != null) {
                        fileReader = new FileReader(file);
                    }

                    if (fileReader != null) {
                        BufferedReader bufferedReader =
                                new BufferedReader(fileReader);
                        String line = null;
                        List<Date> dates = new ArrayList<>();
                        Map<Date, String> bestTimes = new HashMap<>();
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] allLines = line.split(" ");
                            String dateString = allLines[0];
                            String[] times = allLines[2].split(";");
                            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                            Date date = formatter.parse(dateString);
                            dates.add(date);
                            bestTimes.put(date, sortTimes(times));
                        }
                        dates = sortDates(dates);
                        bufferedReader.close();
                        // Barchart values on top - http://stackoverflow.com/questions/15237192/how-to-display-bar-value-on-top-of-bar-javafx
                        //TODO - correct namings below
                        XYChart.Series series = new XYChart.Series();
                        series.setName("Graph");
                        for (Integer i = 0; i < dates.size(); i++) {
                            System.out.println(bestTimes.get(dates.get(i)));
                            series.getData().add(new XYChart.Data(dates.get(i).toString(), getSeconds(bestTimes.get(dates.get(i)))));
                        }
                        chart.getData().add(series);
                    }
                } catch (IOException | ParseException e) {
                    System.out.println(e);
                }

            }
        });

    }

    private List<Date> sortDates(List<Date> dates) {
        Collections.sort(dates, new Comparator<Date>() {
            @Override
            public int compare(Date lhs, Date rhs) {
                if (lhs.getTime() < rhs.getTime()) {
                    return -1;
                } else if (lhs.getTime() == rhs.getTime()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return dates;
    }

    private String sortTimes(String[] times) {
        if (times.length == 0) {
            return null;
        }
        String bestTime = times[0];

        if (times.length == 1) {
            return bestTime;
        }
        for (int i = 1; i < times.length; i++) {
            String[] current = times[i].split(":");
            String[] best = bestTime.split(":");
            if ((Integer.parseInt(current[0]) < Integer.parseInt(best[0])) ||
                    ((Integer.parseInt(current[0]) == Integer.parseInt(best[0]) && (Integer.parseInt(current[1]) < Integer.parseInt(best[1])))) ||
                    ((Integer.parseInt(current[0]) == Integer.parseInt(best[0]) && (Integer.parseInt(current[1]) == Integer.parseInt(best[1]))) && (
                            Integer.parseInt(current[2]) < Integer.parseInt(best[2])))) {
                bestTime = times[i];
            }
        }
        return bestTime;
    }

    private int getSeconds(String time) {
        String[] splits = time.split(":");
        return Integer.parseInt(splits[0]) * 60 + Integer.parseInt(splits[1]);
    }
}
