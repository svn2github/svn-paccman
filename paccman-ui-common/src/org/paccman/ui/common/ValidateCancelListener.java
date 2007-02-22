/*
 * ValidateCancelListener.java
 *
 * Created on 15 décembre 2005, 11:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.common;

import java.util.EventListener;
import org.paccman.ui.common.*;

/**
 *
 * @author jfer
 */
public interface ValidateCancelListener extends EventListener {
    public void onValidateAction();
    public void onCancelAction();
}
