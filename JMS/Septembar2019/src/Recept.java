
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class Recept implements Serializable{
    public String naziv;
    public String detalji;
    
    public Recept()
    {
    }
    public Recept(String naziv)
    {
        this.naziv = naziv;
    }
}
