package pizzaloop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * Created by Chathura on 4/8/2019.
 */
@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    private PizzaRepository pizzaRepository;
    private static final String SUCCESS= "Saved";
    /*
    * READ Operation
    * This method will list all the pizzas in the table
    * URI to access this: http://localhost:8080/demo/all
    */
    @GetMapping(path="/all")
    public @ResponseBody Iterable<PizzaDetails> getPizzaDetails() {
        return pizzaRepository.findAll();
    }

    /*
    * READ Operation based on Pizza ID
    * This method will return the details of a pizza specified by the id
    * URI to access this: http://localhost:8080/demo/findByPizzaId?id=2
    */
    @GetMapping(path="/findByPizzaId")
    public @ResponseBody List<PizzaDetails> getPizzaById(@RequestParam Integer id) {
        return pizzaRepository.findByPizzaId(id);
    }

    /*
    * CREATE Operation
    * This method will crate new pizza item in the database table
    * and returns the SUCCESS message
    * URI to access this: http://localhost:8080/demo/add?name=VegiPizza&description=VegiSupreme&price=2500.75
    */
    @GetMapping(path="/add")
    public @ResponseBody String addNewPizza(@RequestParam String name, @RequestParam String description, @RequestParam Double price) {
        PizzaDetails pizzaDetails = new PizzaDetails();
        pizzaDetails.setName(name);
        pizzaDetails.setDescription(description);
        pizzaDetails.setPrice(price);
        pizzaRepository.save(pizzaDetails);
        return SUCCESS;
    }

    /*
    * DELETE Operation
    * This method will delete existing pizza item by finding it using the ID
    * and returns the deleted item
    * URI to access this: http://localhost:8080/demo/deleteByPizzaId?id=2
    */
    @GetMapping(path="/deleteByPizzaId")
    public @ResponseBody List<PizzaDetails> deletePizzaById(@RequestParam Integer id) {
        return pizzaRepository.deleteByPizzaId(id);
    }

    /*
    * UPDATE Operation
    * This method will update existing pizza details by finding it using the ID
    * and returns the updated data
    * URI to access this: http://localhost:8080/demo/update?id=1&name=updatedname&description=updated&price=1234.56
    */
    @GetMapping(path="/update")
    public @ResponseBody List<PizzaDetails> updatePizzaDetails(@RequestParam Integer id, @RequestParam String name, @RequestParam String description, @RequestParam Double price) {
        //First get all the pizza details according to the provided ID
        List<PizzaDetails> pizzaDetailsList = pizzaRepository.findByPizzaId(id);
        if(!pizzaDetailsList.isEmpty()) {
            //Iterate through the pizza list
            for(PizzaDetails pizzaDetails: pizzaDetailsList) {
                //Set new values
                pizzaDetails.setName(name);
                pizzaDetails.setDescription(description);
                pizzaDetails.setPrice(price);
                //Update existing pizza item
                pizzaRepository.save(pizzaDetails);
            }
        }
        return pizzaRepository.findByPizzaId(id);
    }
}
