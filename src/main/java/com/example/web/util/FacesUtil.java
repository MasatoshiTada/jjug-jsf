package com.example.web.util;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author tada
 */
public final class FacesUtil {
    
    public static final String REDIRECT = "?faces-redirect=true";
    
    private FacesUtil() {}
    
    public static Flash getFlash() {
        return FacesContext.getCurrentInstance().getExternalContext().getFlash();
    }
}
