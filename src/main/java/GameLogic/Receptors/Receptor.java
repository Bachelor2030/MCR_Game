package GameLogic.Receptors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Receptor {
    protected String name;

    private String imagePath = "design/images/creatures/empty.jpg";
    private Image image;
    private ImageView imageView;

    public Receptor(String name){
        this.name = name;
        initDisplay();
    }

    public Receptor() {
        this.name = "empty";
        initDisplay();
    }

    public String getName() {
        return name;
    }

    public abstract void playTurn(int turn);

    @Override
    public String toString() {
        return name;
    }

    private void initDisplay() {
        try {
            image = new Image(imagePath);
            imageView = new ImageView(image);
            imageView.setFitWidth(image.getWidth() * 0.2);
            imageView.setFitHeight(image.getHeight() * 0.2);

            if(imagePath.equals("design/images/creatures/empty.jpg")) {
                imageView.setImage(null);
            }
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Image getImage() {
        return image;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        initDisplay();
    }

    public void setTo(Receptor receptor) {
        System.out.println("I am a new receptor");
        if(receptor == null) {
            imagePath = "design/images/creatures/empty.jpg";
            initDisplay();
        } else {
            imagePath = receptor.imagePath;
            image = receptor.image;
            imageView = receptor.imageView;
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public JSONObject toJson() {
        JSONObject receptor = new JSONObject();
        try {
            receptor.put("name", name);
            receptor.put("image", imagePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return receptor;
    }
}
