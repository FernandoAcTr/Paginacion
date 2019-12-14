package controller;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Frame;
import model.Memoria;
import model.Pagina;
import model.Proceso;
import utils.MyUtils;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<Proceso> listviewProcces;

    @FXML
    private ListView<Proceso> listviewSwap;

    @FXML
    private MenuItem mnuMemorySize;

    @FXML
    private MenuItem mnuNumPages;

    @FXML
    private MenuItem mnuNumFrames;

    @FXML
    private TextField txtSize;

    @FXML
    private Button btnEnter;

    @FXML
    private TextField txtPid;

    @FXML
    private Button btnSale;

    @FXML
    private VBox memoryPane;

    Memoria memory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMemory(1024, 4, 4);
        buildMemoryPane();
        initComponents();
    }


    private void initComponents() {
        mnuMemorySize.setOnAction(event -> {
            int size = MyUtils.showInputDialog("Memory Size", "", "Ingresa el size de la Memoria",
                    memory.getSize() + "");
            if (size != memory.getSize()) {
                initMemory(size, memory.getTotalPags(), memory.getPagina(1).getTotalFrames());
                buildMemoryPane();
            }
        });

        mnuNumPages.setOnAction(event -> {
            int numPages = MyUtils.showInputDialog("Numero de Paginas", "", "Ingresa el numero de Paginas",
                    memory.getTotalPags() + "");
            if (numPages != memory.getTotalPags()) {
                initMemory(memory.getSize(), numPages, memory.getPagina(1).getTotalFrames());
                buildMemoryPane();
            }
        });

        mnuNumFrames.setOnAction(event -> {
            int numFrames = MyUtils.showInputDialog("Numero de Frames", "", "Ingresa el numero de Frames por pagina",
                    memory.getPagina(1).getTotalFrames() + "");
            initMemory(memory.getSize(), memory.getTotalPags(), numFrames);
            buildMemoryPane();
        });

        btnEnter.setOnAction(event -> {
            if (txtSize.getText().length() > 0)
                onEnterProccesAction();
        });

        btnSale.setOnAction(event -> {
            if (txtPid.getText().length() > 0)
                onExitProccessAction();
        });

        txtSize.setOnKeyTyped(onKeyTipedAction);
    }

    private void initMemory(int memorySize, int numPages, int numFrames) {
        try {
            memory = new Memoria(numPages, memorySize, numFrames);
        } catch (Exception e) {
            MyUtils.makeDialog("Error", e.getMessage(), "", Alert.AlertType.WARNING).show();
        }

        listviewProcces.getItems().clear();
        listviewSwap.getItems().clear();
    }

    private void buildMemoryPane() {
        memoryPane.getChildren().clear();

        for (int i = 0; i < memory.getTotalPags(); i++) {

            Pagina pagina = memory.getPagina(i + 1);

            HBox pagePane = makePage("P" + pagina.getNumPag());
            addFrames(pagePane, pagina.getTotalFrames());
            memoryPane.getChildren().add(pagePane);

            for (int j = 0; j < pagina.getTotalFrames(); j++) {
                Frame frame = pagina.getFrame(j + 1);
                setFrameValues(pagina.getNumPag(), j + 1, "", frame.getSize() + "", frame.getOcupado() + "");
            }
        }

    }

    /**
     * Crea un HBox con 4 VBox dentro de el. Una pagina consta de 4 secciones: Nombre, PID, Libre y Ocupado.
     *
     * @param pageName
     * @return
     */
    private HBox makePage(String pageName) {
        int minHeigth = 100;
        int minWidth = 125;

        HBox page = new HBox();
        page.setMinWidth(500);
        page.setMinHeight(minHeigth);
        page.setAlignment(Pos.TOP_CENTER);

        VBox colTitle = new VBox();
        colTitle.setMinWidth(minWidth);
        colTitle.setMinHeight(minHeigth);
        colTitle.setAlignment(Pos.CENTER);
        colTitle.getChildren().add(new Label(pageName));

        page.getChildren().add(colTitle);

        for (int i = 0; i < 3; i++) {
            VBox col = new VBox();
            col.setMinWidth(minWidth);
            col.setMinHeight(minHeigth);
            col.setAlignment(Pos.TOP_CENTER);
            col.setStyle("-fx-border-color: lightslategrey");
            page.getChildren().add(col);
        }

        return page;
    }

    /**
     * A un Hbox que representa una pagina, agrega labels que representan los Frames.
     * Una pagina tiene 4 columnas. La primera solo contiene el nombre de la pagina.
     * Solo se agregan labels de la columna 1 a la 3
     *
     * @param page
     * @param numFrames
     */
    private void addFrames(HBox page, int numFrames) {
        int minWidth = 125;
        double minHeigt;

        for (int i = 1; i <= 3; i++) {
            VBox col = (VBox) page.getChildren().get(i);
            minHeigt = col.getMinHeight() / numFrames;

            for (int j = 0; j < numFrames; j++) {
                Label frame = new Label();
                frame.setMinWidth(minWidth);
                frame.setMinHeight(minHeigt);
                frame.setAlignment(Pos.TOP_CENTER);
                frame.setStyle("-fx-border-color: #cacaca");
                col.getChildren().add(frame);
            }
        }
    }

    /**
     * Asigna valores a las label de un frame
     *
     * @param numPage  el numero de la pagina en donde se encuentra el frame. Comenzando desde el 1
     * @param numFrame el numero del Frame comenzando desde el 1
     */
    private void setFrameValues(int numPage, int numFrame, String PID, String libre, String ocupado) {
        HBox page = (HBox) memoryPane.getChildren().get(numPage - 1);

        VBox colPid = (VBox) page.getChildren().get(1);
        VBox colLibre = (VBox) page.getChildren().get(2);
        VBox colOcupado = (VBox) page.getChildren().get(3);

        ((Label) colPid.getChildren().get(numFrame - 1)).setText(PID);
        ((Label) colLibre.getChildren().get(numFrame - 1)).setText(libre);
        ((Label) colOcupado.getChildren().get(numFrame - 1)).setText(ocupado);
    }

    /**
     * Asigna valores a todos frames de una pagina.
     * @param numPag
     */
    private void refreshPag(int numPag){
        Pagina pagina = memory.getPagina(numPag);

        for (int i = 1; i <= pagina.getTotalFrames(); i++) {
            Frame f = pagina.getFrame(i);
            setFrameValues(numPag, i, f.getDescripcion(), f.getFragmetation() + "", f.getOcupado() + "");
        }
    }

    private EventHandler<KeyEvent> onKeyTipedAction = event -> {
        if (Character.isLetter(event.getCharacter().charAt(0)))
            event.consume();
    };

    private void onEnterProccesAction() {
        int size = Integer.valueOf(txtSize.getText());
        Proceso process = new Proceso(size);
        boolean colocado = memory.colocarProceso(process);

        if (colocado) {
            refreshPag(process.getNumPag());
            listviewProcces.getItems().add(process);
        }else
            listviewSwap.setItems(FXCollections.observableArrayList(memory.getSwapping().getProcesos()));
    }

    private void onExitProccessAction(){
        String PID = txtPid.getText();
        Proceso proceso = null;

        for (Proceso p : listviewProcces.getItems())
            if(p.getPID().equalsIgnoreCase(PID)){
                proceso = p;
                break;
            }

        if(proceso != null){
            listviewProcces.getItems().remove(proceso);

            List<Proceso> listSwap = memory.sacarProceso(proceso.getPID(), proceso.getNumPag());
            refreshPag(proceso.getNumPag());

            listviewSwap.setItems(FXCollections.observableArrayList(memory.getSwapping().getProcesos()));

            for (Proceso p : listSwap)
                listviewProcces.getItems().add(p);
        }
    }

}
