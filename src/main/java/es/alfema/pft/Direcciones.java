package es.alfema.pft;

import java.io.Serializable;

public enum Direcciones implements Serializable {
    UP(-1), DOWN(1), LEFT(-1), RIGHT(1);

    int val;
    Direcciones(int i){
        val = i;
    }

    int getVal(){
        return val;
    }
}
