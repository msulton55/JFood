package msultont.JFood.controller;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import msultont.JFood.*;

/**
 * PromoController
 */
@RequestMapping("/promo")
@RestController
public class PromoController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ArrayList<Promo> getAllPromo() {
        return DatabasePromo.getPromoDatabase();
    }

    @RequestMapping(value = "/{promoCode}", method = RequestMethod.GET)
    public Promo getPromoByCode(@PathVariable String promoCode) {
        Promo promo = DatabasePromo.getPromoByCode(promoCode);
        return promo;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Promo addPromo(@RequestParam(value = "code") String code, 
                          @RequestParam(value = "discount") int discount, 
                          @RequestParam(value = "minPrice") int minPrice, 
                          @RequestParam(value = "active") boolean active) 
    {
        Promo promo = new Promo(DatabasePromo.getLastId()+1, code, discount, minPrice, active);
        try {
            DatabasePromo.addPromo(promo);
        } catch (PromoCodeAlreadyExistsException e) {
            System.out.println(e);
        }
        return promo;
    }
    
}