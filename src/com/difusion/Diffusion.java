package com.difusion;

import java.io.Serializable;

/**
 *
 * @author Parisi Germán
 */
public abstract class Diffusion implements Serializable{
    private String idGroup;
    private String idSender;
    
    public Diffusion(String idGroup){
        this.idGroup = idGroup;
    }
    
    public String getIdGroup(){
        return this.idGroup;
    }
    
    public String getIdSender(){
        return this.idSender;
    }
    
    public void setIdGroup(String idGroup){
        this.idGroup = idGroup;
    }
    
    public void setIdSender(String idSender){
        this.idSender = idSender;
    }
}
