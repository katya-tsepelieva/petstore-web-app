package com.petstore.web_app.controller;

import com.petstore.web_app.model.User;
import com.petstore.web_app.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(@SessionAttribute("loggedInUser") User user, Model model) {
        model.addAttribute("cartItems", cartService.getCartItems(user));
        model.addAttribute("totalPrice", cartService.getTotalPrice(user));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@SessionAttribute("loggedInUser") User user,
                            @RequestParam Long productId,
                            @RequestParam int quantity) {
        cartService.addToCart(user, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@SessionAttribute("loggedInUser") User user,
                                 @RequestParam Long productId) {
        cartService.removeFromCart(user, productId);
        return "redirect:/cart";
    }
}

