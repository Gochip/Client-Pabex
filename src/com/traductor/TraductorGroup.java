/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traductor;

/**
 *
 * @author PaBex
 * @version
 */
public class TraductorGroup {

    private String idGroup,name, idAdmin;
    private Integer maxNum;

    public TraductorGroup(String idGroup,String name, String idAdmin, Integer maxNum) {
        this.idGroup = idGroup;
        this.name = name;
        this.idAdmin = idAdmin;
        this.maxNum = maxNum;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public String getName() {
        return name;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

  
}
