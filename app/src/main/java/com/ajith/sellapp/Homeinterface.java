package com.ajith.sellapp;

import com.ajith.sellapp.model.Product;

public interface Homeinterface {
    //Listener
    interface Homeitem{
        void onHomeItemSelect(Product mProduct);
    }
    interface TotalAmout{
        void getTotalAmount(String amt);
    }
}
