package controller;


import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Gallery;
import model.Photo;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import util.PhotoDownloader;

public class GalleryController {
    @FXML
    public TextField searchTextField;
    @FXML
    private ListView<Photo> imagesListView;
    @FXML
    private TextField imageNameField;
    @FXML
    private ImageView imageView;
    private Gallery galleryModel;

    @FXML
    public void initialize() {
        imagesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });

        imagesListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    bindSelectedPhoto(newValue);
        });
    }

    public void searchButtonClicked(ActionEvent event) {
        PhotoDownloader photoDownloader = new PhotoDownloader();
        galleryModel.clear();
        photoDownloader.searchForPhotos(searchTextField.getText())
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(photo -> galleryModel.addPhoto(photo));
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        ObservableList<Photo> photos = FXCollections.observableArrayList(gallery.getPhotos());
        imagesListView.setItems(photos);
        imagesListView.getSelectionModel().select(0);
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        imageNameField.textProperty().bind(selectedPhoto.nameProperty());
        imageView.imageProperty().bind(selectedPhoto.photoDataProperty());
    }
}
