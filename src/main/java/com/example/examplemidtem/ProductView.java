package com.example.examplemidtem;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Route(value="index")
public class ProductView extends VerticalLayout {
    private ArrayList<Product> productArrayList;
    private ArrayList nameList;
    private ComboBox productList;
    private TextField productName;
    private NumberField productCost, productProfit, productPrice;
    private Button addBtn, delBtn, updateBtn, clearBtn;
    private int i = 0;

    public ProductView(){
        productList = new ComboBox("Product List : ");
        productList.setWidth("600px");
        productName = new TextField("Product Name : ");
        productName.setWidth("600px");
        productName.setValue("");
        productCost = new NumberField("Product Cost : ");
        productCost.setWidth("600px");
        productCost.setValue(0.0);
        productProfit = new NumberField("Product Profit : ");
        productProfit.setWidth("600px");
        productProfit.setValue(0.0);
        productPrice = new NumberField("Product Price : ");
        productPrice.setWidth("600px");
        productPrice.setValue(0.0);
        productPrice.setEnabled(false);
        addBtn = new Button("Add Product");
        delBtn = new Button("Delete Product");
        updateBtn = new Button("Update Product");
        clearBtn = new Button("Clear Product");
        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(addBtn, delBtn, updateBtn, clearBtn);
        this.add(productList, productName, productCost, productProfit, productPrice, h1);
        this.fetchData();

        productList.addFocusListener(event->{
            this.onTimeData();
        });
        productCost.addValueChangeListener(event->{
            this.priceChanged();
            });
        productProfit.addValueChangeListener(event->{
            this.priceChanged();
            });

        addBtn.addClickListener(event->{
           Product prod = new Product(null, productName.getValue(), productCost.getValue(), productProfit.getValue(), productPrice.getValue());
           String state = WebClient.create()
                   .post()
                   .uri("http://localhost:8080/addProduct")
                   .body(Mono.just(prod), Product.class)
                   .retrieve()
                   .bodyToMono(String.class)
                   .block();
            System.out.println(state);
            this.fetchData();
        });

        delBtn.addClickListener(event->{
            Product productbyName = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/getProductByName")
                    .body(Mono.just((String) productList.getValue()), String.class)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .block();

            String state = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/delProduct")
                    .body(Mono.just(productbyName), Product.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            this.fetchData();
        });

        updateBtn.addClickListener(event->{
            Product productbyName = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/getProductByName")
                    .body(Mono.just((String) productList.getValue()), String.class)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .block();
            productbyName.setProductName(productName.getValue());
            productbyName.setProductCost(productCost.getValue());
            productbyName.setProductProfit(productProfit.getValue());
            productbyName.setProductPrice(productPrice.getValue());
            String state = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateProduct")
                    .body(Mono.just(productbyName), Product.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println(state);
            this.fetchData();
        });

        clearBtn.addClickListener(event->{
            productList.setValue("");
           productName.setValue("");
           productCost.setValue(0.0);
           productProfit.setValue(0.0);
        });
    }

    public void priceChanged(){
        double cost = productCost.getValue();
        double profit = productProfit.getValue();
        double val = WebClient.create()
                .get()
                .uri("http://localhost:8080/getPrice/"+cost+"/"+profit)
                .retrieve()
                .bodyToMono(double.class)
                .block();
        productPrice.setValue(val);
    }
    public void fetchData(){
        ArrayList<Product> products = WebClient.create()
                .get()
                .uri("http://localhost:8080/getAllProduct")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ArrayList<Product>>() {
                })
                .block();
        System.out.println(products);
        productArrayList = new ArrayList<Product>(products);
        System.out.println("ProdArrayList: "+productArrayList);
        nameList = new ArrayList<String>();
        for (int i = 0;i<productArrayList.size();i++) {
            nameList.add(productArrayList.get(i).getProductName());
        }
        System.out.println("Combobox: "+nameList);
        productList.setItems(nameList);

    }

    public void onTimeData(){
//        System.out.println(productList.getValue());
            productName.setValue((String) productList.getValue());
            Product productWhereName = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/getProductByName")
                    .body(Mono.just((String) productList.getValue()), String.class)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .block();
            System.out.println(productWhereName);
            productCost.setValue(productWhereName.getProductCost());
            productProfit.setValue(productWhereName.getProductProfit());


    }
}
