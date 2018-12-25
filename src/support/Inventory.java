/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

/**
 *
 * @author Sami
 */
public class Inventory {
    private Object[][] objects;
    private int slots;
    private int maxStack;
    
    public Inventory(int slots, int maxStack) {
        this.slots = slots;
        this.maxStack = maxStack;
        objects = new Object[slots][this.maxStack];
    }
    
    public Object getItem(int pos) {
        Object item = null;
        
        if (pos >= 0 && pos < this.objects.length) {
            for (int i = maxStack - 1;i >= 0;i--) {
                if (this.objects[pos][i] != null) {
                    item = this.objects[pos][i];
                    break;
                }
            }
        }
        
        return item;
    }
    
    public Object[] getHalfItems(int pos) {
        Object[] items = null;
        
        if (pos >= 0 && pos < this.objects.length) {
            int maxLength = -1;
            
            for (int i = 0;i < maxStack;i++) {
                if (this.objects[pos][i] == null) {
                    maxLength = i;
                    break;
                }
            }
            
            if (maxLength < 0) {
                maxLength = maxStack;
            }
            
            if (maxLength > 1) {
                maxLength = maxLength / 2;
            }
            
            if (maxLength > 0) {
                items = new Object[maxLength];
            }
            
            for (int i = 0;i < maxLength;i++) {
                items[i] = this.objects[pos][i];
            }
        }
        
        return items;
    }
    
    public Object[] getAllItems(int pos) {
        Object[] items = null;
        
        if (pos >= 0 && pos < this.objects.length) {
            items = this.objects[pos];
        }
        
        return items;
    }
    
    public Object removeItem(int pos) {
        Object item = null;
        
        if (pos >= 0 && pos < this.objects.length) {
            for (int i = maxStack - 1;i >= 0;i--) {
                if (this.objects[pos][i] != null) {
                    item = this.objects[pos][i];
                    this.objects[pos][i] = null;
                    break;
                }
            }
        }
        
        return item;
    }
    
    public Object[] removeHalfItems(int pos) {
        Object[] items = null;
        
        if (pos >= 0 && pos < this.objects.length) {
            int maxLength = -1;
            
            for (int i = 0;i < maxStack;i++) {
                if (this.objects[pos][i] == null) {
                    maxLength = i;
                    break;
                }
            }
            
            if (maxLength < 0) {
                maxLength = maxStack;
            }
            
            if (maxLength > 1) {
                maxLength = maxLength / 2;
            }
            
            if (maxLength > 0) {
                items = new Object[maxLength];
            }
            
            for (int i = maxLength - 1;i >= 0;i--) {
                items[i] = this.objects[pos][i];
                this.objects[pos][i] = null;
            }
            
            reOrganizeSlot(pos);
        }
        
        return items;
    }
    
    public Object[] removeAllItems(int pos) {
        Object[] items = null;
        
        if (pos >= 0 && pos < this.objects.length) {
            items = this.objects[pos].clone();
            
            for (int i = 0;i < maxStack;i++) {
                this.objects[pos][i] = null;
            }
        }
        
        return items;
    }
    
    public boolean addItem(int pos, Object item) {
        boolean added = false;
        
        if (item != null && pos >= 0 && pos < this.objects.length && (this.objects[pos][0] == null || this.objects[pos][0].equals(item))) {
            for (int i = 0;i < maxStack;i++) {
                if (this.objects[pos][i] == null) {
                    this.objects[pos][i] = item;
                    added = true;
                    break;
                }
            }
            
        }
        
        return added;
    }
    
    public Object[] addItems(int pos,Object[] items) {
        Object[] itemsLeft = null;
        Object[] itemsCopy;
        
        if (items != null) {
            itemsCopy = items.clone();
        } else {
            itemsCopy = null;
        }
        
        if (itemsCopy != null && pos >= 0 && pos < this.objects.length && (this.objects[pos][0] == null || this.objects[pos][0].equals(itemsCopy[0]))) {
            int freePos = -1;
            
            //Überprüft ab welcher Stelle im mit pos ausgewählte 64er Stack eine leere Position ist
            for (int i = 0;i < maxStack;i++) {
                if (this.objects[pos][i] == null) {
                    freePos = i;
                    break;
                }
            }
            
            if (freePos < 0) {
                return itemsCopy;
            }
            
            //Anschließend werden die Elemente des übergebenen items Array stück für stück in die freien Stellen eingefügt bis zum index 63 also eine Stelle vor 64
            //und dabei gleich aus dem items array entfernt
            for (int i = 0;i < itemsCopy.length && freePos < maxStack;i++,freePos++) {
                this.objects[pos][freePos] = itemsCopy[i];
                itemsCopy[i] = null;
            }
            
            //freePos wird auf -1 gesetzt für eine spätere Überprüfung
            freePos = -1;
            
            //Überprüft ab welcher Stelle noch elemente im items Array übrig sind
            for (int i = 0;i < itemsCopy.length;i++) {
                if (itemsCopy[i] != null) {
                    freePos = i;
                    break;
                }
            }
            
            //Wenn freePos == -1 dann wurden keine elemente mehr im items Array gefunden
            if (freePos > -1) {
                itemsLeft = new Object[itemsCopy.length - freePos];
                
                //Ansonsten werden die übriggebliebenen elemente in das itemsLeft Array verschoben und anschließend zurückgeliefert
                for (int i = 0;freePos < itemsCopy.length && i < itemsLeft.length;i++,freePos++) {
                    itemsLeft[i] = itemsCopy[freePos];
                }
            }
        }
        
        return itemsLeft;
    }
    
    public Object[] addItems(Object[] items) {
        boolean done = false;
        Object[] itemsLeft = null;
        Object[] itemsCopy;
        
        if (items != null) {
            itemsCopy = items.clone();
        } else {
            itemsCopy = null;
        }
        
        if (itemsCopy != null) {
            do {
                itemsLeft = null;
                int freePos = -1;
                int freePosVertical = -1;

                //Überprüft ab welcher Stelle im mit pos ausgewählte 64er Stack eine leere Position ist
                for (int i = 0;i < this.objects.length;i++) {
                    if (this.objects[i][0] != null && this.objects[i][0].equals(items[0])) {
                        for (int j = 0;j < this.objects[i].length;j++) {
                            if (this.objects[i][j] == null) {
                                freePosVertical = j;
                                break;
                            }
                        }

                        if (freePosVertical >= 0) {
                            freePos = i;
                            break;
                        } else {
                            continue;
                        }
                    }
                }

                if (freePos < 0) {
                    for (int i = 0;i < this.objects.length;i++) {
                        if (this.objects[i][0] == null) {
                            freePos = i;
                            break;
                        }
                    }
                }

                if (freePos < 0) {
                    return itemsCopy;
                }

                if (freePosVertical < 0) {
                    freePosVertical = 0;
                }

                //Anschließend werden die Elemente des übergebenen items Array stück für stück in die freien Stellen eingefügt bis zum index 63 also eine Stelle vor 64
                //und dabei gleich aus dem items array entfernt
                for (int i = freePosVertical;(i - freePosVertical) < itemsCopy.length && i < maxStack;i++) {
                    this.objects[freePos][i] = itemsCopy[i - freePosVertical];
                    itemsCopy[i - freePosVertical] = null;
                }

                //freePos wird auf -1 gesetzt für eine spätere Überprüfung
                freePos = -1;

                //Überprüft ab welcher Stelle noch elemente im items Array übrig sind
                for (int i = 0;i < itemsCopy.length;i++) {
                    if (itemsCopy[i] != null) {
                        freePos = i;
                        break;
                    }
                }

                //Wenn freePos == -1 dann wurden keine elemente mehr im items Array gefunden
                if (freePos > -1) {
                    itemsLeft = new Object[itemsCopy.length - freePos];

                    //Ansonsten werden die übriggebliebenen elemente in das itemsLeft Array verschoben und anschließend zurückgeliefert
                    for (int i = 0;freePos < itemsCopy.length && i < itemsLeft.length;i++,freePos++) {
                        itemsLeft[i] = itemsCopy[freePos];
                    }
                }

                if (itemsLeft != null) {
                    itemsCopy = itemsLeft.clone();
                } else {
                    done = true;
                    return itemsLeft;
                }
            } while (!done);
        }
        
        return itemsLeft;
    }
    
    public boolean isEmpty() {
        boolean empty = true;
        
        OuterLoop:
        for (int i = 0;i < this.objects.length;i++) {
            for (int j = 0;j < this.objects[i].length;j++) {
                if (objects[i][j] != null) {
                    empty = false;
                    break OuterLoop;
                }
            }
        }
        
        return empty;
    }
    
    public boolean isEmpty(int pos) {
        boolean empty = true;
        
        if (pos >= 0 && pos < this.objects.length) {
            for (int i = 0;i < this.objects[pos].length;i++) {
                if (objects[pos][i] != null) {
                    empty = false;
                    break;
                }
            }
        }
        
        return empty;
    }
    
    public int getNumberOfItemsInSlot(int slot) {
        int number = 0;
        
        if (slot >= 0 && slot < this.objects.length) {
            if (this.objects[slot][this.objects[slot].length - 1] != null) {
                number = maxStack;
                return number;
            }
            
            for (int i = 0;i < this.objects[slot].length;i++) {
                if (this.objects[slot][i] == null) {
                    number = i;
                    break;
                }
            }
        }
        
        return number;
    }
    
    public int getAvailableSlots() {
        return this.slots;
    }
    
    public int getMaxStack() {
        return this.maxStack;
    }
    
    private void reOrganizeSlot(int pos) {
        Object[] items = new Object[this.maxStack];
        
        for (int i = 0,j = 0;i < this.objects[pos].length && j < items.length;i++) {
            if (this.objects[pos][i] != null) {
                items[j] = this.objects[pos][i];
                j++;
            }
        }
        
        this.objects[pos] = items.clone();
    }
}
