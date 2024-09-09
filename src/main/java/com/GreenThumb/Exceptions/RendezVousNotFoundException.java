package com.GreenThumb.Exceptions;


public class RendezVousNotFoundException extends RuntimeException{
    public RendezVousNotFoundException(){
        super("RendezVous not found !");
    }
}
