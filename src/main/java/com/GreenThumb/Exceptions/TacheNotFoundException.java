package com.GreenThumb.Exceptions;


public class TacheNotFoundException extends RuntimeException{
    public TacheNotFoundException(){
        super("Tache not found !");
    }
}
