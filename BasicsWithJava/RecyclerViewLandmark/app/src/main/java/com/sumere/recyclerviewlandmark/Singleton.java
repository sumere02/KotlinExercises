package com.sumere.recyclerviewlandmark;

public class Singleton {

    private Landmark selectedLandmark;
    private static Singleton singleton;

    private Singleton(){

    }

    public Landmark getSelectedLandmark() {
        return selectedLandmark;
    }

    public void setSelectedLandmark(Landmark selectedLandmark) {
        this.selectedLandmark = selectedLandmark;
    }

    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return  singleton;
    }
}
