/*
 * DocumentListener.java
 *
 * Created on 2 février 2006, 08:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.listener;

import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;
import org.paccman.paccman.Category;
import org.paccman.paccman.Document;

/**
 *
 * @author joao
 */
public interface DocumentListener {
    public void documentLoaded(Document document);
    
    public void accountAdded (Account account  );
    public void categoryAdded(Category category);
    public void bankAdded    (Bank     bank    );
}
