package com.GreenThumb.Exceptions;


public class EquipmentNotFoundException extends RuntimeException{
    public EquipmentNotFoundException(){
        super("Equipment not found !");
    }
}
