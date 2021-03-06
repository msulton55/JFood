package msultont.JFood.controller;

import msultont.JFood.*;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

/**
 * This is all Invoice's API for requesting the data into backend system.
 *
 * @author Muhammad Sulton Tauhid
 * @version May 12th, 2020
 */

@RequestMapping("/invoice")
@RestController
public class InvoiceController {
    @RequestMapping("")
    public String indexPage() {
        return "Hello, you are in the Invoice page";
    }

    @RequestMapping("/listInvoiceOngoing")
    public ArrayList<Invoice> getAllInvoice() {
        return DatabaseInvoice.getInvoiceDatabase();
    }

    @RequestMapping("/listInvoiceCanceled")
    public ArrayList<Invoice> getAllInvoiceCanceled() {
        return DatabaseInvoice.getInvoiceDatabaseCanceled();
    }

    @RequestMapping("/listInvoiceFinished")
    public ArrayList<Invoice> getAllInvoiceFinished() {
        return DatabaseInvoice.getInvoiceDatabaseFinished();
    }

    @RequestMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable int id) {
        Invoice invoice = null;
        try {
            invoice = DatabaseInvoice.getInvoiceById(id);
        } catch (InvoiceNotFoundException e) {
            System.out.println(e);
        }
        return invoice;

    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    public ArrayList<Invoice> getInvoiceByCustomer(@PathVariable int customerId) {
        return DatabaseInvoice.getInvoiceByCustomer(customerId);
    }

    @RequestMapping(value = "/invoiceStatus/{id}", method = RequestMethod.PUT)
    public Invoice changeInvoiceStatus(@PathVariable int id, 
                                       @RequestParam(value = "status") InvoiceStatus status) 
    {
        for (Invoice invoice : getAllInvoice()) {
            if (invoice.getId() == id) {
                invoice.setInvoiceStatus(status);
                return invoice;
            }
        }
        return null;
    }

    @RequestMapping(value = "/deleteInvoice", method = RequestMethod.DELETE)
    public Boolean removeInvoice(@RequestParam(value = "id") int id) {
        try {
            DatabaseInvoice.removeInvoice(id);
        } catch (InvoiceNotFoundException e) {
            System.out.println(e);
        }
        return true;
    }

    @RequestMapping(value = "/addFood/{customerId}", method = RequestMethod.PUT)
    public Invoice addFoodPerInvoiceByCustomerId(@RequestParam(value = "foodIdList") ArrayList<Integer> foodIdList, 
                                                 @PathVariable int customerId) {
        ArrayList<Food> newFood = new ArrayList<>();
        try {
            for (int counter : foodIdList) {
                newFood.add(DatabaseFood.getFoodById(counter));
            }
        } catch (FoodNotFoundException e) {
            System.out.println(e);
        }
        for (Invoice invoice : DatabaseInvoice.getInvoiceByCustomer(customerId)) {
            if (invoice.getPaymentType().equals(PaymentType.Cash)) {
                for (Food food : newFood) {
                    invoice.setFoods(food);
                    invoice.setTotalPrice();
                }
                return invoice;
            }
        }
        return null;
    }

    @RequestMapping(value = "/changeStatus/{customerId}", method = RequestMethod.PUT)
    public boolean invoiceChangeStatus(@RequestParam(value = "invoiceStatus") InvoiceStatus invoiceStatus,
                                       @PathVariable int customerId) {

        Boolean result = DatabaseInvoice.changeInvoiceStatusByCustomerId(customerId, invoiceStatus);
        DatabaseInvoice.removeInvoiceByCustomerId(customerId);
        return result;

    }

    @RequestMapping(value = "/createCashInvoice", method = RequestMethod.POST)
    public Invoice addCashInvoice(@RequestParam(value = "foodIdList") ArrayList<Integer> foodIdList, 
                                  @RequestParam(value = "customerId") int customerId, 
                                  @RequestParam(value = "deliveryFee", required = false, defaultValue = "0") int deliveryFee) 
    {
        ArrayList<Food> newFood = new ArrayList<>();
        CashInvoice cashInvoice = null;
        try {
            for (int counter : foodIdList) {
                newFood.add(DatabaseFood.getFoodById(counter));
            }
        } catch (FoodNotFoundException e) {
            System.out.println(e);
        }

        try {
            cashInvoice = new CashInvoice(DatabaseInvoice.getLastId()+1, newFood, DatabaseCustomer.getCustomerById(customerId), deliveryFee);
            cashInvoice.setTotalPrice();
        } catch (CustomerNotFoundException e) {
            System.out.println(e);
        }
        try {
            DatabaseInvoice.addInvoice(cashInvoice);
            return cashInvoice;
        } catch (OngoingInvoiceAlreadyExistsException e) {
            System.out.println(e);
        }
        return null;
    }

    @RequestMapping(value = "/createCashlessInvoice", method = RequestMethod.POST)
    public Invoice addCashlessInvoice (@RequestParam(value = "foodIdList") ArrayList<Integer> foodIdList, 
                                       @RequestParam(value = "customerId" )int customerId, 
                                       @RequestParam(value = "promoCode", required = false) String promoCode) 
    {
        ArrayList<Food> newFood = new ArrayList<>();
        CashlessInvoice cashlessInvoice = null;
        try {
            for (int counter : foodIdList) {
                newFood.add(DatabaseFood.getFoodById(counter));
            }
        } catch (FoodNotFoundException e) {
            System.out.println(e);
        }
        try {
            cashlessInvoice = new CashlessInvoice(DatabaseInvoice.getLastId()+1, newFood, DatabaseCustomer.getCustomerById(customerId), DatabasePromo.getPromoByCode(promoCode));
            cashlessInvoice.setTotalPrice();
            
        } catch (CustomerNotFoundException | PromoNotFoundException e) {
            System.out.println(e);
        }
        try {
            DatabaseInvoice.addInvoice(cashlessInvoice);
            return cashlessInvoice;
        } catch (OngoingInvoiceAlreadyExistsException e) {
            System.out.println(e);
        }
        return null;
    }
}