package client.views;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import client.LocaleManager;
import common.data.Coordinates;
import common.data.Organization;

import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Представление графического отображения организаций.
 */
public class OrganizationsGraphicView {
    private static final int MAX_RGB = 256;
    private static final double EC_LO = -50;
    private static final double EC_HI = 50;
    private static final double MIN_RADIUS = 20;
    private static final double MAX_RADIUS = 40;
    private static final double GRAPH_FONT_SIZE = 15;
    private static final double GRAPH_TEXT_OUTLINE_STROKE = 5;
    private static final double TRANSITION_MILLIS = 700;
    private final Image backgroundImage = new Image("/map.png");
    private final ObjectProperty<Organization> selectedOrganizationProperty = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Organization> hoveredOrganizationProperty = new SimpleObjectProperty<>(null);

    private final Canvas canvas;
    private final ScrollPane view;
    private final Pane clickableShapes = new Pane();
    private GraphicOrganization lastSelectedGraphicOrganization;

    private final Set<GraphicOrganization> graphicOrganizations = new HashSet<>();

    private final SetChangeListener<Organization> listener = change -> {
        if (change.wasAdded()) {
            graphicOrganizations.add(new GraphicOrganization(change.getElementAdded()));
        }

        if (change.wasRemoved()) {
            for (Iterator<GraphicOrganization> it = graphicOrganizations.iterator(); it.hasNext();) {
                GraphicOrganization graphicOrganization = it.next();
                if (graphicOrganization.getOrganization().equals(change.getElementRemoved())) {
                    graphicOrganization.removeFromPane();
                    it.remove();
                }
            }
        }
        redrawOnCanvas();
    };

    /**
     * Создает объект OrganizationsGraphicView с заданным набором организаций.
     * @param organizationSet набор организаций
     */
    public OrganizationsGraphicView(ObservableSet<Organization> organizationSet) {
        canvas = new Canvas(backgroundImage.getWidth(), backgroundImage.getHeight());
        canvas.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
        canvas.getGraphicsContext2D().setTextBaseline(VPos.CENTER);
        clickableShapes.setMinSize(canvas.getWidth(), canvas.getHeight());
        clickableShapes.setMaxSize(canvas.getWidth(), canvas.getHeight());
        organizationSet.addListener(listener);
        selectedOrganizationProperty.addListener((o, oldV, newV) -> redrawOnCanvas());
        hoveredOrganizationProperty.addListener((o, oldV, newV) -> redrawOnCanvas());

        LocaleManager.localeProperty().addListener((o, oldV, newV) -> redrawOnCanvas());

        setupCoordsText();

        view = new ScrollPane();
        view.setContent(new StackPane(canvas, clickableShapes));
        view.setHvalue(0.5); // Перемещает пользовательское представление в середину
        view.setVvalue(0.5);
        view.layout();
        redrawOnCanvas();
    }

    /**
     * Настраивает текст с координатами мыши.
     */
    private void setupCoordsText() {
        Text coordsText = new Text();
        coordsText.setTextAlignment(TextAlignment.LEFT);
        coordsText.setTextOrigin(VPos.BOTTOM);

        clickableShapes.getChildren().add(coordsText);
        clickableShapes.setOnMousePressed(e -> {
            if (getSelectedOrganization() != null) {
                setSelectedOrganization(null);
            }
        });

        clickableShapes.setOnMouseMoved(e -> {
            coordsText.setX(e.getX());
            coordsText.setY(e.getY());
            coordsText.setText(String.format("X: %.1f\nY: %.1f", (e.getX() - canvas.getWidth() / 2), (e.getY() - canvas.getHeight() / 2)));
        });
    }

    /**
     * Возвращает представление графического отображения.
     * @return представление графического отображения
     */
    public Node getView() {
        return view;
    }

    /**
     * Возвращает свойство выбранной организации.
     * @return свойство выбранной организации
     */
    public ObjectProperty<Organization> selectedOrganizationProperty() {
        return selectedOrganizationProperty;
    }

    /**
     * Возвращает выбранную организацию.
     * @return выбранная организация
     */
    public Organization getSelectedOrganization() {
        return selectedOrganizationProperty.get();
    }

    /**
     * Возвращает наведенную организацию.
     * @return наведенная организация
     */
    public Organization getHoveredOrganization() {
        return hoveredOrganizationProperty.get();
    }

    /**
     * Устанавливает выбранную организацию.
     * @param organization выбранная организация
     */
    public void setSelectedOrganization(Organization organization) {
        selectedOrganizationProperty.set(organization);
    }

    /**
     * Устанавливает наведенную организацию.
     * @param organization наведенная организация
     */
    public void setHoveredOrganization(Organization organization) {
        hoveredOrganizationProperty.set(organization);
    }

    /**
     * Перерисовывает графическое отображение на холсте.
     */
    private void redrawOnCanvas() {
        canvas.getGraphicsContext2D().drawImage(backgroundImage, 0, 0);
        graphicOrganizations.forEach(graphicOrg -> {
            graphicOrg.draw();
            graphicOrg.drawText();
        });

        if (lastSelectedGraphicOrganization != null) {
            lastSelectedGraphicOrganization.draw();
            lastSelectedGraphicOrganization.drawText();
        }
    }

    /**
     * Создает круговую фигуру для указанных координат и организации.
     * @param coordinates координаты
     * @param organization организация
     * @return круговая фигура
     */
    private Circle createCircleCoordinates(Coordinates coordinates, Organization organization) {
        double centerX = canvas.getWidth() / 2;
        if (coordinates.getX() != null) {
            centerX += coordinates.getX();
            centerX = ((centerX % canvas.getWidth()) + canvas.getWidth()) % canvas.getWidth();
        }
        double centerY = canvas.getHeight() / 2;
        if (coordinates.getY() != null) {
            centerY += coordinates.getY();
            centerY = ((centerY % canvas.getHeight()) + canvas.getHeight()) % canvas.getHeight();
        }
        double clipped = Math.max(EC_LO, Math.min(organization.getEmployeesCount(), EC_HI));
        double radius = (MAX_RADIUS - MIN_RADIUS) / (EC_HI - EC_LO) * clipped + MIN_RADIUS;

        return new Circle(centerX, centerY, radius);
    }

    /**
     * Вложенный класс, представляющий графическую организацию.
     */
    private class GraphicOrganization {
        private final Organization organization;
        private final Circle circle;
        private boolean showOnCanvas = false;

        /**
         * Создает объект GraphicOrganization для указанной организации.
         * @param organization организация
         */
        GraphicOrganization(Organization organization) {
            this.organization = organization;
            circle = createCircleCoordinates(organization.getCoordinates(), organization);
            EventHandler<MouseEvent> clickHandler = event -> setSelectedOrganization(organization);
            circle.setOnMouseClicked(clickHandler);
            circle.setStrokeWidth(MIN_RADIUS / 2);
            shapesFill(getColorByOwnerHashCode());

            FadeTransition lineTransition = makeTransition(circle, 0, 1);
            lineTransition.setOnFinished(e -> {
                showOnCanvas = true;
                shapesFill(Color.TRANSPARENT);
                redrawOnCanvas();
            });
            lineTransition.play();

            clickableShapes.getChildren().addAll(circle);
            circle.setOnMouseExited(event -> setHoveredOrganization(null));
            circle.setOnMouseEntered(event -> setHoveredOrganization(organization));
        }

        /**
         * Возвращает организацию.
         * @return организация
         */
        Organization getOrganization() {
            return organization;
        }

        /**
         * Возвращает цвет, основанный на хэш-коде владельца организации.
         * @return цвет
         */
        Color getColorByOwnerHashCode() {
            Random random = new Random(organization.getOwner().hashCode());
            return Color.rgb(
                random.nextInt(MAX_RGB),
                random.nextInt(MAX_RGB),
                random.nextInt(MAX_RGB)
            );
        }

        /**
         * Заполняет фигуры цветом.
         * @param color цвет
         */
        void shapesFill(Color color) {
            circle.setFill(color);
        }

        /**
         * Рисует организацию на холсте.
         */
        void draw() {
            if (!showOnCanvas) {
                return;
            }
            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.setFill(getColorByOwnerHashCode());
            gc.setStroke(getColorByOwnerHashCode());
            gc.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);

            if (organization.equals(getSelectedOrganization())) {
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(circle.getRadius() / 6);
                double centerX = circle.getCenterX();
                double centerY = circle.getCenterY();
                double radius = circle.getRadius();
                gc.strokeOval(centerX - radius + gc.getLineWidth() / 2, centerY - radius + gc.getLineWidth() / 2, radius * 2 - gc.getLineWidth(), radius * 2 - gc.getLineWidth());
            }
            if (organization.equals(getSelectedOrganization())) {
                lastSelectedGraphicOrganization = this;
            }
        }

        /**
         * Рисует текст организации на холсте.
         */
        void drawText() {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFont(new Font(GRAPH_FONT_SIZE));
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(GRAPH_TEXT_OUTLINE_STROKE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);

            if (organization.equals(getHoveredOrganization()) || organization.equals(getSelectedOrganization())) {
                String nameLabel = LocaleManager.getObservableStringByKey("nameLabel").get();
                String ownerLabel = LocaleManager.getObservableStringByKey("ownerLabel").get();
                String text = nameLabel + ": " + organization.getName() + "\n" + ownerLabel + ": " + organization.getOwner();
                gc.strokeText(text,
                        circle.getCenterX(),
                        circle.getCenterY());
                gc.fillText(text,
                        circle.getCenterX(),
                        circle.getCenterY());
            }
        }

        /**
         * Удаляет графическую организацию с панели.
         */
        void removeFromPane() {
            showOnCanvas = false;
            shapesFill(getColorByOwnerHashCode());
            FadeTransition lineTransition = makeTransition(circle, 1, 0);
            lineTransition.setOnFinished(e -> clickableShapes.getChildren().removeAll(circle));
            lineTransition.play();
        }

        /**
         * Создает анимацию появления графической организации.
         * @param node графическая организация
         * @param from начальное значение прозрачности
         * @param to конечное значение прозрачности
         * @return анимация
         */
        FadeTransition makeTransition(Node node, double from, double to) {
            FadeTransition transition = new FadeTransition(Duration.millis(TRANSITION_MILLIS), node);
            transition.setFromValue(from);
            transition.setToValue(to);
            return transition;
        }
    }
}
