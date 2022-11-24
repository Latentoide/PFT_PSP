package es.alfema.pft;

import java.io.Serializable;

public enum Direcciones implements Serializable {
    UP(-10), DOWN(10), LEFT(-10), RIGHT(10);

    int val;
    Direcciones(int i){
        val = i;
    }

    int getVal(){
        return val;
    }
}
