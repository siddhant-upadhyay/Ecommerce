package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar; // hbox is used to provide the varieties of tools on the top of the page
    // and which is present in horizontal order manner
    HBox footerBar;
    Button signInButton;

    Label welcomeLabel;
    VBox body;
    Customer loggedInCustomer;

    ProductList productList = new ProductList();
    VBox productPage;

    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
   public BorderPane createContent(){
       BorderPane root = new BorderPane();
        root.setPrefSize(800,600); // size of the layout of the UI
       // root.getChildren().add(loginPage); // method to add the children to pane
           root.setTop(headerBar);
     //  root.setCenter(loginPage); // it will set the login credentials to centre
       body =  new VBox();
       body.setPadding(new Insets(10));
       body.setAlignment(Pos.CENTER);
       root.setCenter(body);
       productPage = productList.getAllProducts();
       body.getChildren().add(productPage);

       root.setBottom(footerBar);
        return root;
    }
    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    private void createLoginPage(){
       // Text is like creating and displaying the text over the UI
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");
        // for typing the actual text
        TextField userName = new TextField("sidupa523@gmail.com");
        userName.setPromptText("Type your user name here ");
        PasswordField password = new PasswordField(); // It will hide the digits
        password.setText("abc123");
        password.setPromptText("Type your password here");
        Label massageLabel = new Label("Welcome to the Shopping World");
        Button loginButton = new Button("Login");


        loginPage = new GridPane();
        loginPage.setStyle("-fx-background-color:grey;"); // it will make the color of the background as grey
        loginPage.setAlignment(Pos.CENTER); // It will set the credential to the center
        // hgap and vgap helps to make gaps in between the userame and password entry
        loginPage.setVgap(10);
        loginPage.setHgap(10);
        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(massageLabel,0,2);
        loginPage.add(loginButton , 1 ,2);

        // make the button to do it's working
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
      // fetching/displaying  the user name on to the massageLabel when clicking on the login button
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name , pass);
                if (loggedInCustomer != null){
                    massageLabel.setText("Welcome : " + loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome- "+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
                else {
                    massageLabel.setText("Login failed !  please provide correct credentials");
                }

            }
        });
    }

    private void createHeaderBar(){
       Button homeButton = new Button();
        Image image = new Image("C:\\Users\\KIIT\\IdeaProjects\\Ecommerce\\src\\357699e8f3cebb604bc8c2cb172682a9.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);


       TextField searchBar = new TextField();
       searchBar.setPromptText("Search here");
       searchBar.setPrefWidth(280); // it will change the width of the search bar
       Button searchButton = new Button("Search");
       signInButton = new Button("Sign In");
       welcomeLabel = new Label();

       Button cartButton = new Button("Cart");
        Button orderButton = new Button("Orders");

       headerBar = new HBox(20);// 20 is the spacing between head search area and search button
        headerBar.setStyle("-fx-background-color:yellow;");// it will change the color of whole header bar
       // PADDING IS PROVIDED to make some distance from the top i.e it will not the top bar
       headerBar.setPadding(new Insets(10));
       // It will make the search bar to the centre
       headerBar.setAlignment(Pos.CENTER);
       headerBar.getChildren().addAll(homeButton ,searchBar , searchButton , signInButton , cartButton , orderButton);

       signInButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               body.getChildren().clear(); // remove everything
               body.getChildren().add(loginPage); // redirected to login page
               headerBar.getChildren().remove(signInButton);
           }
       });
       cartButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               body.getChildren().clear();
               VBox prodPage = productList.getProductInCart(itemsInCart);
               prodPage.setAlignment(Pos.CENTER);// it will place the order button to the center
               prodPage.setSpacing(10);
               prodPage.getChildren().add(placeOrderButton);
               body.getChildren().add(prodPage);
               footerBar.setVisible(false); // all cases need to be handled for this
           }
       });

       placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               // need lists of product and customer
               if(itemsInCart == null){
                   // please select a product first to create a order
                   showDialogue("please add a product in cart to place an order !");
                   return;
               }
               if (loggedInCustomer == null){
                   showDialogue("Please login first to place the order");
                   return;
               }
               int count = Order.placeMultipleOrder(loggedInCustomer , itemsInCart );
               if (count != 0){
                   showDialogue("Woohhooo!! Order for "+count+" product placed Successfully");
               }
               else {
                   showDialogue("Order Failed !!");
               }
           }
       });
       homeButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               body.getChildren().clear();
               //body.getChildren().add(productPage);
              // footerBar.setVisible(true);
               if (loggedInCustomer == null) {
                   body.getChildren().add(loginPage);
                   headerBar.getChildren().remove(3);
               }
               else {
                   body.getChildren().add(productPage);
                   footerBar.setVisible(true);
               }
           }
       });

    }
    private void createFooterBar(){

        Button buyNowButton = new Button("BuyNow");
        Button addToCartButton = new Button("Add to Cart");

        signInButton = new Button("Sign In");
        welcomeLabel = new Label();
       footerBar = new HBox(20);// 20 is the spacing between head search area and search button
        //headerBar.setStyle("-fx-background-color:yellow;");// it will change the color of whole header bar
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        // PADDING IS PROVIDED to make some distance from the top i.e it will not the top bar
        // It will make the search bar to the centre
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton , addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    // please select a product first to create a order
                      showDialogue("please select a product first to create a order !");
                      return;
                }
                if (loggedInCustomer == null){
                    showDialogue("Please login first to place the order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer , product);
                if (status == true){
                    showDialogue("Woohhooo!! Order placed Successfully");
                }
                else {
                    showDialogue("Order Failed !!");
                }
            }
        });
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if (product == null) {
                    // please select a product first to create a order
                    showDialogue("please select a product first to add it to cart !");
                    return;
                }
                itemsInCart.add(product);
                showDialogue("Selected items is added to cart successfully ");
            }
        });
    }
    private void showDialogue (String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
