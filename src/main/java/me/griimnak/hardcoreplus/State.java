package me.griimnak.hardcoreplus;

class State{
    // initial state
    private boolean state = true;

    public void set(boolean state){
        // set true or false
        this.state = state;
    }

    public boolean get() {
        // return true or false
        return this.state;
    }
}